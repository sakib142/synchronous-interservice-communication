package com.pearson.glp.crosscutting.isc.config;

import java.text.Normalizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

/**
 * The AppConfiguration class.
 * 
 *<p>Load the properties from the file.
 *
 */
@Configuration
@PropertySource(value = { "classpath:config.properties" })
public class AppConfiguration {
    /**
     * AppConfiguration instantiate.
     */
    public AppConfiguration() {
        super();
    }

    /**
     * The instance variable env.
     */
    @Autowired
    private Environment env;

    /**
     * The method create Object of RestTemplate.
     * 
     * @return restTemplate object
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * The method create object of PropertySourcesPlaceholderConfigurer.
     * 
     * @return object properties
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer
           propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * Get the Property Value as Integer.
     * 
     * @param name
     *            the name
     * @return the string
     */
    public Integer getIntegerProperty(String name) {
        if (this.getString(name) != null) {
            return Integer.valueOf(this.getString(name));
        } else {
            return null;
        }
    }

    /**
     * Get the property value as Boolean.
     * 
     * @param name
     *            the name
     * @return the string
     */
    public Boolean getBooleanProperty(String name) {
        if (this.getString(name) != null) {
            return Boolean.valueOf(this.getString(name));
        } else {
            return Boolean.FALSE;
        }
    }

    /**
     * Get the property value as String.
     * 
     * @param name
     *            the name
     * @return the string
     */
    public String getStringProperty(String name) {
        return Normalizer.normalize(this.getString(name), Normalizer.Form.NFKC);
    }

    /**
     * Gets the string.
     *
     * @param name
     *            the name
     * @return the string
     */
    private String getString(String name) {
        return this.env.getProperty(name);
    }
}