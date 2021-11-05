# AWS CDK Sample ApplicationLoadBalancedFargateService

## :rocket: Quick Start

**1. Install CDK globaly using NPM**

```shell
npm install -g aws-cdk
```

**2. Create the [bootstrap stack](https://docs.aws.amazon.com/cdk/latest/guide/bootstrapping.html) in your AWS account**
_This only needs to be ran once per account/region._

```shell
cdk bootstrap
```

**3. Build Cloudformation files**

```shell
cdk synth
```

**4. Deploy**

```shell
cdk deploy
```

## Useful commands

| Command       | Definition                                            |
| ------------- | ----------------------------------------------------- |
| `mvn package` | Compile and run tests.                                |
| `cdk ls`      | List all stacks in the app.                           |
| `cdk synth`   | Emits the synthesized CloudFormation template.        |
| `cdk deploy`  | Deploy this stack to your default AWS account/region. |
| `cdk diff`    | Compare deployed stack with current state.            |
| `cdk docs`    | Open CDK documentation.                               |
