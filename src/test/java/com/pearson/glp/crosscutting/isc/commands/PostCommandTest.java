package com.pearson.glp.crosscutting.isc.commands;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.pearson.glp.crosscutting.isc.config.AppConfiguration;
import com.pearson.glp.crosscutting.isc.model.RequestConfiguration;
/**
 * The InvokePostCommandTest class.
 * 
 *<p>This test class test post request.
 *
 *<p>Handle the fall back of http request.
 *
 *<p>Implement circuit breaker , timeout and bulkhead.
 * 
 *
 */
public class PostCommandTest {
    /**
     * InvokePostCommandTest instantiate.  
     */
    public PostCommandTest() {
        super();
    }
    /**
     * Logger instance variable of class.
     */
    private final static Logger logger =
            LoggerFactory.getLogger(PostCommandTest.class);
    /**
     * The mock instance variable restTemplate.
     */
    @Mock
    private RestTemplate restTemplate;
    /**
     * The mock instance variable appConfig.
     */
    @Mock
    private AppConfiguration appConfig;
    /**
     * The  instance variable requestTimeoutInMiliSeconds.
     */
    private int requestTimeoutInMiliSeconds = 5000;
    /**
     * The  instance variable threadPoolCoreSize.
     */
    private int threadPoolCoreSize = 5;
    /**
     * The  instance variable threadPoolQueueSize.
     */
    private int threadPoolQueueSize = 5;
    /**
     * The fall back operation perform.
     */
    private Consumer<String> fallbackFunction =
            x -> logger.info("data recieved. {}", x);
    
    private Map<String, String> headersMap;
    /**
     * The method initialize the mockito.
     */
    @BeforeClass
    public void beforeClass() {
        MockitoAnnotations.initMocks(this);
    }
    /**
     * Setup and mocking required the object.
     */
    @BeforeMethod
    public void setUpMethod() {
        Mockito.when(this.appConfig.getIntegerProperty(
            "hystrix.command.configGet.execution.isolation.thread."
                    + "timeoutInMilliseconds"))
                .thenReturn(this.requestTimeoutInMiliSeconds);
        Mockito.when(this.appConfig
                .getIntegerProperty("hystrix.threadpool.configGet.coreSize"))
                .thenReturn(this.threadPoolCoreSize);
        Mockito.when(this.appConfig.getIntegerProperty(
            "hystrix.threadpool.configGet.queueSizeRejectionThreshold"))
                .thenReturn(this.threadPoolQueueSize);
        this.headersMap = new HashMap<>();
        headersMap.put("abc", "abc");
    }

    /**
     * The method test the properties initialization.
     */
    @Test
    public void
           testWithHystrixPropertiesInitializationForPostMethod() {
        this.restTemplate = Mockito.mock(RestTemplate.class);
        HystrixRequestContext context =
                HystrixRequestContext.initializeContext();
        ResponseEntity<Object> myEntity =
                new ResponseEntity<>("Dinesh,Jeevan", HttpStatus.ACCEPTED);
        Mockito.when(this.restTemplate.exchange(Mockito.<URI> any(),
            Mockito.<HttpMethod> any(), Mockito.<HttpEntity<?>> any(),
            Mockito.<Class<Object>> any())).thenReturn(myEntity);
        try {
            String result = new PostCommand(this.restTemplate,
                    "http://localhost:8090/list", this.fallbackFunction,
                    new RequestConfiguration(7000), "dataToPost",
                    this.appConfig,headersMap).execute();
            Assert.assertEquals(result, "Dinesh,Jeevan");
        } finally {
            context.shutdown();
        }
    }
    /**
     * The method test fall back scenario .
     */
    @Test
    public void testFallbackScenerio() {
        this.restTemplate = Mockito.mock(RestTemplate.class);
        HystrixRequestContext context =
                HystrixRequestContext.initializeContext();
        Mockito.when(this.restTemplate.exchange(Mockito.<URI> any(),
            Mockito.<HttpMethod> any(), Mockito.<HttpEntity<?>> any(),
            Mockito.<Class<Object>> any())).thenThrow(new RuntimeException());
        try {
            String result = new PostCommand(this.restTemplate,
                    "http://localhost:8090/list", this.fallbackFunction,
                    new RequestConfiguration(5000, 7, 8), "dataToPost",
                    this.appConfig, headersMap).execute();
            Assert.assertEquals(result, "");
        } finally {
            context.shutdown();
        }
    }
}
