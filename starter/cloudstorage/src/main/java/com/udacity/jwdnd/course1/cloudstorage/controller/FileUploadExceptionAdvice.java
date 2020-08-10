package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileUploadExceptionAdvice {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleMaxSizeException(
            MaxUploadSizeExceededException exc,
            HttpServletRequest request,
            HttpServletResponse response) {

        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.getModel().put("message", "File too large!");
        return modelAndView;
    }

    @ExceptionHandler(FileSizeLimitExceededException.class)
    public ModelAndView handleFileSizeException(
            MaxUploadSizeExceededException exc,
            HttpServletRequest request,
            HttpServletResponse response) {

        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.getModel().put("message", "File too large!");
        return modelAndView;
    }
}
