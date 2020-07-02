package org.example.beatmybet.controllers;

import lombok.Getter;
import org.aspectj.weaver.ast.Test;
import org.example.beatmybet.entity.TestJson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/json")
public class JsonTestController {

    @GetMapping
    public ResponseEntity<TestJson> get(){
        TestJson testJson = new TestJson(1, "Hello");
        return ResponseEntity.ok(testJson);
    }
    @GetMapping("/getElse")
    public TestJson getWithoutResponseEntity(){
        return new TestJson(1, "Bye");
    }
    @PostMapping
    public TestJson post(@RequestBody TestJson testJson){
        System.out.println(testJson);
        return testJson;
    }
}
