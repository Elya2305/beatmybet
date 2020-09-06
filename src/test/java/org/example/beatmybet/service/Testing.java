package org.example.beatmybet.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;

@SpringBootTest
//@RunWith(MockitoJUnitRunner.class)
@TestExecutionListeners(MockitoTestExecutionListener.class)
public class Testing {

    @BeforeEach
    void initBeforeEach(){
        System.out.println("init before each");
    }

    @BeforeAll
    static void initBeforeAll() {
        System.out.println("init before all");
    }
/*
    @Test
    void test1(){
        System.out.println("test 1");
    }

    @Test
    void test2(){
        System.out.println("test 3");
    }

    @Test
    void test3(){
        System.out.println("test 3");
    }*/
}
