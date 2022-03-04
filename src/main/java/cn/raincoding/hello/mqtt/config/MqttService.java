package cn.raincoding.hello.mqtt.config;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.nio.charset.StandardCharsets;

/**
 * @author zengqm01
 * @date 2022/3/4 09:39
 */
@Log4j2
@AllArgsConstructor
@Component
public class MqttService {

    private final MqttClient mqttClient;

    /**
     * 向 Mqtt 发布消息
     *
     * @param mqtt Mqtt 消息对象，详见 {@link cn.raincoding.hello.mqtt.config.Mqtt}
     */
    public void publish(Mqtt<?> mqtt) {
        try {
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setPayload(mqtt.getPayloadJsonStr().getBytes(StandardCharsets.UTF_8));
            mqttMessage.setQos(mqtt.getQos());
            mqttMessage.setRetained(mqtt.isRetained());

            mqttClient.publish(mqtt.getTopic(), mqttMessage);
        } catch (MqttException e) {
            log.error("publish message: {} to mqtt topic: {} failed", mqtt.getPayloadJsonStr(), mqtt.getTopic(), e);
        }
    }

    /**
     * 订阅 Mqtt 主题
     *
     * @param topic Mqtt 主题
     * @param callback 回调监听
     */
    public void subscribe(String topic, IMqttMessageListener callback) {
        try {
            mqttClient.subscribe(topic, callback);
        } catch (MqttException e) {
            log.error("subscribe mqtt topic: {} failed", topic, e);
        }
    }

    /**
     * 系统停止时关闭 MqttClient
     */
    @PreDestroy
    public void close() {
        try {
            mqttClient.disconnect();
            mqttClient.close();
        } catch (MqttException e) {
            log.error("mqtt client close failed", e);
        }
    }

}