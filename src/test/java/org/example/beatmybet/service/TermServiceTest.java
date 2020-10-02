package org.example.beatmybet.service;

import org.example.beatmybet.dto.BidDTO;
import org.example.beatmybet.dto.TermDto;
import org.example.beatmybet.dto.TermVariantDto;
import org.example.beatmybet.entity.Category;
import org.example.beatmybet.entity.Event;
import org.example.beatmybet.entity.Term;
import org.example.beatmybet.entity.TermVariant;
import org.example.beatmybet.repository.EventRepository;
import org.example.beatmybet.repository.TermRepository;
import org.example.beatmybet.repository.TermVariantRepository;
import org.example.beatmybet.repository.UserRepository;
import org.example.beatmybet.service.impl.TermServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@TestExecutionListeners(MockitoTestExecutionListener.class)
public class TermServiceTest {
    TermService termService;

    @Mock
    private TermRepository termRepository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TermVariantRepository termVariantRepository;

    @Mock
    private BidService bidService;

    private static final Category POLITICS = new Category();
    private static final Category PRESIDENT_SELECTIONS = new Category();

    private static final Event PRESIDENT_UKRAINE = generateEvent(1L, PRESIDENT_SELECTIONS, LocalDateTime.now(), "president selection in Ukraine",
            LocalDateTime.of(2020, Month.JUNE, 23, 1, 1), LocalDateTime.of(2020, Month.APRIL, 23, 1, 1));
    private static final Event PRESIDENT_GERMANY = generateEvent(2L, PRESIDENT_SELECTIONS, LocalDateTime.now(), "president selection in Africa",
            LocalDateTime.of(2020, Month.MAY, 23, 1, 1), LocalDateTime.of(2020, Month.APRIL, 23, 1, 1));
    private static final Event PRESIDENT_USA = generateEvent(3L, PRESIDENT_SELECTIONS, LocalDateTime.now(), "president selection in USA",
            LocalDateTime.of(2020, Month.AUGUST, 23, 1, 1), LocalDateTime.of(2020, Month.APRIL, 23, 1, 1));

    private static final Term ZELENSKII = generateTerm(1L, PRESIDENT_UKRAINE, "Zelenskii will win");
    private static final Term POROSHEKNO = generateTerm(2L, PRESIDENT_UKRAINE, "Poroshenko will win");
    private static final Term TIMOSHENKO = generateTerm(3L, PRESIDENT_UKRAINE, "Timoshenko will win");
    private static final Term CHUPAKABRA = generateTerm(4L, PRESIDENT_UKRAINE, "Chupakabra will win");

    private static final Term SHTANMAIER = generateTerm(5L, PRESIDENT_GERMANY, "Shtanmaier will win");
    private static final Term TRUMP = generateTerm(6L, PRESIDENT_USA, "Donald Trump will win");

    private static final TermVariant ZELENSKII_1 = generateTermVariant(1L, ZELENSKII, "Yes");
    private static final TermVariant ZELENSKII_2 = generateTermVariant(2L, ZELENSKII, "No");
    private static final TermVariant POROSHENKO_1 = generateTermVariant(3L, POROSHEKNO, "Yes");
    private static final TermVariant POROSHENKO_2 = generateTermVariant(4L, POROSHEKNO, "No");
    private static final TermVariant TIMOSHENKO_1 = generateTermVariant(5L, ZELENSKII, "Yes");
    private static final TermVariant TIMOSHENKO_2 = generateTermVariant(6L, ZELENSKII, "No");
    private static final TermVariant CHUPAKABRA_1 = generateTermVariant(7L, ZELENSKII, "Yes");
    private static final TermVariant CHUPAKABRA_2 = generateTermVariant(8L, ZELENSKII, "No");


    private static Event generateEvent(Long id, Category category, LocalDateTime date, String name, LocalDateTime dateStop, LocalDateTime dateEnd) {
        Event event = new Event();
        event.setId(id);
        event.setCategory(category);
        event.setDate(date);
        event.setName(name);
        event.setDateStop(dateStop);
        event.setDateEnd(dateEnd);
        return event;
    }

    private static Term generateTerm(Long id, Event event, String name) {
        Term term = new Term();
        term.setId(id);
        term.setEvent(event);
        term.setName(name);
        return term;
    }

    private static TermVariant generateTermVariant(Long id, Term term, String title) {
        TermVariant termVariant = new TermVariant();
        termVariant.setId(id);
        termVariant.setName(title);
        termVariant.setTerm(term);
        return termVariant;
    }

