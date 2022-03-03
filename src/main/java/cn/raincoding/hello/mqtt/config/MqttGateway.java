package cn.raincoding.hello.mqtt.config;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;

/**
 * @author zengqm01
 * @date 2022/3/3 14:00
 */
@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
public interface MqttGateway {

    /**
     * 向 EMQX 发送消息
     *
     * @param topic topic，eg: testtopic/1
     * @param qos MQTT QoS 等级，<a href="https://www.emqx.com/zh/blog/introduction-to-mqtt-qos">详见</a>
     * @param payload 消息内容
     */
    void sendToMqtt(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos, String payload);

}
