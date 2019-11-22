package com.cloud.licensingservice.enums;

/**
 * Author: Liuchong
 * Description:
 * date: 2019/11/22 11:24
 */
public class KafkaEnum {
    public enum GroupEnum {
        ORGANIZATION("organization");

        private String name;

        GroupEnum(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
