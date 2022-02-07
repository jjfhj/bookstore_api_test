package com.github.jjfhj.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "file:/tmp/credentials.properties",
        "classpath:config/credentials.properties"
})
public interface CredentialsConfig extends Config {

    @Key("userName")
    String userName();

    @Key("password")
    String password();
}
