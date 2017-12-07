package com.gd.ashylin.cache.server.job;

import com.gd.ashylin.cache.server.binding.request.CommandRequestBinding;
import com.gd.ashylin.cache.server.binding.request.GetRequestBinding;
import com.gd.ashylin.cache.server.binding.request.PutRequestBinding;
import com.gd.ashylin.cache.server.binding.request.RequestBinding;
import com.gd.ashylin.cache.server.binding.response.StatResponseBinding;
import com.gd.ashylin.cache.server.command.Command;
import com.gd.ashylin.cache.server.job.cachemanip.CacheManipulator;
import com.gd.ashylin.cache.server.job.cachemanip.CacheManipulatorImpl;
import com.gd.ashylin.cache.server.job.parse.JsonParser;
import com.gd.ashylin.cache.server.job.parse.Parser;
import com.gd.ashylin.cache.server.stat.Stat;
import com.gd.ashylin.cache.valid.CacheRequestValidator;
import com.gd.ashylin.cache.valid.CacheRequestValidatorImpl;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author Alexander Shylin
 */
public class CacheJobImpl implements CacheJob {

    static final Logger LOGGER = Logger.getLogger(CacheJobImpl.class);

    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;


    private CacheManipulator broker;

    private RequestBinding requestBinding;

    private Parser parser = new JsonParser();

    private CacheRequestValidator validator = new CacheRequestValidatorImpl();


    CacheJobImpl(Socket s, CacheJobExecutorImpl producer) throws IOException {
        this.socket = s;
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        this.broker = new CacheManipulatorImpl(producer);
    }

    @Override
    public void run() {
        String data;
        try {
            while ((data = in.readLine()) != null) {
                requestBinding = parser.fromStringToObject(data);

                String response = manipulateCacheDependingOnRequest();

                if (response != null && response.length() > 0) {
                    out.println(response);
                }

                break;
            }
        } catch (IOException e) {
            e.printStackTrace(out);
        } finally {
            try {
                out.close();
                in.close();
                socket.close();
            } catch (IOException e) {
                LOGGER.error("Unable to close", e);
            }
        }
    }


    private String manipulateCacheDependingOnRequest() {
        if (requestBinding instanceof PutRequestBinding) {
            return cachePutOperation();
        }

        if (requestBinding instanceof GetRequestBinding) {
            return cacheGetOperation();
        }

        if (requestBinding instanceof CommandRequestBinding) {
            return cacheCommandOperation();
        }

        return null;
    }

    private String cacheCommandOperation() {
        CommandRequestBinding commandRequestBinding = (CommandRequestBinding) requestBinding;

        validator.validateCommandRequest(commandRequestBinding.getCommand());

        switch (commandRequestBinding.getCommand()) {
            case Command.SIZE:
                return String.valueOf(broker.size());
            case Command.INVALIDATE_ALL:
                broker.removeAll();
            case Command.STATS:
                return getStatsJson();
            default:
                return null;
        }
    }

    private String cacheGetOperation() {
        GetRequestBinding getRequestBinding = (GetRequestBinding) requestBinding;

        validator.validateGetRequest(getRequestBinding.getKey(), getRequestBinding.getCommand());

        switch (getRequestBinding.getCommand()) {
            case Command.GET:
                return String.valueOf(broker.get(getRequestBinding.getKey()));
            case Command.INVALIDATE:
                broker.remove(getRequestBinding.getKey());
            default:
                return null;
        }
    }

    private String cachePutOperation() {
        PutRequestBinding putRequestBinding = (PutRequestBinding) requestBinding;

        try {
            validator.validatePutRequest(putRequestBinding.getKey(), putRequestBinding.getTtl());
        } catch (IllegalArgumentException e) {
            Stat.loadExceptionCount.incrementAndGet();
        }

        if (putRequestBinding.getTtl() == 0) {
            putRequestBinding.setTtl(CacheJobExecutor.DEFAULT_TTL);
        }

        broker.put(putRequestBinding.getKey(), putRequestBinding.getValue(), putRequestBinding.getTtl());
        return null;
    }

    private String getStatsJson() {
        StatResponseBinding statResponseBinding = new StatResponseBinding();

        statResponseBinding.setHitCount(Stat.hitCount.get());
        statResponseBinding.setMissCount(Stat.missCount.get());
        statResponseBinding.setLoadSuccessCount(Stat.loadSuccessCount.get());
        statResponseBinding.setLoadExceptionCount(Stat.loadExceptionCount.get());
        statResponseBinding.setTotalLoadTime(Stat.getTotalLoadTime());
        statResponseBinding.setEvictionCount(Stat.evictionCount.get());

        try {
            return new ObjectMapper().writeValueAsString(statResponseBinding);
        } catch (IOException e) {
            LOGGER.error("Unable to write stats as json", e);
            return null;
        }
    }
}
