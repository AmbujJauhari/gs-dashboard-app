package com.ambuj.plugin;


import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.openspaces.core.cluster.ClusterInfo;
import org.openspaces.pu.container.standalone.StandaloneProcessingUnitContainerProvider;

import java.io.File;

@Mojo(name = "gsStandAloneDeployer")
public class GsStandAloneDeployer extends AbstractMojo {

    @Parameter
    private File puFile;

    public void execute() throws MojoExecutionException, MojoFailureException {
        StandaloneProcessingUnitContainerProvider standaloneProcessingUnitContainerProvider =
                new StandaloneProcessingUnitContainerProvider(puFile.getParentFile().getAbsolutePath());
        ClusterInfo clusterInfo = new ClusterInfo();
        clusterInfo.setNumberOfInstances(1);
        clusterInfo.setSchema("partitioned-sync2backup");
        clusterInfo.setNumberOfBackups(0);
        clusterInfo.setInstanceId(1);

        standaloneProcessingUnitContainerProvider.addConfigLocation("file:" + puFile.getAbsolutePath());
        standaloneProcessingUnitContainerProvider.setClusterInfo(clusterInfo);

        standaloneProcessingUnitContainerProvider.createContainer();
    }
}
