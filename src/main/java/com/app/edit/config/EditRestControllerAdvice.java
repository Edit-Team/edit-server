package com.app.edit.config;

import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EditRestControllerAdvice {

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public BaseResponse missingParameterHandler() {
        return new BaseResponse(BaseResponseStatus.REQUEST_PARAMETER_MISSING);
    }
}
