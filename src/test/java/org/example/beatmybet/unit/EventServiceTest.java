package org.example.beatmybet.unit;

import org.example.beatmybet.dto.BaseEventDTO;
import org.example.beatmybet.dto.HomeBidDTO;
import org.example.beatmybet.dto.HomeEventDTO;
import org.example.beatmybet.entity.*;
import org.example.beatmybet.repository.*;
import org.example.beatmybet.service.EventService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@TestExecutionListeners(MockitoTestExecutionListener.class)
public class EventServiceTest {
    private EventService eventService;

    private EventRepository eventRepository;

    private CategoryRepository categoryRepository;

    private TermRepository termRepository;

    private BidRepository bidRepository;

    private UserRepository userRepository;

    private PostingRepository postingRepository;

    private TermVariantRepository termVariantRepository;

    private HomeEventDTO homeEventDTO;
    private static final String CONTENT_HOME_EVENT = "The president selection in Ukraine";

    private  static final String CATEGORY = "selections";

    private  static final String SUPER_CATEGORY = "politics";

    private static final String titleTerm = "The win of Zelenskii";

    private static final List<HomeBidDTO> bids = List.of(new HomeBidDTO("Yes", 1.5), new HomeBidDTO("No", 3.0));

    @Before
    public void setUp() {
        eventRepository = mock(EventRepository.class);
        categoryRepository = mock(CategoryRepository.class);
        termRepository = mock(TermRepository.class);
        bidRepository = mock(BidRepository.class);
        userRepository = mock(UserRepository.class);
        postingRepository = mock(PostingRepository.class);
        termVariantRepository = mock(TermVariantRepository.class);

        eventService = new EventService(eventRepository, categoryRepository, termRepository, bidRepository,
                userRepository, postingRepository, termVariantRepository);

 /*       Category category = new Category();
        category.setId(1L);
        category.setName("politics");

        User user = new User();
        user.setId(1L);
        user.setDate(LocalDate.now());
        user.setPassword("12345");
        user.setPhone(1234567890);
        List<Event> events = List.of(new Event(1L, category, LocalDateTime.now(), user, "President Selections",
                LocalDateTime.now(), LocalDateTime.now()));
        Term term = new Term(1L, "Zelenskii will win", List.of(new TermVariant(1L, "Yes"),new TermVariant(2L, "No")));
        events.get(0).setTerms(List.of(term));

        doReturn(events).when(eventRepository).findAll();
        doReturn(new Long[] {1L}).when(termRepository).getMostPopularTermFromTerms(List.of());
        doReturn(term).when(termRepository).findById(1L);
*/

        homeEventDTO = new HomeEventDTO();
    }



    public void getAllEventWithMostPopularBidOrderedByPopularity() {

    }

    public void getHomeEvent() {

    }

}
