package com.unacademy.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Created by OmiD.HaghighatgoO on 8/21/2019.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("UnacademyUser")
public class UnacademyUser implements Serializable{
    @Id
    private String email;

    private String mobileNumber;
    private String firstName;
    private String surname;
    private String age;
    private List<String> course;
    private String occupation;
    private double score;

    public UnacademyUser(String firstName, String surname, String age) {

        this.firstName = firstName;
        this.surname = surname;
        this.age = age;
    }
}
