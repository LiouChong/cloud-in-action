package com.cloud.licensingservice.pojo;

import lombok.ToString;

@ToString
public class Organization {
    String id;
    String name;
    String contactName;
    String contactEmail;
    String contactPhone;


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":\"")
                .append(id).append('\"');
        sb.append(",\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"contactName\":\"")
                .append(contactName).append('\"');
        sb.append(",\"contactEmail\":\"")
                .append(contactEmail).append('\"');
        sb.append(",\"contactPhone\":\"")
                .append(contactPhone).append('\"');
        sb.append('}');
        return sb.toString();
    }
}