package com.example.myapp;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.StopInstancesRequest;

public class StopEc2 {
    public static void main(String[] args) {
        new AwsInfo();
        String instanceId = AwsInfo.instanceId;
        String name = AwsInfo.instanceName;

        System.out.println("=============================================");
        System.out.println("Instance name:" + name + " id:" + instanceId);
        System.out.println("=============================================");

        Region region = Region.US_EAST_1;
        Ec2Client ec2 = Ec2Client.builder()
                .region(region)
                .build();

        stopInstance(ec2, instanceId);

        ec2.close();
    }

    public static void stopInstance(Ec2Client ec2, String instanceId) {

        StopInstancesRequest request = StopInstancesRequest.builder()
                .instanceIds(instanceId)
                .build();

        ec2.stopInstances(request);
        System.out.printf("Successfully stopped instance %s", instanceId);
    }

}
