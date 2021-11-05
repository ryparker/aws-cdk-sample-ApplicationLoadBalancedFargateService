package com.myorg.getmymoodyresponse.cdk;

import com.myorg.hello.cdk.HelloStack;
import com.myorg.wegood.cdk.WeGoodStack;

import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.StackProps;

import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.ecs.Cluster;
import software.amazon.awscdk.services.ecs.CloudMapNamespaceOptions;

import software.amazon.awscdk.services.servicediscovery.NamespaceType;
import software.amazon.awscdk.services.servicediscovery.PrivateDnsNamespace;

public class GetMyMoodyResponseTestStack extends Stack {

    public GetMyMoodyResponseTestStack(final Construct scope, final String id) {
        this(scope, id, null, "wegood.stub.WeGoodProxyFactory");
    }

    public GetMyMoodyResponseTestStack(final Construct scope, final String id, final StackProps props,
		String weGoodFactoryClassName) {

        super(scope, id, props);

		/* Create VPC with a max of two availability zones: */
		Vpc vpc = Vpc.Builder.create(this, "moody-vpc")
			.maxAzs(2)  // Default is all AZs in region
			.build();

		String namespaceName = "moody.com";

		PrivateDnsNamespace privateDnsNamespace =
			PrivateDnsNamespace.Builder.create(this, namespaceName)
				.name(namespaceName).vpc(vpc).build();

		/* Create ECS fargate-backed cluster within the VPC created above: */
		Cluster cluster = Cluster.Builder.create(this, "moody-cluster")
			.vpc(vpc)
			.defaultCloudMapNamespace(CloudMapNamespaceOptions.builder()
				.name("moody-default-namespace")
				.type(NamespaceType.DNS_PRIVATE)
				.build()
				)
			.build();

		//String weGoodServiceURL = "http://" + "wegood:80";
		//String weGoodServiceURL = "http://" + "wegood" + "." + namespaceName + ":4567";
		String weGoodServiceURL = "http://" + "wegood" + "." + namespaceName + ":80";
			// YourServiceDiscoveryServiceName.YourServiceDiscoveryNamespace
		new HelloStack(this, cluster, "hello", props, "hello", "wegood.stub.WeGoodProxyFactory",
			weGoodServiceURL, privateDnsNamespace);
		new WeGoodStack(this, cluster, "wegood", props, "wegood", privateDnsNamespace);
	}
}
