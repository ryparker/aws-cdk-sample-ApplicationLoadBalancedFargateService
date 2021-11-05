package com.myorg.getmymoodyresponse.cdk;

import software.amazon.awscdk.core.App;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.core.Environment;

public class GetMyMoodyResponseTestApp {
	public static void main(final String[] args) {
		String stackName = "MoodyTestStack";
		String region = "us-east-1";
		App app = new App();
		new GetMyMoodyResponseTestStack(app, stackName);
		app.synth();
	}
}
