/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.search.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationUtils {

    public static final String CONFIG_FILE				= "/application.properties";
    public static ConfigurationUtils instance 			= new ConfigurationUtils();
    public static Properties PROPERTIES = null;
    
    /**
     * Get configuration of this application.
     *
     * @return properties
     */
    public static Properties getConfig() {
        if ( PROPERTIES == null ) {
            InputStream is = ConfigurationUtils.class.getResourceAsStream(CONFIG_FILE);
            PROPERTIES = new Properties();
            try {
                PROPERTIES.load(is);
            } catch ( IOException ioe ) {
            } // end if
        } // end if

        return PROPERTIES;
    } // end of getConfig()


    /**
     * Get config property value.
     *
     * @param key   key
     * @return      value
     */
    public static String get(String key) {
        return getConfig().getProperty( key );
    }
    
    
    public static ConfigurationUtils getInstance(){
    	
    	return instance;
    }
}
