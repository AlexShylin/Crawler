package com.gd.ashylin.crawler.service.validator;

import com.gd.ashylin.crawler.service.constant.Parameters;

/**
 * @author Alexander Shylin
 */
public class RequestValidatorImpl implements RequestValidator {

    private ValidatorUtil validatorUtil;


    @Override
    public void validateId(Long id) {
        validatorUtil.validateNumber(id, Parameters.ID.toString());
    }

    @Override
    public void validateGetResultParams(Long id, String sort, Integer offset, Integer pageSize) {
        validatorUtil.validateNumber(id, Parameters.ID.toString());
        validatorUtil.validateSort(sort, Parameters.SORT.toString());
        validatorUtil.validateNumber(Long.valueOf(offset), Parameters.OFFSET.toString());
        validatorUtil.validateNumber(Long.valueOf(pageSize), Parameters.PAGE_SIZE.toString());
    }

    @Override
    public void validateGetScrapResultsParams(Long id, String search, String field) {
        validatorUtil.validateNumber(id, Parameters.ID.toString());
        validatorUtil.validateSearch(search, Parameters.SEARCH.toString());
        validatorUtil.validateField(field, Parameters.FIELD.toString());
    }

    @Override
    public void validateLaunchParams(String url, Long delay) {
        validatorUtil.validateUrl(url, Parameters.URL.toString());
        validatorUtil.validateNumber(delay, Parameters.DELAY.toString());
    }

    @Override
    public void validateLaunchParams(String url, Integer threads, Long delay) {
        validatorUtil.validateUrl(url, Parameters.URL.toString());
        validatorUtil.validateNumber(Long.valueOf(threads), Parameters.THREADS.toString());
        validatorUtil.validateNumber(delay, Parameters.DELAY.toString());
    }

    public void setValidatorUtil(ValidatorUtil validatorUtil) {
        this.validatorUtil = validatorUtil;
    }
}
