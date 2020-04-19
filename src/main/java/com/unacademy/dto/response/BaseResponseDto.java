package com.unacademy.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public abstract class BaseResponseDto {
    private String message;
    private Integer code;
    private Map<String,Object> data;

    public BaseResponseDto(String message) {
        this.message = message;
    }
    public BaseResponseDto(String message, int code) {
        this.message=message;
        this.code=code;
    }
    public BaseResponseDto(String message , Map<String,Object> data) {
        this.message = message;
        this.data = data;
    }

}
