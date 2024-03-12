package com.github.dsmiles.awslocal;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record ApplicationProperties (String queue, String bucket) {}
