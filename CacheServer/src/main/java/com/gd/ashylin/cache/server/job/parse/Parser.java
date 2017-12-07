package com.gd.ashylin.cache.server.job.parse;

import com.gd.ashylin.cache.server.binding.request.RequestBinding;

import java.io.IOException;

/**
 * @author Alexander Shylin
 */
public interface Parser {
    /**
     * Converts string representation of the object into object.
     * <p>
     * JSON template examples:
     * <p>
     * {@code PutRequestBinding} template
     * {
     * "key": "key_string",
     * "value": "value_string",
     * "ttl": 2500
     * }
     * <p>
     * <p>
     * {@code GetRequestBinding} template
     * {
     * "key": "key_string",
     * "command": "get | invalidate"
     * }
     * <p>
     * <p>
     * {@code CommandRequestBinding} template
     * {
     * "command": "invalidate_all | size | stats"
     * }
     *
     * @param data string representation of the object
     * @return converted object
     * @throws IOException if string representation doesn't match any object
     */
    RequestBinding fromStringToObject(String data) throws IOException;
}
