package cn.raincoding.hello.mqtt.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author zengqm01
 * @date 2022/3/3 14:26
 */
@Setter
@Getter
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;

    private Integer age;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
