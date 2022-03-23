package com.example.myapp;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.TerminateInstancesRequest;
import software.amazon.awssdk.services.ec2.model.TerminateInstancesResponse;
import software.amazon.awssdk.services.ec2.model.InstanceStateChange;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;
import java.util.List;

public class TerminateEc2 {

    public static void main(String[] args) {

        new AwsInfo();
        String instanceId = AwsInfo.instanceId;
        String name = AwsInfo.instanceName;

        System.out.println("=============================================");
        System.out.println("Instance name:" + name + " id:" + instanceId);
        System.out.println("=============================================");

        instanceId = "i-0f439ab689de17557";

        Region region = Region.US_EAST_1;
        Ec2Client ec2 = Ec2Client.builder()
                .region(region)
                .build();

        //terminateEC2(ec2, instanceId);
        ec2.close();
    }

    public static void terminateEC2(Ec2Client ec2, String instanceID) {

        try {
            TerminateInstancesRequest ti = TerminateInstancesRequest.builder()
                    .instanceIds(instanceID)
                    .build();

            TerminateInstancesResponse response = ec2.terminateInstances(ti);
            List<InstanceStateChange> list = response.terminatingInstances();

            for (int i = 0; i < list.size(); i++) {
                InstanceStateChange sc = (list.get(i));
                System.out.println("The ID of the terminated instance is " + sc.instanceId());
            }
        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }
}