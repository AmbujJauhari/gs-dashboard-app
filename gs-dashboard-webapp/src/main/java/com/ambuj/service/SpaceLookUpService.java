package com.ambuj.service;

import com.ambuj.domain.SpaceLookUpDetails;
import com.ambuj.util.ResourceLoadUtil;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SpaceLookUpService {
    private static final String ENVCONFIG_DIR = "envconfig/";

    @Value("${app-config-ext:*.conf}")
    private String configIdentifier;

    @Autowired
    private ResourceLoadUtil resourceLoadUtil;

    private List<SpaceLookUpDetails> lookUpDetails = new ArrayList<>();

    private Map<String, GigaSpace> envProxyMap = new HashMap<>();

    @PostConstruct
    public void getAllConfigs() {
        try {
            Resource[] confFiles = resourceLoadUtil.loadResources("classpath*:" + ENVCONFIG_DIR + configIdentifier);
            for (Resource resource : confFiles) {
                String fileName = resource.getFilename();
                String baseName = fileName.substring(0, fileName.indexOf("."));
                Config config = ConfigFactory.load(ENVCONFIG_DIR + baseName);
                lookUpDetails.add(new SpaceLookUpDetails.SpaceLookUpDetailsBuilder().buildWithConfig(config));
            }

            for (SpaceLookUpDetails spaceLookUpDetails : lookUpDetails) {
                if (spaceLookUpDetails.isSecured()) {
                    UrlSpaceConfigurer urlSpaceConfigurer = new UrlSpaceConfigurer(spaceLookUpDetails.getUrl())
                            .credentials(spaceLookUpDetails.getUserName(), spaceLookUpDetails.getPassword());
                    envProxyMap.put(spaceLookUpDetails.getEnvName(), new GigaSpaceConfigurer(urlSpaceConfigurer).gigaSpace());
                } else {
                    UrlSpaceConfigurer urlSpaceConfigurer = new UrlSpaceConfigurer(spaceLookUpDetails.getUrl());
                    envProxyMap.put(spaceLookUpDetails.getEnvName(), new GigaSpaceConfigurer(urlSpaceConfigurer).gigaSpace());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public GigaSpace getSpace(String envName) {
        return envProxyMap.get(envName);
    }

    public List<SpaceLookUpDetails> gsLookUpDetails() {
        return lookUpDetails;
    }

}
