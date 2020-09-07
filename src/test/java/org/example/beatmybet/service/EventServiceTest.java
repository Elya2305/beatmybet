package org.example.beatmybet.service;


import org.example.beatmybet.dto.BaseEventDTO;
import org.example.beatmybet.dto.HomeBidDTO;
import org.example.beatmybet.dto.HomeEventDTO;
import org.example.beatmybet.entity.Category;
import org.example.beatmybet.entity.Event;
import org.example.beatmybet.entity.Term;
import org.example.beatmybet.repository.CategoryRepository;
import org.example.beatmybet.repository.EventRepository;
import org.example.beatmybet.repository.UserRepository;
import org.example.beatmybet.service.impl.EventServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@TestExecutionListeners(MockitoTestExecutionListener.class)
public class EventServiceTest {

    private EventService eventService;
    @Mock
    private TermService termService;
    @Mock
    private BidService bidService;
    @Mock
    private EventRepository eventRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private UserRepository userRepository;

    private static int FIRST_PAGE = 1;
    private static int SECOND_PAGE = 2;
    private static int THIRD_PAGE = 3;

    private static final Category POLITICS = new Category();
    private static final Category PRESIDENT_SELECTIONS = new Category();

    private static final Event PRESIDENT_UKRAINE = generateEvent(1L, PRESIDENT_SELECTIONS, LocalDateTime.now(), "president selection in Ukraine",
            LocalDateTime.of(2020, Month.JUNE, 23, 1, 1), LocalDateTime.of(2020, Month.APRIL, 23, 1, 1));
    private static final Event PRESIDENT_GERMANY = generateEvent(2L, PRESIDENT_SELECTIONS, LocalDateTime.now(), "president selection in Africa",
            LocalDateTime.of(2020, Month.MAY, 23, 1, 1), LocalDateTime.of(2020, Month.APRIL, 23, 1, 1));
    private static final Event PRESIDENT_USA = generateEvent(3L, PRESIDENT_SELECTIONS, LocalDateTime.now(), "president selection in USA",
            LocalDateTime.of(2020, Month.AUGUST, 23, 1, 1), LocalDateTime.of(2020, Month.APRIL, 23, 1, 1));

    private static final Term ZELENSKII = generateTerm(1L, PRESIDENT_UKRAINE, "Zelenskii will win");
    private static final Term SHTANMAIER = generateTerm(2L, PRESIDENT_GERMANY, "Shtanmaier will win");
    private static final Term TRUMP = generateTerm(3L, PRESIDENT_USA, "Donald Trump will win");

    @BeforeEach
    public void setUp() {
        eventService = new EventServiceImpl(termService, bidService, eventRepository, categoryRepository, userRepository);
        POLITICS.setId(1L);
        POLITICS.setName("politics");
        POLITICS.setCategory(null);

        PRESIDENT_SELECTIONS.setId(2L);
        PRESIDENT_SELECTIONS.setName("president selections");
        PRESIDENT_SELECTIONS.setCategory(POLITICS);

        doReturn(List.of(PRESIDENT_UKRAINE, PRESIDENT_GERMANY, PRESIDENT_USA)).when(eventRepository).findAll();

        doReturn(ZELENSKII).when(termService).mostPopularTermByEvent(PRESIDENT_UKRAINE);
        doReturn(SHTANMAIER).when(termService).mostPopularTermByEvent(PRESIDENT_GERMANY);
        doReturn(TRUMP).when(termService).mostPopularTermByEvent(PRESIDENT_USA);

        doReturn(List.of(new HomeBidDTO("Yes", 2.0), new HomeBidDTO("No", 1.7))).when(bidService).homeBidDTOsByTerm(ZELENSKII);
        doReturn(List.of(new HomeBidDTO("Yes", 1.3), new HomeBidDTO("No", 5.8))).when(bidService).homeBidDTOsByTerm(SHTANMAIER);
        doReturn(List.of(new HomeBidDTO("Yes", 2.4), new HomeBidDTO("No", 8.7))).when(bidService).homeBidDTOsByTerm(TRUMP);

        doReturn(4).when(eventRepository).countAmountOfBids(PRESIDENT_UKRAINE.getId());
        doReturn(5).when(eventRepository).countAmountOfBids(PRESIDENT_GERMANY.getId());
        doReturn(6).when(eventRepository).countAmountOfBids(PRESIDENT_USA.getId());


    }

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

