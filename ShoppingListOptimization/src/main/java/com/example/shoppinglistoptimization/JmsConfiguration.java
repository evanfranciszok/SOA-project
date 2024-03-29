package com.example.shoppinglistoptimization;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jms.annotation.EnableJms;

@EnableJms
@Configuration
public class JmsConfiguration {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(JmsConfiguration.class);

    @Value("${activemq.broker-url}")
    private String brokerUrl;

    @Value("${activemq.broker-user}")
    private String brokerUsername;

    @Value("${activemq.broker-password}")
    private String brokerPassword;

    private final ResourceLoader resourceLoader;

    @Autowired
    public JmsConfiguration(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * Connect JMS to an external ActiveMQ session, based on the active.broker-url of application.properties
     */
    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();

        activeMQConnectionFactory.setBrokerURL(brokerUrl);

        log.info("Connect to ActiveMQ host: {}", brokerUrl);

        if(brokerUsername != null && !brokerUsername.isEmpty() && brokerPassword != null && !brokerPassword.isEmpty()) {
            activeMQConnectionFactory.setUserName(brokerUsername);
            activeMQConnectionFactory.setPassword(brokerPassword);
        }

        return activeMQConnectionFactory;
    }
}