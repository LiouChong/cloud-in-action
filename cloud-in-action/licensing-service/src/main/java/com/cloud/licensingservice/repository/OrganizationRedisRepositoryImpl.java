package com.cloud.licensingservice.repository;


import com.alibaba.fastjson.JSONObject;
import com.cloud.licensingservice.pojo.Organization;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
public class OrganizationRedisRepositoryImpl implements OrganizationRedisRepository {
    private static final String HASH_NAME ="org";

    private RedisTemplate<String, Organization> redisTemplate;
    private HashOperations<String, String, String> hashOperations;

    public OrganizationRedisRepositoryImpl(){
        super();
    }

    @Autowired
    private OrganizationRedisRepositoryImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }


    @Override
    public void saveOrganization(Organization org) {
        hashOperations.put(HASH_NAME, org.getId(), JSONObject.toJSONString(org));
    }

    @Override
    public void updateOrganization(Organization org) {
        hashOperations.put(HASH_NAME, org.getId(), JSONObject.toJSONString(org));
    }

    @Override
    public void deleteOrganization(String organizationId) {
        hashOperations.delete(HASH_NAME, organizationId);
    }

    @Override
    public Organization findOrganization(String organizationId) {
        return JSONObject.toJavaObject(JSONObject.parseObject(hashOperations.get(HASH_NAME, organizationId)), Organization.class);
    }

    public static void main(String[] args) {
        String s = "{\"contactEmail\":\"cd\",\"contactName\":\"cd\",\"contactPhone\":\"cd\",\"id\":\"lc3\",\"name\":\"102aae36-83f8-4c88-88aa-3f7ba78abda4\"}";
        Organization organization = JSONObject.toJavaObject(JSONObject.parseObject(s), Organization.class);
        System.out.println(organization);
    }
}
