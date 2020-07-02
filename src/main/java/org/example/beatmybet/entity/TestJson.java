package org.example.beatmybet.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class TestJson {
    private int id;
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // Json -> Object: name = null
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //
    private String name;
}
