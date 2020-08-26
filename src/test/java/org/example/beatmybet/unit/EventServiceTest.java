package org.example.beatmybet.unit;

import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@TestExecutionListeners(MockitoTestExecutionListener.class)
public class EventServiceTest {

}
