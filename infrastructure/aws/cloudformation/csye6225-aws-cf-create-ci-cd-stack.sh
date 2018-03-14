#!/bin/bash

echo "Enter The Stack Name:"
read stackname


vpcId=$(aws ec2 describe-vpcs --filters "Name=cidr-block-association.cidr-block,Values=10.0.0.0/16" --query "Vpcs[0].VpcId" --output text)
echo $vpcId
subnetId=$(aws ec2 describe-subnets --filters "Name=cidrBlock,Values=10.0.0.0/24" --query "Subnets[0].SubnetId" --output text)
instanceName="$stackname-csye6225-instance"
echo $subnetId


aws cloudformation create-stack --template-body file://./csye6225-cf-ci-cd.json --stack-name ${stackname} --capabilities "CAPABILITY_NAMED_IAM" --parameters ParameterKey=InstanceName,ParameterValue=$instanceName ParameterKey=SubnetId,ParameterValue=$subnetId


aws cloudformation wait stack-create-complete --stack-name ${stackname}
echo done
