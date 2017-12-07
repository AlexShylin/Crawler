package com.gd.ashylin.cache.server.job.parse;

import com.gd.ashylin.cache.server.binding.request.CommandRequestBinding;
import com.gd.ashylin.cache.server.binding.request.GetRequestBinding;
import com.gd.ashylin.cache.server.binding.request.PutRequestBinding;
import com.gd.ashylin.cache.server.binding.request.RequestBinding;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * @author Alexander Shylin
 */
public class JsonParser implements Parser {

    @Override
    public RequestBinding fromStringToObject(String data) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(data, PutRequestBinding.class);
        } catch (IOException ignored) {

        }

        try {
            return objectMapper.readValue(data, CommandRequestBinding.class);
        } catch (IOException ignored) {

        }

        try {
            return objectMapper.readValue(data, GetRequestBinding.class);
        } catch (IOException ignored) {

        }

        throw new IOException("Request object has wrong format");
    }

}