    @BeforeEach
    public void setUp() {
        termService = new TermServiceImpl(termRepository, eventRepository, userRepository, termVariantRepository, bidService);
        POLITICS.setId(1L);
        POLITICS.setName("politics");
        POLITICS.setCategory(null);

        PRESIDENT_SELECTIONS.setId(2L);
        PRESIDENT_SELECTIONS.setName("president selections");
        PRESIDENT_SELECTIONS.setCategory(POLITICS);

        PRESIDENT_UKRAINE.setTerms(List.of(ZELENSKII, POROSHEKNO, TIMOSHENKO, CHUPAKABRA));
        PRESIDENT_GERMANY.setTerms(List.of(SHTANMAIER));
        PRESIDENT_USA.setTerms(List.of(TRUMP));

        ZELENSKII.setVariants(List.of(ZELENSKII_1, ZELENSKII_2));
        POROSHEKNO.setVariants(List.of(POROSHENKO_1, POROSHENKO_2));
        TIMOSHENKO.setVariants(List.of(TIMOSHENKO_1, TIMOSHENKO_2));
        CHUPAKABRA.setVariants(List.of(CHUPAKABRA_1, CHUPAKABRA_2));

        doReturn(new Long[]{ZELENSKII.getId()}).when(termRepository).getMostPopularTermFromTerms(List.of(ZELENSKII.getId(),
                POROSHEKNO.getId(), TIMOSHENKO.getId(), CHUPAKABRA.getId()));

        doReturn(ZELENSKII).when(termRepository).getOne(ZELENSKII.getId());
        doReturn(POROSHEKNO).when(termRepository).getOne(POROSHEKNO.getId());
        doReturn(TIMOSHENKO).when(termRepository).getOne(TIMOSHENKO.getId());
        doReturn(CHUPAKABRA).when(termRepository).getOne(CHUPAKABRA.getId());
        doReturn(SHTANMAIER).when(termRepository).getOne(SHTANMAIER.getId());
        doReturn(TRUMP).when(termRepository).getOne(TRUMP.getId());

        doReturn(ZELENSKII_1).when(termVariantRepository).findOpposite(ZELENSKII.getId(), ZELENSKII_2.getId());
        doReturn(ZELENSKII_2).when(termVariantRepository).findOpposite(ZELENSKII.getId(), ZELENSKII_1.getId());
        doReturn(POROSHENKO_1).when(termVariantRepository).findOpposite(POROSHEKNO.getId(), POROSHENKO_2.getId());
        doReturn(POROSHENKO_2).when(termVariantRepository).findOpposite(POROSHEKNO.getId(), POROSHENKO_1.getId());
        doReturn(TIMOSHENKO_1).when(termVariantRepository).findOpposite(TIMOSHENKO.getId(), TIMOSHENKO_2.getId());
        doReturn(TIMOSHENKO_2).when(termVariantRepository).findOpposite(TIMOSHENKO.getId(), TIMOSHENKO_1.getId());
        doReturn(CHUPAKABRA_1).when(termVariantRepository).findOpposite(CHUPAKABRA.getId(), CHUPAKABRA_2.getId());
        doReturn(CHUPAKABRA_2).when(termVariantRepository).findOpposite(CHUPAKABRA.getId(), CHUPAKABRA_1.getId());
    }

    @Test
    void mostPopularTermByEvent() {
        Term actual = termService.mostPopularTermByEvent(PRESIDENT_UKRAINE);
        assertEquals(actual, ZELENSKII);
    }

    @Test
    void allTermsByEvent() {
        TermDto zelenskiiWinner = new TermDto();
        zelenskiiWinner.setId(ZELENSKII.getId());
        zelenskiiWinner.setTitle(ZELENSKII.getName());
        zelenskiiWinner.setVariants(List.of(
                new TermVariantDto("No", ZELENSKII_2.getId(), List.of()),
        new TermVariantDto("Yes", ZELENSKII_1.getId(), List.of())
        ));
        TermDto poroshenkoWinner = new TermDto();
        poroshenkoWinner.setId(POROSHEKNO.getId());
        poroshenkoWinner.setTitle(POROSHEKNO.getName());
        poroshenkoWinner.setVariants(List.of(
                new TermVariantDto("No", POROSHENKO_2.getId(),  List.of()),
                new TermVariantDto("Yes", POROSHENKO_1.getId(), List.of())
        ));
        TermDto timoshenkoWinner = new TermDto();
        timoshenkoWinner.setId(TIMOSHENKO.getId());
        timoshenkoWinner.setTitle(TIMOSHENKO.getName());
        timoshenkoWinner.setVariants(List.of(
                new TermVariantDto("No", TIMOSHENKO_2.getId(),  List.of()),
                new TermVariantDto("Yes", TIMOSHENKO_1.getId(), List.of())
        ));
        TermDto chupakabraWinner = new TermDto();
        chupakabraWinner.setId(CHUPAKABRA.getId());
        chupakabraWinner.setTitle(CHUPAKABRA.getName());
        chupakabraWinner.setVariants(List.of(
                new TermVariantDto("No", CHUPAKABRA_2.getId(), List.of()),
                new TermVariantDto("Yes", CHUPAKABRA_1.getId(), List.of())
        ));
        List<TermDto> expected = List.of(zelenskiiWinner, poroshenkoWinner, timoshenkoWinner, chupakabraWinner);
        List<TermDto> actual = termService.allTermsByEvent(PRESIDENT_UKRAINE);
        assertEquals(expected, actual);
    }

}
