package com.app.edit.config;

import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class EditRestControllerAdvice {

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public BaseResponse missingParameterHandler() {
        return new BaseResponse(BaseResponseStatus.REQUEST_PARAMETER_MISSING);
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public BaseResponse argumentTypeMismatchHandler() {
        return new BaseResponse(BaseResponseStatus.REQUEST_PARAMETER_MISMATCH);
    }
}
