package com.thc.basespr.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//2024-07-03 추가(클래스 처음 추가함)
/**
 *  토큰이 정상적이지 않을 때 사용되는 예외처리
 *  HttpStatus 401
 */
@ResponseStatus(value=HttpStatus.UNAUTHORIZED)
@SuppressWarnings("serial")
@NoArgsConstructor
public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String message) {
        super(message);
    }
}
