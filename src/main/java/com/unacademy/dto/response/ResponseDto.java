package com.unacademy.dto.response;

import java.util.Map;

public class ResponseDto extends BaseResponseDto {
    public ResponseDto(String message, Integer code, Map<String, Object> data) {
        super(message, code, data);
    }

    public ResponseDto(String message) {
        super(message);
    }

    public ResponseDto(String message, Integer code) {
        super(message, code);
    }

    public ResponseDto(String message, Map<String, Object> data) {
        super(message, data);
    }
}
