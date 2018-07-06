package com.pearson.glp.crosscutting.isc.config;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The AppConfigurationTest class.
 * 
 *<p>Load the properties from the file.
 *
 */
public class AppConfigurationTest {

    /**
     * AppConfigurationTest instantiate.
     */
    public AppConfigurationTest() {
        super();
    }

    /**
     * The inject mock instance variable appConfig.
     */
    @InjectMocks
    private AppConfiguration appConfig;
    /**
     * The mock instance variable env.
     */
    @Mock
    private Environment env;

    /**
     * Initialize mockito.
     */
    @BeforeClass
    public void beforeClass() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * The method test the property object null or not.
     */
    @Test
    private void propertySourcesPlaceholderConfigurerTest() {
        Assert.assertNotNull(
            this.appConfig.propertySourcesPlaceholderConfigurer());
    }

    /**
     * The method test the restTemplate null or not.
     */
    @Test
    public void testCreationOfRestTemplate() {
        RestTemplate restTemplate = this.appConfig.restTemplate();
        Assert.assertNotNull(restTemplate);
    }

    /**
     * The method test integer value.
     */
    @Test
    public void testFetchIntegerProperty() {
        Mockito.when(this.env.getProperty(
            "hystrix.command.configGet.execution.isolation.thread."
                    + "timeoutInMilliseconds"))
                .thenReturn("5000");
        int val = this.appConfig.getIntegerProperty(
            "hystrix.command.configGet.execution.isolation.thread."
                    + "timeoutInMilliseconds");
        Assert.assertEquals(val, 5000);
    }

    /**
     * The method test the integer value null or not.
     */
    @Test
    public void testFetchIntegerPropertyWhilePropertyNull() {
        Mockito.when(this.env
                .getProperty("hystrix.command.configGet.execution.isolation."
                        + "thread.timeoutInMilliseconds"))
                .thenReturn(null);
        Integer val = this.appConfig.getIntegerProperty(
            "hystrix.command.configGet.execution.isolation."
                    + "thread.timeoutInMilliseconds");
        Assert.assertNull(val);
    }
    /**
     * The method test boolean value.
     */
    @Test
    public void testFetchBooleanProperty() {
        Mockito.when(this.env.getProperty("hystrix.circuitEnabled"))
                .thenReturn("true");
        boolean val = this.appConfig.getBooleanProperty("hystrix."
                + "circuitEnabled");
        Assert.assertEquals(val, true);
    }

    /**
     * The method test boolean value null or not.
     */
    @Test
    public void testFetchBooleanPropertyWhilePropertyIsNull() {
        Mockito.when(this.env.getProperty("hystrix.circuitEnabled"))
                .thenReturn(null);
        Boolean val =
                this.appConfig.getBooleanProperty("hystrix.circuitEnabled");
        Assert.assertFalse(val);
    }
    /**
     * The method test the string value.
     */
    @Test
    public void testFetchStringProperty() {
        Mockito.when(this.env.getProperty("hystrix.executionStrategy"))
                .thenReturn("THREAD");
        String val =
                this.appConfig.getStringProperty("hystrix.executionStrategy");
        Assert.assertEquals(val, "THREAD");
    }

}
