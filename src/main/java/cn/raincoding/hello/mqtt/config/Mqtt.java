package cn.raincoding.hello.mqtt.config;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author zengqm01
 * @date 2022/3/3 14:17
 */
@Setter
@Getter
public class Mqtt<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private String topic;

    private int qos;

    private T payload;

}
