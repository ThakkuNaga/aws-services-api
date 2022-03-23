package com.example.myapp;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;
import software.amazon.awssdk.services.ec2.model.RebootInstancesRequest;

public class RebootEc2 {

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

        rebootEC2Instance(ec2, instanceId);
        ec2.close();
    }

    public static void rebootEC2Instance(Ec2Client ec2, String instanceId) {

        try {
            RebootInstancesRequest request = RebootInstancesRequest.builder()
                    .instanceIds(instanceId)
                    .build();

            ec2.rebootInstances(request);
            System.out.printf(
                    "Successfully rebooted instance %s", instanceId);
        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

}
