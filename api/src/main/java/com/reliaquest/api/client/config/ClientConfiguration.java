package com.reliaquest.api.client.config;

import java.util.logging.Level;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;

public class ClientConfiguration {

    @Bean
    public Level getLogger() {
        return Level.ALL;
    }

    @Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }
}
