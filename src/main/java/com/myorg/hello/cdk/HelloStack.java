package com.myorg.hello.cdk;

import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;

import software.amazon.awscdk.services.ecs.Cluster;
import software.amazon.awscdk.services.ecs.ContainerImage;
import software.amazon.awscdk.services.ecs.CloudMapNamespaceOptions;

import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedFargateService;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedTaskImageOptions;

import software.amazon.awscdk.services.servicediscovery.NamespaceType;

import software.amazon.awscdk.services.ecs.LogDriver;
import software.amazon.awscdk.services.ecs.AwsLogDriverProps;

import software.amazon.awscdk.services.servicediscovery.PrivateDnsNamespace;
import software.amazon.awscdk.services.ecs.CloudMapOptions;

import java.util.Map;
import java.util.HashMap;

public class HelloStack {

    public HelloStack(final Stack stack, final Cluster cluster, final String id,
		final StackProps props, String serviceName, String weGoodFactoryClassName,
		String weGoodServiceURL, PrivateDnsNamespace privateDnsNamespace) {

		/* Pass name of the WeGood stub factory class to the container env: */
		Map containerEnvMap = new HashMap();
		containerEnvMap.put("WEGOOD_FACTORY_CLASS_NAME", weGoodFactoryClassName);
		containerEnvMap.put("WEGOOD_SERVICE_URL", weGoodServiceURL);

		/* Create a load-balanced Fargate service for the hello microservice and
			make it public: */
		ApplicationLoadBalancedFargateService fgs =
			ApplicationLoadBalancedFargateService.Builder.create(stack, id)
			.serviceName(serviceName)
			.cluster(cluster)           // Required
			.memoryLimitMiB(2048)       // Default is 512
			.cpu(512)                   // Default is 256
			.desiredCount(1)            // Default is 1

			.cloudMapOptions(CloudMapOptions.builder() // auto discovery mode
				.cloudMapNamespace(privateDnsNamespace)
				.name(serviceName)
				.build())

			.taskImageOptions(
				ApplicationLoadBalancedTaskImageOptions.builder()
				.image(ContainerImage.fromRegistry("cliffberg/hello"))
				.containerPort(4567)
				.environment(containerEnvMap)
				.enableLogging(true)
				.logDriver(LogDriver.awsLogs(AwsLogDriverProps.builder()
					.streamPrefix("Hello").build()))
				.build())
			.publicLoadBalancer(true)   // Default is false
			.build();
    }
}
