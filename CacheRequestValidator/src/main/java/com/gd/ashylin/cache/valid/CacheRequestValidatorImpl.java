package com.gd.ashylin.cache.valid;

import com.gd.ashylin.cache.valid.command.Command;
import com.gd.ashylin.cache.valid.util.CacheValidateUtil;
import com.gd.ashylin.cache.valid.util.CacheValidateUtilImpl;

/**
 * @author Alexander Shylin
 */
public class CacheRequestValidatorImpl implements CacheRequestValidator {

    public static final String WRONG_GET_REQUEST_COMMAND = "Command param must be 'get' or 'invalidate', but not '%s'";
    public static final String WRONG_COMMAND_REQUEST_COMMAND = "Command param must be 'stats', 'size' or 'invalidate_all', but not '%s'";


    private CacheValidateUtil validator = new CacheValidateUtilImpl();

    @Override
    public void validatePutRequest(Object key, Long ttl) {
        validator.notNull(key, ttl);
        validator.isNotNegativeNumber(ttl);
    }

    @Override
    public void validateGetRequest(Object key, String command) {
        validator.notNull(key, command);

        switch (command) {
            case Command.GET:
            case Command.INVALIDATE:
                break;
            default:
                throw new IllegalArgumentException(String.format(WRONG_GET_REQUEST_COMMAND, command));
        }
    }

    @Override
    public void validateCommandRequest(String command) {
        validator.notNull(command);

        switch (command) {
            case Command.SIZE:
            case Command.STATS:
            case Command.INVALIDATE_ALL:
                break;
            default:
                throw new IllegalArgumentException(String.format(WRONG_COMMAND_REQUEST_COMMAND, command));
        }
    }
}
