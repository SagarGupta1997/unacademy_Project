package com.unacademy.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UnacademyUserRequest {

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String surname;

    @JsonProperty("age")
    private String age;

    @JsonProperty("course")
    private List<String> course;

    @JsonProperty("occupation")
    private String occupation;

}

