package com.pearson.glp.crosscutting.isc.commands;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.pearson.glp.crosscutting.isc.config.AppConfiguration;
import com.pearson.glp.crosscutting.isc.model.RequestConfiguration;

/**
 * The InvokePutCommand class.
 * 
 *<p>This class invoke put request.
 *
 *<p>Handle the fall back of http request.
 *
 *<p>Implement circuit breaker , timeout and bulkhead.
 * 
 *
 */
public class PutCommand extends HystrixCommand<String> {
    /**
     * The constant variable COMMAND_GROUP.
     */
    private static final String COMMAND_GROUP = "SyncServiceCommand";
    /**
     * The instance variable restTemplate.
     */
    private RestTemplate restTemplate;
    /**
     * The instance variable fallbackFunction.
     */
    private Consumer<String> fallbackFunction;
    /**
     * The instance variable serviceUrl.
     */
    private String serviceUrl;
    /**
     * The instance variable jsonData.
     */
    private String jsonData;
    
    /**
     * The instance variable HeaderMap.
     */
    private Map<String, String> headersMap = new HashMap<>();

    /**
     * The hystrix invoke put request.
     * 
     * @param restTemplate
     *            http rest client
     * @param serviceUrl
     *            http url
     * @param fallbackFunction
     *            callback function
     * @param reqConfigs
     *            request configuration
     * @param jsonData
     *            post data
     * @param appConfig
     *            application configuration
     */
    public PutCommand(RestTemplate restTemplate, String serviceUrl,
            Consumer<String> fallbackFunction, RequestConfiguration reqConfigs,
            String jsonData, AppConfiguration appConfig, Map<String, String> headersMap) {
        super(Setter
                .withGroupKey(
                    HystrixCommandGroupKey.Factory.asKey(COMMAND_GROUP))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionTimeoutInMilliseconds(
                            reqConfigs.getTimoutInMiliseconds() > 0
                                    ? reqConfigs.getTimoutInMiliseconds()
                                    : appConfig.getIntegerProperty(
                                        "hystrix.command.configPut."
                                             + "execution.isolation."
                                             + "thread.timeoutInMilliseconds"))
                        .withExecutionIsolationStrategy(
                    HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)
                        .withCircuitBreakerEnabled(true)
                        .withExecutionTimeoutEnabled(true)
                        .withFallbackEnabled(true))
                .andThreadPoolPropertiesDefaults(
                    HystrixThreadPoolProperties.Setter()
                            .withCoreSize(reqConfigs.getThreadPoolCoreSize() > 0
                                    ? reqConfigs.getThreadPoolCoreSize()
                                    : appConfig.getIntegerProperty(
                                       "hystrix.threadpool.configPut.coreSize"))
                            .withQueueSizeRejectionThreshold(
                                reqConfigs.getThreadPoolQueueSize() > 0
                                        ? reqConfigs.getThreadPoolQueueSize()
                                        : appConfig.getIntegerProperty(
                                            "hystrix.threadpool."
                                            + "configPut."+
                                            "queueSizeRejectionThreshold"))));
        this.fallbackFunction = fallbackFunction;
        this.serviceUrl = serviceUrl;
        this.restTemplate = restTemplate;
        this.jsonData = jsonData;
        this.headersMap=headersMap;
    }
    /**
     * Execute the run method.
     * 
     *<p>Send http request using RestTemplate.
     */
    @Override
    protected String run() throws Exception {
        HttpEntity<String> request =
                new HttpEntity<>(this.jsonData, this.getHttpHeaders());
        return this.restTemplate.exchange(URI.create(this.serviceUrl),
            HttpMethod.PUT, request, String.class).getBody();
    }
    /**
     * Execute the getFallBack method.
     * 
     *<p>Accept the callback function.
     */
    @Override
    protected String getFallback() {
        this.fallbackFunction.accept(this.jsonData);
        return "";
    }

    /**
     * The method return http header.
     * 
     * @return object of HttpHeaders
     */
    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headersMap.forEach((key,value) -> headers.set(key, value));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
