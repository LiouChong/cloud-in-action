package com.cloud.licensingservice.service;

import com.cloud.licensingservice.config.ServiceConfig;
import com.cloud.licensingservice.controller.LicenseServiceController;
import com.cloud.licensingservice.license.OrganizationDiscoverClient;
import com.cloud.licensingservice.license.OrganizationFeignClient;
import com.cloud.licensingservice.license.OrganizationRestTemplateClient;
import com.cloud.licensingservice.pojo.License;
import com.cloud.licensingservice.pojo.Organization;
import com.cloud.licensingservice.repository.LicenseRepository;
import com.cloud.licensingservice.util.UserContextHolder;
import com.netflix.discovery.converters.Auto;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Author: Liuchong
 * Description:
 * date: 2019/11/4 11:34
 */
@Service
public class LicenseService {
    private static final Logger logger = LoggerFactory.getLogger(LicenseServiceController.class);

    @Autowired
    private LicenseRepository licenseRepository;
    @Autowired
    ServiceConfig config;
    @Autowired
    OrganizationFeignClient organizationFeignClient;
    @Autowired
    OrganizationRestTemplateClient organizationRestClient;

    @Autowired
    OrganizationDiscoverClient organizationDiscoveryClient;

    public License getLicense(String organizationId, String licenseId) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        license.setProductName(license.getProductName() + config.getExamplePropertty());
        return license;
    }

    public void saveLicense(License license) {
        license.setOrganizationId(UUID.randomUUID().toString());
        licenseRepository.save(license);
    }

    public License getLicense(String organizationId, String licenseId, String clientType) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);

        Organization org = retrieveOrgInfo(organizationId, clientType);
        license.setOrganizationId(org.toString());
        return license;
    }
    @HystrixCommand(
            commandProperties = {
                    // 定制化hystrix
                    // 设置断路器超时时间
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),

                    // 10秒最小调用次数
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
                    // 跳闸必须达到的调用失败的百分比
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "75"),
                    // 断路器跳闸之后，hystrix允许另一个调用通过以便测试服务是否恢复健康之前的hystrix的休眠时间
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "7000"),

                    // 用于控制 Hystrix 用来监视服务调用问题的窗口期时间长短。其默认值为 10 000 ms
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "15000"),
                    // 滚动窗口中收集统计信息的次数。
                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "5")
            },
            // 添加了一个hystrix的后备方法，如果调用失败则会调用这个方法
            fallbackMethod = "buildFallbackLicenseList",

            // 设置舱壁模式
            threadPoolProperties =
                    // 定义线程池中最大的线程数
                    {@HystrixProperty(name = "coreSize", value = "30"),
                            // 线程池队列，对传入的请求进行排队
                            @HystrixProperty(name = "maxQueueSize", value = "10")}
    )
    public List<License> getLicensesByOrg(String organizationId){
        logger.info("LicenseService.getLicensesByOrg  Correlation id: {}", UserContextHolder.getContext().getCorrelationId());

        return licenseRepository.findByOrganizationId(organizationId);
    }
    // 使用断路器包装getListLicense()方法。
    // 每当调用时间超过1000ms时，断路器将中断对 getListLicense()方法的调用 。
    @HystrixCommand(
            commandProperties = {
                    // 定制化hystrix
                    // 设置断路器超时时间
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),

                    // 10秒最小调用次数
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
                    // 跳闸必须达到的调用失败的百分比
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "75"),
                    // 断路器跳闸之后，hystrix允许另一个调用通过以便测试服务是否恢复健康之前的hystrix的休眠时间
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "7000"),

                    // 用于控制 Hystrix 用来监视服务调用问题的窗口期时间长短。其默认值为 10 000 ms
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "15000"),
                    // 滚动窗口中收集统计信息的次数。
                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "5")
            },
            // 添加了一个hystrix的后备方法，如果调用失败则会调用这个方法
            fallbackMethod = "buildFallbackLicenseList",

            // 设置舱壁模式
            threadPoolProperties =
                    // 定义线程池中最大的线程数
                    {@HystrixProperty(name = "coreSize", value = "30"),
                            // 线程池队列，对传入的请求进行排队
                            @HystrixProperty(name = "maxQueueSize", value = "10")}
    )
    public List<License> getListLicenses(String organizationId) {
        randomSleep();
        return licenseRepository.findByOrOrganizationId(organizationId);
    }

    private void randomSleep() {
        int i = new Random().nextInt(3) + 1;
        if (i <= 2) {
            sleep();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(1100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // hystrix耗费时间太长调用的方法。此后备方法必须与由＠ HystrixCommand
    // 保护的原始方法位于同一个类中，并且必须具有与原始方法完全相同的方法签名
    private List<License> buildFallbackLicenseList(String organizationId) {
        License license = new License();
        license.setOrganizationId("超时!");
        return Collections.singletonList(license);
    }

    private Organization retrieveOrgInfo(String organizationId, String clientType) {
        Organization organization = null;


        switch (clientType) {
            case "feign":
                System.out.println("正在使用feign客户端");
                organization = organizationFeignClient.getOrganization(organizationId);
                break;
            case "rest":
                System.out.println("正在使用rest客户端");
                organization = organizationRestClient.getOrganization(organizationId);
                break;
            case "discovery":
                System.out.println("正在使用服务发现客户端");
                organization = organizationDiscoveryClient.getOrganization(organizationId);
                break;
            default:
                organization = organizationDiscoveryClient.getOrganization(organizationId);
                break;
        }
        return organization;
    }
}
