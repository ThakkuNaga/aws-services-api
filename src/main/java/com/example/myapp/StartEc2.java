package com.example.myapp;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.StartInstancesRequest;

public class StartEc2 {
    public static void main(String[] args) {
        new AwsInfo();
        String instanceId = AwsInfo.instanceId;
        String name = AwsInfo.instanceName;

        System.out.println("=============================================");
        System.out.println("Instance name:" + name + " id:" + instanceId);
        System.out.println("=============================================");

        // String instanceId = "i-03e75671c5fff7930";

        Region region = Region.US_EAST_1;
        Ec2Client ec2 = Ec2Client.builder()
                .region(region)
                .build();

        startInstance(ec2, instanceId);

        ec2.close();
    }

    public static void startInstance(Ec2Client ec2, String instanceId) {

        StartInstancesRequest request = StartInstancesRequest.builder()
                .instanceIds(instanceId)
                .build();

        ec2.startInstances(request);
        System.out.printf("Successfully started instance %s", instanceId);
    }

}
