package com.li.csmall.common.ex.handler;

import com.li.csmall.common.ex.ServiceException;
import com.li.csmall.common.web.JsonResult;
import com.li.csmall.common.web.State;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



import java.util.List;


@RestControllerAdvice
public class GlobalExceptionHandler {

    public GlobalExceptionHandler() {
        System.out.println("GlobalExceptionHandler.GlobalExceptionHandler");
    }

    @ExceptionHandler({ServiceException.class})
    public JsonResult<Void> handleServiceException(ServiceException ex){
        return JsonResult.fail(ex.getState(),ex.getMessage());
    }
    
    @ExceptionHandler(BindException.class)
    public JsonResult<Void> handleBindException(BindException ex){
        List<FieldError> fieldError = ex.getBindingResult().getFieldErrors();
        StringBuilder stringBuilder = new StringBuilder();
        for (FieldError error : fieldError) {
            stringBuilder.append("; ");
            stringBuilder.append(error);
        }
        String message = stringBuilder.substring(2);
        return JsonResult.fail(State.ERR_BAD_REQUEST,message);
    }
}
