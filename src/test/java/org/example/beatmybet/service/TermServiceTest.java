package org.example.beatmybet.service;

import org.example.beatmybet.repository.EventRepository;
import org.example.beatmybet.repository.TermRepository;
import org.example.beatmybet.repository.TermVariantRepository;
import org.example.beatmybet.repository.UserRepository;
import org.example.beatmybet.service.impl.TermServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;

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

    @BeforeEach
    public void setUp() {
        termService = new TermServiceImpl(termRepository, eventRepository, userRepository, termVariantRepository, bidService);
    }

    @Test
    void variantsByName() {

    }
}
