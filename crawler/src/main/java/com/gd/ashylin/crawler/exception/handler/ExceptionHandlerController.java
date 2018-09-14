package com.gd.ashylin.crawler.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gd.ashylin.crawler.bindings.response.CrawlerErrorResponseBinding;
import com.gd.ashylin.crawler.exception.ErrorNamesCodes;
import com.gd.ashylin.crawler.exception.RelaunchCrawlerException;
import com.gd.ashylin.crawler.exception.RequestParsingException;
import com.gd.ashylin.crawler.exception.ValidationException;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Alexander Shylin
 */
@EnableWebMvc
@ControllerAdvice
public class ExceptionHandlerController implements HandlerExceptionResolver {

    static final Logger LOGGER = Logger.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler(RequestParsingException.class)
    @ResponseBody
    public CrawlerErrorResponseBinding handleRequestParingEx(RequestParsingException ex) {
        return new CrawlerErrorResponseBinding(ex.getName(), ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(RelaunchCrawlerException.class)
    @ResponseBody
    public CrawlerErrorResponseBinding handleRelaunchCrawlerEx(RelaunchCrawlerException ex) {
        return new CrawlerErrorResponseBinding(ErrorNamesCodes.OPERATION_IS_NOT_SUPPORTED_NAME,
                ErrorNamesCodes.OPERATION_IS_NOT_SUPPORTED_CODE, ex.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    public CrawlerErrorResponseBinding handleValidationEx(ValidationException ex) {
        return new CrawlerErrorResponseBinding(ex.getName(), ex.getCode(), ex.getMessage());
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        LOGGER.fatal("Uncaught binding occurred", ex);
        try {
            String json = new ObjectMapper().writeValueAsString(
                    new CrawlerErrorResponseBinding(ErrorNamesCodes.INTERNAL_SERVER_ERROR_NAME,
                            ErrorNamesCodes.INTERNAL_SERVER_ERROR_CODE,
                            ErrorNamesCodes.INTERNAL_SERVER_ERROR_MESSAGE));

            response.getWriter().print(json);
        } catch (IOException e) {
            LOGGER.error("Unable to send error json response (500)", e);
        }
        return null;
    }
}
