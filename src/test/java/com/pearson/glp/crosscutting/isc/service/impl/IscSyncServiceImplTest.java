package com.pearson.glp.crosscutting.isc.service.impl;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.mockito.InjectMocks;
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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.pearson.glp.crosscutting.isc.config.AppConfiguration;
import com.pearson.glp.crosscutting.isc.model.RequestConfiguration;
/**
 *The IscSyncServiceImplTest class.
 */
public class IscSyncServiceImplTest {
    /**
     * IscSyncServiceImplTest instantiate.
     */
    public IscSyncServiceImplTest() {
        super();
    }
    /**
     * The mock instance variable restTemplate.
     */
    @Mock
    private RestTemplate restTemplate;
    /**
     * Logger object of class.
     */
    private final static Logger logger =
            LoggerFactory.getLogger(IscSyncServiceImplTest.class);
    /**
     * The mock instance variable appConfig.
     */
    @Mock
    private AppConfiguration appConfig;

    /**
     * The inject mock instance serviceImpl.
     */
    @InjectMocks
    private IscSyncServiceImpl serviceImpl;
    /**
     * The instance variable requestTimeoutInMiliSeconds.
     */
    private int requestTimeoutInMiliSeconds = 5000;
    /**
     * The instance variable threadPoolCoreSize.
     */
    private int threadPoolCoreSize = 5;
    /**
     * The instance variable threadPoolQueueSize.
     */
    private int threadPoolQueueSize = 5;

    /**
     * The call back function fallbackFunction.
     */
    private Consumer<String> fallbackFunction = x ->
        logger.info("data recieved.{}",x);
        
    private Map<String, String> headersMap;
   
