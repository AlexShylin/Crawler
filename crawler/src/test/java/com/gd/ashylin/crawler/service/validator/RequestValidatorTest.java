package com.gd.ashylin.crawler.service.validator;

import com.gd.ashylin.crawler.service.constant.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

/**
 * @author Alexander Shylin
 */
@RunWith(MockitoJUnitRunner.class)
public class RequestValidatorTest {

    @InjectMocks
    private RequestValidatorImpl requestValidator = new RequestValidatorImpl();

    @Mock
    private ValidatorUtil validatorUtil = mock(ValidatorUtil.class);

    @Test
    public void testValidateId() {
        requestValidator.validateId(0L);

        doNothing().when(validatorUtil).validateNumber(0L, Parameters.ID.toString());

        verify(validatorUtil, times(1)).validateNumber(0L, Parameters.ID.toString());
    }

    @Test
    public void testValidateGetResultParams() {
        requestValidator.validateGetResultParams(0L, "sort", 0, 0);

        doNothing().when(validatorUtil).validateNumber(0L, Parameters.ID.toString());
        doNothing().when(validatorUtil).validateSort("", Parameters.SORT.toString());

        verify(validatorUtil, times(1)).validateNumber(0L, Parameters.ID.toString());
        verify(validatorUtil, times(1)).validateNumber(0L, Parameters.OFFSET.toString());
        verify(validatorUtil, times(1)).validateNumber(0L, Parameters.PAGE_SIZE.toString());
        verify(validatorUtil, times(1)).validateSort("sort", Parameters.SORT.toString());
    }

    @Test
    public void testValidateGetScrapResults() {
        requestValidator.validateGetScrapResultsParams(0L, "search", "field");

        doNothing().when(validatorUtil).validateNumber(0L, Parameters.ID.toString());
        doNothing().when(validatorUtil).validateSearch("", Parameters.SEARCH.toString());
        doNothing().when(validatorUtil).validateField("", Parameters.FIELD.toString());

        verify(validatorUtil, times(1)).validateNumber(0L, Parameters.ID.toString());
        verify(validatorUtil, times(1)).validateSearch("search", Parameters.SEARCH.toString());
        verify(validatorUtil, times(1)).validateField("field", Parameters.FIELD.toString());
    }

    @Test
    public void testValidateLaunchParams() {
        requestValidator.validateLaunchParams("url", 20L);

        doNothing().when(validatorUtil).validateUrl("", Parameters.URL.toString());
        doNothing().when(validatorUtil).validateNumber(0L, Parameters.DELAY.toString());

        verify(validatorUtil, times(1)).validateNumber(20L, Parameters.DELAY.toString());
        verify(validatorUtil, times(1)).validateUrl("url", Parameters.URL.toString());
    }

    @Test
    public void testValidateLaunchParamsV2() {
        requestValidator.validateLaunchParams("url", 1,20L);

        doNothing().when(validatorUtil).validateUrl("", Parameters.URL.toString());
        doNothing().when(validatorUtil).validateNumber(1L, Parameters.THREADS.toString());
        doNothing().when(validatorUtil).validateNumber(0L, Parameters.DELAY.toString());

        verify(validatorUtil, times(1)).validateNumber(20L, Parameters.DELAY.toString());
        verify(validatorUtil, times(1)).validateNumber(1L, Parameters.THREADS.toString());
        verify(validatorUtil, times(1)).validateUrl("url", Parameters.URL.toString());
    }
}
