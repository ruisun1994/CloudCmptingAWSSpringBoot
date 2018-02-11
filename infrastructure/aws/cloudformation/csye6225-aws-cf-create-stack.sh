#!/bin/bash

echo "-------Enter The Stack Name:"

echo "-------Start to build the cloudformation:"
aws cloudformation create-stack --stack-name $1 --template-body file://csye6225-cf-networking.json

echo "-------Check if the cloudformation has been done sucessfully!"
stackStatus=$(aws cloudformation describe-stacks --stack-name $1 --query 'Stacks[0].StackStatus' --output text)
echo $stackStatus

sleep 100
stackStatus=$(aws cloudformation describe-stacks --stack-name $1 --query 'Stacks[0].StackStatus' --output text)

if [ "$stackStatus" == "CREATE_COMPLETE" ]; then
	#statements
	echo "cloudformation sucessfully!"
	exit 0
else
	echo "failed"
	exit 0
fi
