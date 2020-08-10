package com.udacity.jwdnd.course1.cloudstorage.config;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResolveException implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

            ModelAndView modelAndView = new ModelAndView("home");
//            if (e instanceof MaxUploadSizeExceededException) {
                modelAndView.getModel().put("message", "File size exceeds limit!");
//            }
            return modelAndView;
    }
}
