package com.jzl.cloudTest.config.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

public interface LoadBalancer {
    ServiceInstance instance(List<ServiceInstance> serviceInstances);
}
