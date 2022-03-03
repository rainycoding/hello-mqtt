package cn.raincoding.hello.mqtt.controller;

import cn.raincoding.hello.mqtt.config.Mqtt;
import cn.raincoding.hello.mqtt.config.MqttGateway;
import cn.raincoding.hello.mqtt.entity.User;
import cn.raincoding.hello.mqtt.util.JsonUtils;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zengqm01
 * @date 2022/3/3 14:13
 */
@RestController
@AllArgsConstructor
@RequestMapping("/mqtt")
public class MqttController {

    private final MqttGateway mqttGateway;

    @PostMapping("/sendStr")
    public String sendToMqtt(@RequestBody Mqtt<String> mqtt) {
        mqttGateway.sendToMqtt(mqtt.getTopic(), mqtt.getQos(), mqtt.getPayload());
        return "Success";
    }

    @PostMapping("/sendEntity")
    public String sendEntityToMqtt(@RequestBody Mqtt<User> mqtt) {
        mqttGateway.sendToMqtt(mqtt.getTopic(), mqtt.getQos(), JsonUtils.obj2json(mqtt.getPayload()));
        return "Success";
    }

}
