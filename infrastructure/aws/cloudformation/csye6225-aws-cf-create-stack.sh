#!/bin/bash

echo "-------Enter The Stack Name:"
read stackname

echo "-------Start to build the cloudformation:"
aws cloudformation create-stack --stack-name "$stackname" --template-body file://csye6225-cf-networking.json

echo "-------Check if the cloudformation has been done sucessfully!"
stackStatus=$(aws cloudformation describe-stacks --stack-name "$stackname" --query 'Stacks[0].StackStatus' --output text)
#echo $stackStatus

while [[ "$stackStatus" != "CREATE_COMPLETE" && "$stackStatus" != "ROLLBACK_COMPLETE" ]]
do
	stackStatus=$(aws cloudformation describe-stacks --stack-name "$stackname" --query 'Stacks[0].StackStatus' --output text)
	echo "Please wait a moment!"
	echo $stackStatus
	sleep 3
done

if [ "$stackStatus" == "CREATE_COMPLETE" ]; then
	#statements
	echo "cloudformation sucessfully!"
	exit 0
else
	echo "failed"
	exit 0
fi