    private void setBasicsAttributes(Event event, BaseEventDTO baseEventDTO) {
        baseEventDTO.setId(event.getId());
        baseEventDTO.setCategory(event.getCategory().getName());
        baseEventDTO.setSuperCategory(event.getCategory().getCategory().getName());
        baseEventDTO.setDateStop(event.getDateStop().toLocalDate());
        baseEventDTO.setContent(event.getName());
        baseEventDTO.setAmountOfBids(eventRepository.countAmountOfBids(event.getId()));
    }

    @Test
    public void getAllEventWithMostPopularBidOrderedByPopularity() {
        HomeEventDTO ukraineEvent = new HomeEventDTO();
        setBasicsAttributes(PRESIDENT_UKRAINE, ukraineEvent);
        ukraineEvent.setTitleTerm(ZELENSKII.getName());
        ukraineEvent.setBids(List.of(new HomeBidDTO("Yes", 2.0), new HomeBidDTO("No", 1.7)));

        HomeEventDTO germanEvent = new HomeEventDTO();
        setBasicsAttributes(PRESIDENT_GERMANY, germanEvent);
        germanEvent.setTitleTerm(SHTANMAIER.getName());
        germanEvent.setBids(List.of(new HomeBidDTO("Yes", 1.3), new HomeBidDTO("No", 5.8)));

        HomeEventDTO usaEvent = new HomeEventDTO();
        setBasicsAttributes(PRESIDENT_USA, usaEvent);
        usaEvent.setTitleTerm(TRUMP.getName());
        usaEvent.setBids(List.of(new HomeBidDTO("Yes", 2.4), new HomeBidDTO("No", 8.7)));

        List<HomeEventDTO> expectedPage1 = List.of(usaEvent, germanEvent);
        List<HomeEventDTO> actualPage1 = eventService.getAllEventWithMostPopularBid(Event.Order.popular, FIRST_PAGE);

        List<HomeEventDTO> expectedPage2 = List.of(ukraineEvent);
        List<HomeEventDTO> actualPage2 = eventService.getAllEventWithMostPopularBid(Event.Order.popular, SECOND_PAGE);

        List<HomeEventDTO> expectedPage3 = List.of();
        List<HomeEventDTO> actualPage3 = eventService.getAllEventWithMostPopularBid(Event.Order.popular, THIRD_PAGE);

        assertEquals(expectedPage1, actualPage1);
        assertEquals(expectedPage2, actualPage2);
        assertEquals(expectedPage3, actualPage3);
    }

    @Test
    public void getAllEventWithMostPopularBidOrderedByData(){
        HomeEventDTO ukraineEvent = new HomeEventDTO();
        setBasicsAttributes(PRESIDENT_UKRAINE, ukraineEvent);
        ukraineEvent.setTitleTerm(ZELENSKII.getName());
        ukraineEvent.setBids(List.of(new HomeBidDTO("Yes", 2.0), new HomeBidDTO("No", 1.7)));

        HomeEventDTO germanEvent = new HomeEventDTO();
        setBasicsAttributes(PRESIDENT_GERMANY, germanEvent);
        germanEvent.setTitleTerm(SHTANMAIER.getName());
        germanEvent.setBids(List.of(new HomeBidDTO("Yes", 1.3), new HomeBidDTO("No", 5.8)));

        HomeEventDTO usaEvent = new HomeEventDTO();
        setBasicsAttributes(PRESIDENT_USA, usaEvent);
        usaEvent.setTitleTerm(TRUMP.getName());
        usaEvent.setBids(List.of(new HomeBidDTO("Yes", 2.4), new HomeBidDTO("No", 8.7)));

        List<HomeEventDTO> expectedPage1 = List.of(germanEvent, ukraineEvent);
        List<HomeEventDTO> actualPage1 = eventService.getAllEventWithMostPopularBid(Event.Order.date, FIRST_PAGE);

        List<HomeEventDTO> expectedPage2 = List.of(usaEvent);
        List<HomeEventDTO> actualPage2 = eventService.getAllEventWithMostPopularBid(Event.Order.date, SECOND_PAGE);

        List<HomeEventDTO> expectedPage3 = List.of();
        List<HomeEventDTO> actualPage3 = eventService.getAllEventWithMostPopularBid(Event.Order.date, THIRD_PAGE);

        assertEquals(expectedPage1, actualPage1);
        assertEquals(expectedPage2, actualPage2);
        assertEquals(expectedPage3, actualPage3);
    }

}
