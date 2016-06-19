package com.ambuj.service;

import com.ambuj.domain.SpaceLookUpDetails;
import com.ambuj.domain.SpaceLookUpDto;
import com.ambuj.exception.ConfigLoadException;
import com.ambuj.exception.ConfigNotFoundException;
import com.ambuj.exception.MalformedConfigException;
import com.ambuj.exception.SpaceInstantiaionException;
import com.ambuj.util.ResourceLoadUtil;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.commons.lang3.ArrayUtils;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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

    private Map<SpaceLookUpDetails, GigaSpace> envProxyMap = new HashMap<>();

    private List<Throwable> instantiationExceptions = new ArrayList<>();

    @PostConstruct
    public void getAllConfigs() {
        Resource[] confFiles = null;
        try {
            confFiles = resourceLoadUtil.loadResources("classpath*:" + ENVCONFIG_DIR + configIdentifier);
        } catch (Exception e) {
            instantiationExceptions.add(new ConfigLoadException(e));
        }

        if (ArrayUtils.isEmpty(confFiles)) {
            instantiationExceptions.add(new ConfigNotFoundException(ENVCONFIG_DIR));
        } else {
            for (Resource resource : confFiles) {
                try {
                    String fileName = resource.getFilename();
                    String baseName = fileName.substring(0, fileName.indexOf("."));
                    Config config = ConfigFactory.load(ENVCONFIG_DIR + baseName);
                    lookUpDetails.addAll(new SpaceLookUpDetails.SpaceLookUpDetailsBuilder().buildWithConfig(config));
                } catch (Exception ex) {
                    instantiationExceptions.add(new MalformedConfigException(ex));
                    continue;
                }
            }

            for (SpaceLookUpDetails spaceLookUpDetails : lookUpDetails) {
                try {
                    UrlSpaceConfigurer urlSpaceConfigurer;
                    if (spaceLookUpDetails.isSecured()) {
                        urlSpaceConfigurer = new UrlSpaceConfigurer(spaceLookUpDetails.getSpaceUrl())
                                .credentials(spaceLookUpDetails.getUserName(), spaceLookUpDetails.getPassword());
                    } else {
                        urlSpaceConfigurer = new UrlSpaceConfigurer(spaceLookUpDetails.getSpaceUrl());
                    }

                    GigaSpace gigaSpace = new GigaSpaceConfigurer(urlSpaceConfigurer).gigaSpace();
                    envProxyMap.put(spaceLookUpDetails, gigaSpace);
                } catch (Exception e) {
                    instantiationExceptions.add(new SpaceInstantiaionException(spaceLookUpDetails.getSpaceName()));
                    continue;
                }

            }
        }
    }

    public List<String> getAllSpacesForGrid(String gridName) {
        List<String> spacesForGrid = new ArrayList<>();
        for (SpaceLookUpDetails spaceLookUpDetails : envProxyMap.keySet()) {
            if (spaceLookUpDetails.getEnvName().equals(gridName)) {
                spacesForGrid.add(spaceLookUpDetails.getSpaceName());
            }
        }
        return spacesForGrid;
    }

    public GigaSpace getSpace(String envName, String spaceName) {
        GigaSpace gigaSpace = null;
        for (SpaceLookUpDetails spaceLookUpDetails : envProxyMap.keySet()) {
            if (spaceLookUpDetails.getEnvName().equals(envName) && spaceLookUpDetails.getSpaceName().equals(spaceName)) {
                gigaSpace = envProxyMap.get(spaceLookUpDetails);
            }
        }
        return gigaSpace;
    }

    public SpaceLookUpDto gsLookUpDetails() {
        SpaceLookUpDto spaceLookUpDto = new SpaceLookUpDto();
        SpaceLookUpDetails.SpaceLookUpDetailsBuilder builder = new SpaceLookUpDetails.SpaceLookUpDetailsBuilder();
        for (SpaceLookUpDetails spaceLookUpDetails : envProxyMap.keySet()) {
            spaceLookUpDto.getGridNames().add(spaceLookUpDetails.getEnvName());
        }
        if (instantiationExceptions.size() > 0) {
            spaceLookUpDto.setExceptions(instantiationExceptions.toString());
        }
        return spaceLookUpDto;
        //      return lookUpDetails;
    }

}
