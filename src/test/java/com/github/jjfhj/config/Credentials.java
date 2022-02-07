package com.github.jjfhj.config;

import org.aeonbits.owner.ConfigFactory;

public class Credentials {
    public static final CredentialsConfig CREDENTIALS_CONFIG = ConfigFactory.create(CredentialsConfig.class, System.getProperties());
}
