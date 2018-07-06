
package com.pearson.glp.crosscutting.isc.service.impl;

import java.util.Map;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pearson.glp.crosscutting.isc.commands.DeleteCommand;
import com.pearson.glp.crosscutting.isc.commands.GetCommand;
import com.pearson.glp.crosscutting.isc.commands.PostCommand;
import com.pearson.glp.crosscutting.isc.commands.PutCommand;
import com.pearson.glp.crosscutting.isc.config.AppConfiguration;
import com.pearson.glp.crosscutting.isc.model.RequestConfiguration;
import com.pearson.glp.crosscutting.isc.service.IscSyncService;
/**
 * The IscSyncServiceImpl class.
 */
@Service
public class IscSyncServiceImpl implements IscSyncService {
    
    /**
     * IscSyncServiceImpl instantiate.
     */
    public IscSyncServiceImpl() {
        super();
    }

    /**
     * Logger instance of class.
     */
    private final static Logger logger =
            LoggerFactory.getLogger(IscSyncServiceImpl.class);
    /**
     * The instance variable restTemplate.
     */
    @Autowired
    private RestTemplate restTemplate;
    /**
     * The instance variable appConfig.
     */
    @Autowired
    private AppConfiguration appConfig;
    /**
     * Invoke get method.
     * 
     * @param serviceUrl
     *            url value
     * @param fallbackFunction
     *            call back function
     * @param requestConfigurations
     *            configuration value
     * @return string response
     */
    @Override
    public String invokeGet(String serviceUrl,
            Consumer<String> fallbackFunction,
            RequestConfiguration requestConfigurations, Map<String, String> headersMap) {
        logger.info("GET request received for {} to process "
                + " with customized Hysterix configuration...", serviceUrl);
        GetCommand invokeGetCommand = new GetCommand(
                this.restTemplate, serviceUrl, fallbackFunction,
                requestConfigurations, this.appConfig, headersMap);
        return invokeGetCommand.execute();
    }
    /**
     * Invoke get method.
     * 
     * @param serviceUrl
     *            url value
     * @param fallbackFunction
     *            call back function
     * @return string response
     */    @Override
    public String invokeGet(String serviceUrl,
            Consumer<String> fallbackFunction, Map<String, String> headersMap) {
        logger.info("GET request received for {} to process "
                + "without customized Hysterix configuration...", serviceUrl);
        GetCommand invokeGetCommand = new GetCommand(
                this.restTemplate, serviceUrl, fallbackFunction,
                new RequestConfiguration(0, 0, 0), this.appConfig, headersMap);
        return invokeGetCommand.execute();
    }
     /**
      * Invoke post method.
      * 
      * @param serviceUrl
      *            url value
      * @param fallbackFunction
      *            call back function
      * @param requestConfigurations
      *            configuration value
      * @param jsonData
      *            post data value
      * @return string response
      */
    @Override
    public String invokePost(String serviceUrl,
            Consumer<String> fallbackFunction,
            RequestConfiguration requestConfigurations, String jsonData, Map<String, String> headersMap) {
        logger.info("POST request received for {} to process "
                + "with customized Hysterix configuration...", serviceUrl);
        PostCommand invokePostCommand = new PostCommand(
                this.restTemplate, serviceUrl, fallbackFunction,
                requestConfigurations, jsonData, this.appConfig, headersMap);
        return invokePostCommand.execute();
    }
    /**
     * Invoke post method.
     * 
     * @param serviceUrl
     *            url value
     * @param fallbackFunction
     *            call back function
     * @param jsonData
     *            post data value
     * @return string response
     */
    @Override
    public String invokePost(String serviceUrl,
            Consumer<String> fallbackFunction, String jsonData, Map<String, String> headersMap) {
        logger.info("POST request received for {} to process "
                + "without customized Hysterix configuration...", serviceUrl);
        PostCommand invokePostCommand = new PostCommand(
                this.restTemplate, serviceUrl, fallbackFunction,
                new RequestConfiguration(0, 0, 0), jsonData, this.appConfig, headersMap);
        return invokePostCommand.execute();
    }
    /**
     * Invoke put method.
     * 
     * @param serviceUrl
     *            url value
     * @param fallbackFunction
     *            call back function
     * @param requestConfigurations
     *            configuration value
     * @param jsonData
     *            post data value
     * @return string response
     */
    @Override
    public String invokePut(String serviceUrl,
            Consumer<String> fallbackFunction,
            RequestConfiguration requestConfigurations, String jsonData, Map<String, String> headersMap) {
        logger.info("PUT request received for {} to process "
                + "with customized Hysterix configuration...", serviceUrl);
        PutCommand invokePutCommand = new PutCommand(
                this.restTemplate, serviceUrl, fallbackFunction,
                requestConfigurations, jsonData, this.appConfig, headersMap);
        return invokePutCommand.execute();
    }
    /**
     * Invoke put method.
     * 
     * @param serviceUrl
     *            url value
     * @param fallbackFunction
     *            call back function
     * @param jsonData
     *            post data value
     * @return string response
     */
    @Override
    public String invokePut(String serviceUrl,
            Consumer<String> fallbackFunction, String jsonData, Map<String, String> headersMap) {
        logger.info("PUT request received for {} to process "
                + " without customized Hysterix configuration...", serviceUrl);
        PutCommand invokePutCommand = new PutCommand(
                this.restTemplate, serviceUrl, fallbackFunction,
                new RequestConfiguration(0, 0, 0), jsonData, this.appConfig, headersMap);
        return invokePutCommand.execute();
    }
    /**
     * Invoke delete method.
     * 
     * @param serviceUrl
     *            url value
     * @param fallbackFunction
     *            callback function
     * @param requestConfigurations
     *            configuration value
     * @return string response
     */
    @Override
    public String invokeDelete(String serviceUrl,
            Consumer<String> fallbackFunction,
            RequestConfiguration requestConfigurations, Map<String, String> headersMap) {
        logger.info("DELETE request received for {} to process "
                + " with customized Hysterix configuration...", serviceUrl);
        DeleteCommand invokeDeleteCommand = new DeleteCommand(
                this.restTemplate, serviceUrl, fallbackFunction,
                requestConfigurations, this.appConfig, headersMap);
        return invokeDeleteCommand.execute();
    }
    /**
     * Invoke delete method.
     * 
     * @param serviceUrl
     *            url value
     * @param fallbackFunction
     *            call back function
     * @return string response
     */
    @Override
    public String invokeDelete(String serviceUrl,
            Consumer<String> fallbackFunction, Map<String, String> headersMap) {
        logger.info("DELETE request received for {} to process "
                + " without customized Hysterix configuration...", serviceUrl);
        DeleteCommand invokeDeleteCommand = new DeleteCommand(
                this.restTemplate, serviceUrl, fallbackFunction,
                new RequestConfiguration(0, 0, 0), this.appConfig, headersMap);
        return invokeDeleteCommand.execute();
    }
}
