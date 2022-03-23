package com.example.myapp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AwsInfo {

    public static String instanceId = "";
    public static String ami = "";
    public static String instanceName = "";
    public static String keyName = "";
    public static String groupName = "";
    public static String groupDesc = "";
    public static String groupId = "";
    public static String vpcId = "";

    AwsInfo() {
        InputStream input = null;
        try {
            input = AwsInfo.class.getClassLoader().getResourceAsStream("config.properties");

            Properties prop = new Properties();

            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }

            // load a properties file from class path, inside static method
            prop.load(input);
            ami = prop.getProperty("ami");
            instanceName = prop.getProperty("instanceName");
            instanceId = prop.getProperty("masterec2");
            keyName = prop.getProperty("keyPair");
            groupName = prop.getProperty("groupName");
            groupDesc = prop.getProperty("groupDesc");
            vpcId = prop.getProperty("vpcId");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null)
                try {
                    input.close();
                } catch (IOException e) {                    
                    e.printStackTrace();
                }
        }
    }
}
