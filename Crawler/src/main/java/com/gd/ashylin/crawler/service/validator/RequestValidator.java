package com.gd.ashylin.crawler.service.validator;

/**
 * @author Alexander Shylin
 */
public interface RequestValidator {
    void validateId(Long id);

    void validateGetResultParams(Long id, String sort, Integer offset, Integer pageSize);

    void validateGetScrapResultsParams(Long id, String search, String field);

    void validateLaunchParams(String url, Long delay);

    void validateLaunchParams(String url, Integer threads, Long delay);
}
