/*
 * Tencent is pleased to support the open source community by making Spring Cloud Tencent available.
 *
 * Copyright (C) 2019 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the BSD 3-Clause License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://opensource.org/licenses/BSD-3-Clause
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.tencent.cloud.polaris.circuitbreaker.example;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Service A Controller
 *
 * @author Haotian Zhang
 */
@RestController
@RequestMapping("/example/service/a")
public class ServiceAController {

    private final ProviderB polarisServiceB;

    private final RestTemplate restTemplate;

    public ServiceAController(ProviderB polarisServiceB, RestTemplate restTemplate) {
        this.polarisServiceB = polarisServiceB;
        this.restTemplate = restTemplate;
    }

    /**
     * 获取当前服务的信息
     *
     * @return 返回服务信息
     * @throws Exception
     */
    @GetMapping("/info")
    public String info() throws Exception {
        return "hello world ! I'am a service";
    }

    /**
     * 获取B服务的信息
     *
     * @return 返回B服务的信息
     * @throws Exception
     */
    @GetMapping("/getBServiceInfo")
    public String getBServiceInfo() throws Exception {
        return polarisServiceB.info();
    }

    @RequestMapping(value = "/testRest", method = RequestMethod.GET)
    public String testRest() {
        ResponseEntity<String> entity = restTemplate.getForEntity("http://polaris-circuitbreaker-example-b/example/service/b/info", String.class);
        return entity.getBody();
    }

}
