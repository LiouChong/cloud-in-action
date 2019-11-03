package com.cloud.licensingservice.pojo;


import lombok.Data;

@Data
public class License {
    private String id;
    private String produceName;
    private String licenseType;
    private String organizationId;

}