    /**
     * Mockito initialize.    
     */
    @BeforeClass
    public void beforeClass() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Setup the mockito and mock the object.
     */
    @BeforeMethod
    public void setUpMethod() {
        Mockito.when(this.appConfig.getIntegerProperty(
                "hystrix.command.configGet.execution.isolation."
                        + "thread.timeoutInMilliseconds"))
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
     * The method test get request with request configuration.
     */
    @Test
    public void testInvokeGetWithRequestConfiguration() {
        this.restTemplate = Mockito.mock(RestTemplate.class);
        this.appConfig = Mockito.mock(AppConfiguration.class);
        HystrixRequestContext context =
                HystrixRequestContext.initializeContext();
        Mockito.when(this.restTemplate.getForObject(
                URI.create("http://localhost:8090/list"), String.class))
                .thenReturn("Gaurav, Dinesh, Jeeevan");
        try {
            this.serviceImpl.invokeGet("http://localhost:8090/list",
                    this.fallbackFunction, new RequestConfiguration(7000), headersMap);
        } finally {
            context.shutdown();
        }
    }
    /**
     * The method test get request without request configuration.
     */
    @Test
    public void testInvokeGetWithoutRequestConfiguration() {
        this.restTemplate = Mockito.mock(RestTemplate.class);
        this.appConfig = Mockito.mock(AppConfiguration.class);
        HystrixRequestContext context =
                HystrixRequestContext.initializeContext();
        Mockito.when(this.restTemplate.getForObject(
                URI.create("http://localhost:8090/list"), String.class))
                .thenReturn("Gaurav, Dinesh, Jeeevan");
        try {
            this.serviceImpl.invokeGet("http://localhost:8090/list",
                    this.fallbackFunction, headersMap);
        } finally {
            context.shutdown();
        }
    }
    /**
     * The method test post request with request configuration.
     */
    @Test
    public void testInvokePostWithRequestConfiguration() {
        this.restTemplate = Mockito.mock(RestTemplate.class);
        this.appConfig = Mockito.mock(AppConfiguration.class);
        HystrixRequestContext context =
                HystrixRequestContext.initializeContext();
        ResponseEntity<Object> myEntity = new ResponseEntity<>(
                "Dinesh,Jeevan", HttpStatus.ACCEPTED);
        Mockito.when(this.restTemplate.exchange(Mockito.<URI> any(),
                Mockito.<HttpMethod> any(), Mockito.<HttpEntity<?>> any(),
                Mockito.<Class<Object>> any())).thenReturn(myEntity);
        try {
            this.serviceImpl.invokePost("http://localhost:8090/list",
                    this.fallbackFunction, new RequestConfiguration(7000),
                    "dataToPost", headersMap);
        } finally {
            context.shutdown();
        }
    }
    /**
     * The method test post request without request configuration.
     */
    @Test
    public void testInvokePostWithoutRequestConfiguration() {
        this.restTemplate = Mockito.mock(RestTemplate.class);
        this.appConfig = Mockito.mock(AppConfiguration.class);
        HystrixRequestContext context =
                HystrixRequestContext.initializeContext();
        ResponseEntity<Object> myEntity = new ResponseEntity<>(
                "Dinesh,Jeevan", HttpStatus.ACCEPTED);
        Mockito.when(this.restTemplate.exchange(Mockito.<URI> any(),
                Mockito.<HttpMethod> any(), Mockito.<HttpEntity<?>> any(),
                Mockito.<Class<Object>> any())).thenReturn(myEntity);
        try {
            this.serviceImpl.invokePost("http://localhost:8090/list",
                    this.fallbackFunction, "dataToPost", headersMap);
        } finally {
            context.shutdown();
        }
    }
    /**
     * The method test put request with request configuration.
     */
    @Test
    public void testInvokePutWithRequestConfiguration() {
        this.restTemplate = Mockito.mock(RestTemplate.class);
        this.appConfig = Mockito.mock(AppConfiguration.class);
        HystrixRequestContext context =
                HystrixRequestContext.initializeContext();
        ResponseEntity<Object> myEntity =
                new ResponseEntity<>("Gaurav", HttpStatus.ACCEPTED);
        Mockito.when(this.restTemplate.exchange(Mockito.<URI> any(),
                Mockito.<HttpMethod> any(), Mockito.<HttpEntity<?>> any(),
                Mockito.<Class<Object>> any())).thenReturn(myEntity);
        try {
            this.serviceImpl.invokePut("http://localhost:8090/list",
                    this.fallbackFunction, new RequestConfiguration(7000),
                    "dataToPut", headersMap);
        } finally {
            context.shutdown();
        }
    }
    /**
     * The method test put request without request configuration.
     */
    @Test
    public void testInvokePutWithoutRequestConfiguration() {
        this.restTemplate = Mockito.mock(RestTemplate.class);
        this.appConfig = Mockito.mock(AppConfiguration.class);
        HystrixRequestContext context =
                HystrixRequestContext.initializeContext();
        ResponseEntity<Object> myEntity =
                new ResponseEntity<>("Gaurav", HttpStatus.ACCEPTED);
        Mockito.when(this.restTemplate.exchange(Mockito.<URI> any(),
                Mockito.<HttpMethod> any(), Mockito.<HttpEntity<?>> any(),
                Mockito.<Class<Object>> any())).thenReturn(myEntity);
        try {
            this.serviceImpl.invokePut("http://localhost:8090/list",
                    this.fallbackFunction, "dataToPut", headersMap);
        } finally {
            context.shutdown();
        }
    }
    /**
     * The method test delete request with request configuration.
     */
    @Test
    public void testInvokeDeleteWithRequestConfiguration() {
        this.restTemplate = Mockito.mock(RestTemplate.class);
        this.appConfig = Mockito.mock(AppConfiguration.class);
        HystrixRequestContext context =
                HystrixRequestContext.initializeContext();
        ResponseEntity<Object> myEntity =
                new ResponseEntity<>("ABC", HttpStatus.ACCEPTED);
        Mockito.when(this.restTemplate.exchange(Mockito.<URI> any(),
                Mockito.<HttpMethod> any(), Mockito.<HttpEntity<?>> any(),
                Mockito.<Class<Object>> any())).thenReturn(myEntity);
        try {
            this.serviceImpl.invokeDelete("http://localhost:8090/list",
                    this.fallbackFunction, new RequestConfiguration(7000), headersMap);
        } finally {
            context.shutdown();
        }
    }
    /**
     * The method test delete request without request configuration.
     */
    @Test
    public void testInvokeDeleteWithoutRequestConfiguration() {
        this.restTemplate = Mockito.mock(RestTemplate.class);
        HystrixRequestContext context =
                HystrixRequestContext.initializeContext();
        ResponseEntity<Object> myEntity =
                new ResponseEntity<>("ABC", HttpStatus.ACCEPTED);
        Mockito.when(this.restTemplate.exchange(Mockito.<URI> any(),
                Mockito.<HttpMethod> any(), Mockito.<HttpEntity<?>> any(),
                Mockito.<Class<Object>> any())).thenReturn(myEntity);
        try {
            this.serviceImpl.invokeDelete("http://localhost:8090/list",
                    this.fallbackFunction, headersMap);
        } finally {
            context.shutdown();
        }
    }

}
