package com.github.jjfhj.config;

import org.aeonbits.owner.ConfigFactory;

public class App {

    public static final CredentialsConfig CREDENTIALS_CONFIG =
            ConfigFactory.create(CredentialsConfig.class, System.getProperties());

    public static final ApiConfig API_CONFIG = ConfigFactory.create(ApiConfig.class, System.getProperties());
}
