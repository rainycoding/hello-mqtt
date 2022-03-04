package cn.raincoding.hello.mqtt.config;

import lombok.Getter;
import lombok.Setter;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

/**
 * @author zengqm01
 * @date 2022/3/3 12:17
 */
@Setter
@Getter
@Configuration
@IntegrationComponentScan
@ConfigurationProperties(prefix = "spring.mqtt")
public class MqttConfiguration {

    private String serverUrl;

    private String clientId;

    private String username;

    private String password;

    private String defaultTopic;

    private int timeout = 100;

    private int keepAlive = 100;

    @Bean
    public MqttConnectOptions mqttConnectOptions() {
        MqttConnectOptions mqttOptions = new MqttConnectOptions();
        mqttOptions.setServerURIs(new String[] {serverUrl});
        mqttOptions.setUserName(username);
        mqttOptions.setPassword(password.toCharArray());
        mqttOptions.setConnectionTimeout(timeout);
        mqttOptions.setKeepAliveInterval(keepAlive);
        mqttOptions.setAutomaticReconnect(true);
        return mqttOptions;
    }

    @Bean
    public MqttClient mqttClient(MqttConnectOptions mqttConnectOptions) throws MqttException {
        MqttClient mqttClient = new MqttClient(mqttConnectOptions.getServerURIs()[0], clientId);
        mqttClient.connect(mqttConnectOptions);
        return mqttClient;
    }

}
