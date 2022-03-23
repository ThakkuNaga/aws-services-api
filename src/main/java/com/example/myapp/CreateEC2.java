package com.example.myapp;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.CreateTagsRequest;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;
import software.amazon.awssdk.services.ec2.model.InstanceType;
import software.amazon.awssdk.services.ec2.model.RunInstancesRequest;
import software.amazon.awssdk.services.ec2.model.RunInstancesResponse;
import software.amazon.awssdk.services.ec2.model.Tag;

public class CreateEC2 {
        public static void main(String[] args) {

                final String USAGE = "\n" +
                                "Usage:\n" +
                                "   <name> <amiId>\n\n" +
                                "Where:\n" +
                                "   name - an instance name value that you can obtain from the AWS Console (for example, ami-xxxxxx5c8b987b1a0). \n\n"
                                +
                                "   amiId - an Amazon Machine Image (AMI) value that you can obtain from the AWS Console (for example, i-xxxxxx2734106d0ab). \n\n";

                // if (args.length != 2) {
                // System.out.println(USAGE);
                // System.exit(1);
                // }
                
                String name = "MyEc2";
                String amiId = "ami-0a8b4cd432b1c3063";
                Region region = Region.US_EAST_1;
                Ec2Client ec2 = Ec2Client.builder()
                                .region(region)
                                .build();

                String instanceId = createEC2Instance(ec2, name, amiId);
                System.out.println("The Amazon EC2 Instance ID is " + instanceId);
                ec2.close();
        }

        public static String createEC2Instance(Ec2Client ec2, String name, String amiId) {

                RunInstancesRequest runRequest = RunInstancesRequest.builder()
                                .imageId(amiId)
                                .instanceType(InstanceType.T1_MICRO)
                                .maxCount(1)
                                .minCount(1)
                                .build();

                RunInstancesResponse response = ec2.runInstances(runRequest);
                String instanceId = response.instances().get(0).instanceId();

                Tag tag = Tag.builder()
                                .key("Name")
                                .value(name)
                                .build();

                CreateTagsRequest tagRequest = CreateTagsRequest.builder()
                                .resources(instanceId)
                                .tags(tag)
                                .build();

                try {
                        ec2.createTags(tagRequest);
                        System.out.printf(
                                        "Successfully started EC2 Instance %s based on AMI %s",
                                        instanceId, amiId);

                        return instanceId;

                } catch (Ec2Exception e) {
                        System.err.println(e.awsErrorDetails().errorMessage());
                        System.exit(1);
                }

                return "";
        }
}