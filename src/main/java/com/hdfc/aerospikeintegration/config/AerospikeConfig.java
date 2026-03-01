package com.hdfc.aerospikeintegration.config;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.policy.ClientPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AerospikeConfig {

    @Value("${aerospike.host}")
    private String host;

    @Value("${aerospike.port}")
    private int port;

    @Bean
    public AerospikeClient aerospikeClient() {
        ClientPolicy policy = new ClientPolicy();
        return new AerospikeClient(policy,host, port);
    }
}