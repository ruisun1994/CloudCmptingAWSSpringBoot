#!/bin/bash

echo "-------Enter The Stack Name:"
read stackname

#Prepare the Variables
VpcName=$stackname-csye6225-vpc
KEY_NAME=$stackname-key

echo
echo "-------Test if the vpc existed:"
echo "-------The VpcId is:"
VPC_ID0=$(aws ec2 describe-vpcs --filter Name=tag:Name,Values="$VpcName" --query 'Vpcs[0].VpcId')&&
#echo $VPC_ID0
if
	[ $VPC_ID0 == null ];then
	echo "Concerning VPC not existed, create one first!"
	exit 0
fi

VPC_ID=$(echo $VPC_ID0 | tr -d \")
#echo $VPC_ID

echo
echo "-------Test if the Stack existed:"
stack_Id=$(aws cloudformation describe-stacks --stack-name "$stackname-csye6225-stack" --query 'Stacks[0].StackId') 
if
	[ $? -eq 0 ]; then
	echo "Stack Already existed, terminated it first!"
	exit 0
fi

echo
echo "create stack named $stackname-csye6225-stack in 2 second"
sleep 2


echo "-------The SubnetId is:"
SUBNET_ID=$(aws ec2 describe-subnets --filters "Name=vpc-id, Values=$VPC_ID" --query "Subnets[0].SubnetId" --output text)&&
echo $SUBNET_ID


echo "-------Start to build the cloudformation:"
aws cloudformation create-stack --stack-name "$stackname-csye6225-stack" --template-body file://csye6225-cf-networking.json --parameters ParameterKey=ParamSubnetID,ParameterValue=$SUBNET_ID ParameterKey=ParamKeyName,ParameterValue=$KEY_NAME ParameterKey=ParamVpcID,ParameterValue=$VPC_ID&&

