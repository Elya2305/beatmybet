package org.example.beatmybet.service;


import org.example.beatmybet.repository.CategoryRepository;
import org.example.beatmybet.repository.EventRepository;
import org.example.beatmybet.repository.UserRepository;
import org.example.beatmybet.service.impl.EventServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;

import static org.mockito.Mockito.mock;

@SpringBootTest
//@RunWith(MockitoJUnitRunner.class)
@TestExecutionListeners(MockitoTestExecutionListener.class)
public class EventServiceTest {
    private EventService eventService;

    private static final int SIZE_OF_PAGE = 2;

    private TermService termService;

    private BidService bidService;

    private EventRepository eventRepository;

    private CategoryRepository categoryRepository;

    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        termService = mock(TermService.class);
        bidService = mock(BidService.class);
        eventRepository = mock(EventRepository.class);
        categoryRepository = mock(CategoryRepository.class);
        userRepository = mock(UserRepository.class);


        eventService = new EventServiceImpl(termService, bidService, eventRepository, categoryRepository, userRepository);
    }


    public void getAllEventWithMostPopularBidOrderedByPopularity() {

    }

    public void getHomeEvent() {

    }

}
