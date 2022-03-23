package com.example.myapp;

import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.CreateFunctionRequest;
import software.amazon.awssdk.services.lambda.model.FunctionCode;
import software.amazon.awssdk.services.lambda.model.LambdaException;
import software.amazon.awssdk.services.lambda.model.CreateFunctionResponse;
import software.amazon.awssdk.services.lambda.model.Runtime;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class CreateLambda {

    public static void main(String[] args) {

        // functionName - the name of the Lambda function.
        // filePath - the path to the ZIP or JAR where the code is located.
        // role - the role ARN that has Lambda permissions.
        // handler - the fully qualifed method name (for example,
        // example.Handler::handleRequest)

        String functionName = "MyLambda2";
        String filePath = "C:\\AWS\\lambdas.zip";
        String role = "arn:aws:iam::420197396933:role/service-role/Hello-World-role-ff96zxio";
        String handler = "HellowWorld.handler";

        Region region = Region.US_EAST_1;
        LambdaClient awsLambda = LambdaClient.builder()
                .region(region)
                .build();

        createLambdaFunction(awsLambda, functionName, filePath, role, handler);
        awsLambda.close();
    }

    public static void createLambdaFunction(LambdaClient awsLambda,
            String functionName,
            String filePath,
            String role,
            String handler) {

        try {
            InputStream is = new FileInputStream(filePath);
            SdkBytes fileToUpload = SdkBytes.fromInputStream(is);

            FunctionCode code = FunctionCode.builder()
                    .zipFile(fileToUpload)
                    .build();

            CreateFunctionRequest functionRequest = CreateFunctionRequest.builder()
                    .functionName(functionName)
                    .description("Created by the Lambda Java API")
                    .code(code)
                    .handler(handler)
                    .runtime(Runtime.JAVA8)
                    .role(role)
                    .build();

            CreateFunctionResponse functionResponse = awsLambda.createFunction(functionRequest);
            System.out.println("The function ARN is " + functionResponse.functionArn());

        } catch (LambdaException | FileNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
