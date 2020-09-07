package org.example.beatmybet.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;

import java.util.List;

@SpringBootTest
@TestExecutionListeners(MockitoTestExecutionListener.class)
public class MockitoLearning {

    @Mock
    List<Integer> list;

    @Test
    void test() {
        list.add(3);

        Assertions.assertEquals(1, list.size());
    }
}
