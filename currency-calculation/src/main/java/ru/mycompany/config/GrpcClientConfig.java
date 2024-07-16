package ru.mycompany.config;

import net.devh.boot.grpc.client.config.GrpcChannelProperties;
import net.devh.boot.grpc.client.config.GrpcChannelsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClientConfig {

    @Bean
    public GrpcChannelProperties currencyRateGrpcServiceChannel(GrpcChannelsProperties properties) {
        GrpcChannelProperties channelProperties = new GrpcChannelProperties();
        channelProperties.setAddress("static://localhost:9090"); // Убедитесь, что адрес и порт правильные
        properties.getClient().put("currencyRateGrpcService", channelProperties);
        return channelProperties;
    }
}
