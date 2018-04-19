#!/bin/bash

echo "-------Enter The Stack Name:"
read stackname

echo
echo "-------Test if the Stack existed:"
stack_Id=$(aws cloudformation describe-stacks --stack-name "$stackname" --query 'Stacks[0].StackId' --output text)
echo $stack_Id
if
	[ "$stack_Id" != "" ]; then
	echo "Stack has already existed, terminate first!"
	exit 0
fi

lambdaarn=$(aws lambda get-function --function-name "csye6225" --query "Configuration.FunctionArn" --output text)
echo $lambdaarn

vpcId=$(aws ec2 describe-vpcs --filters "Name=cidr-block-association.cidr-block,Values=10.0.0.0/16" --query "Vpcs[0].VpcId" --output text)
echo $vpcId

subnetId=$(aws ec2 describe-subnets --filters "Name=cidrBlock,Values=10.0.0.0/24" --query "Subnets[0].SubnetId" --output text)
echo $subnetId

webpolicy=$(aws ec2 describe-security-groups --filters Name=tag-key,Values="Web" --query "SecurityGroups[0].GroupId" --output text)
echo $webpolicy

loadbalancerpolicy=$(aws ec2 describe-security-groups --filters Name=tag-key,Values="loadbalancer" --query "SecurityGroups[0].GroupId" --output text)
echo $loadbalancerpolicy

Subnet1=$(aws ec2 describe-subnets --filters "Name=cidrBlock,Values=10.0.0.0/24" --query "Subnets[0].SubnetId" --output text)
echo $Subnet1

Subnet2=$(aws ec2 describe-subnets --filters "Name=cidrBlock,Values=10.0.3.0/24" --query "Subnets[0].SubnetId" --output text)
echo $Subnet2

Subnet3=$(aws ec2 describe-subnets --filters "Name=cidrBlock,Values=10.0.4.0/24" --query "Subnets[0].SubnetId" --output text)
echo $Subnet3

echo "-------Start to build the cloudformation:"
aws cloudformation create-stack --stack-name $stackname --template-body file://csye6225-cf-application.json --capabilities CAPABILITY_NAMED_IAM --parameters ParameterKey=WebPolicy,ParameterValue=$webpolicy ParameterKey=lambdaarn,ParameterValue=$lambdaarn ParameterKey=Subnet1,ParameterValue=$Subnet1 ParameterKey=Subnet2,ParameterValue=$Subnet2 ParameterKey=Subnet3,ParameterValue=$Subnet3 ParameterKey=LoadBalancerPolicy,ParameterValue=$loadbalancerpolicy ParameterKey=VpcId,ParameterValue=$vpcId ParameterKey=ImageId,ParameterValue=ami-66506c1c ParameterKey=CoolDown,ParameterValue=60 ParameterKey=MinSize,ParameterValue=3 ParameterKey=MaxSize,ParameterValue=7 ParameterKey=DesiredCapacity,ParameterValue=3 ParameterKey=InstanceType,ParameterValue=t2.micro ParameterKey=DomainName,ParameterValue=csye6225-spring2018-sunrui.me.

echo "-------Check if the cloudformation has been done sucessfully!"
stackStatus=$(aws cloudformation describe-stacks --stack-name $stackname --query 'Stacks[0].StackStatus' --output text)

while [[ "$stackStatus" != "CREATE_COMPLETE" ]]
do
	stackStatus=$(aws cloudformation describe-stacks --stack-name $stackname --query 'Stacks[0].StackStatus' --output text)
	echo "Please wait a moment!"
	echo $stackStatus
	if [ "$stackStatus" == "ROLLBACK_IN_PROGRESS" ];then
	break
  fi
	sleep 3
done

if [ "$stackStatus" == "CREATE_COMPLETE" ]; then
	#statements
	echo "cloudformation sucessfully!"
	exit 0
else
	echo "failed"
	aws cloudformation delete-stack --stack-name "$stackname"
	exit 0
fi
