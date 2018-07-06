
package com.pearson.glp.crosscutting.isc.service;

import java.util.Map;
import java.util.function.Consumer;

import com.pearson.glp.crosscutting.isc.model.RequestConfiguration;

/**
 * The IscSyncService interface.
 *
 */
public interface IscSyncService {
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
	public String invokeGet(String serviceUrl, Consumer<String> fallbackFunction,
			RequestConfiguration requestConfigurations, Map<String, String> headersMap);

	/**
	 * Invoke get method.
	 * 
	 * @param serviceUrl
	 *            url value
	 * @param fallbackFunction
	 *            call back function
	 * @return string response
	 */
	public String invokeGet(String serviceUrl, Consumer<String> fallbackFunction, Map<String, String> headersMap);

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
	public String invokePost(String serviceUrl, Consumer<String> fallbackFunction,
			RequestConfiguration requestConfigurations, String jsonData, Map<String, String> headersMap);

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
	public String invokePost(String serviceUrl, Consumer<String> fallbackFunction, String jsonData,
			Map<String, String> headersMap);

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
	public String invokePut(String serviceUrl, Consumer<String> fallbackFunction,
			RequestConfiguration requestConfigurations, String jsonData, Map<String, String> headersMap);

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
	public String invokePut(String serviceUrl, Consumer<String> fallbackFunction, String jsonData,
			Map<String, String> headersMap);

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
	public String invokeDelete(String serviceUrl, Consumer<String> fallbackFunction,
			RequestConfiguration requestConfigurations, Map<String, String> headersMap);

	/**
	 * Invoke delete method.
	 * 
	 * @param serviceUrl
	 *            url value
	 * @param fallbackFunction
	 *            call back function
	 * @return string response
	 */
	public String invokeDelete(String serviceUrl, Consumer<String> fallbackFunction, Map<String, String> headersMap);

}
