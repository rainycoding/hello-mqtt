package cn.raincoding.hello.mqtt.controller;

import cn.raincoding.hello.mqtt.config.Mqtt;
import cn.raincoding.hello.mqtt.config.MqttService;
import cn.raincoding.hello.mqtt.entity.User;
import cn.raincoding.hello.mqtt.util.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zengqm01
 * @date 2022/3/3 14:13
 */
@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping("/mqtt")
public class MqttController {

    private final MqttService mqttService;

    @PostMapping("/sendStr")
    public String sendToMqtt(@RequestBody Mqtt<String> mqtt) {
        mqttService.subscribe(mqtt.getTopic(), (topic, message) -> {
            log.info(new String(message.getPayload()));
        });
        mqttService.publish(mqtt);
        return "Success";
    }

    @PostMapping("/sendEntity")
    public String sendEntityToMqtt(@RequestBody Mqtt<User> mqtt) {
        mqttService.subscribe(mqtt.getTopic(), (topic, message) -> {
            String s = new String(message.getPayload());
            log.info(JsonUtils.json2obj(s, User.class));
        });
        mqttService.publish(mqtt);
        return "Success";
    }

}
