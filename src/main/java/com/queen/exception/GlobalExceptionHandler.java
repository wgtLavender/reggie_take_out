package com.queen.exception;

import com.queen.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author
 * @date 2022/12/07/22:00
 */

@RestControllerAdvice(annotations = {RestController.class, Controller.class})
//@ResponseBody
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(value = CustomException.class)
    public R exceptionHandler(CustomException customException) {

        return R.error(customException.getMessage());


    }


}
