#!/bin/bash

echo "-------Enter The Stack Name:"

<<<<<<< HEAD
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
=======
echo
echo "-------Test if the Stack existed:"
stack_Id=$(aws cloudformation describe-stacks --stack-name "$stackname" --query 'Stacks[0].StackId' --output text)
echo $stack_Id
if 
	[ "$stack_Id" != "" ]; then
	echo "Stack has already existed, terminate first!"
	exit 0
fi

echo "-------Start to build the cloudformation:"
aws cloudformation create-stack --stack-name $stackname --template-body file://csye6225-cf-networking.json

echo "-------Check if the cloudformation has been done sucessfully!"
stackStatus=$(aws cloudformation describe-stacks --stack-name $stackname --query 'Stacks[0].StackStatus' --output text)

while [[ "$stackStatus" != "CREATE_COMPLETE" && "$stackStatus" != "ROLLBACK_COMPLETE" ]]
do
	stackStatus=$(aws cloudformation describe-stacks --stack-name $stackname --query 'Stacks[0].StackStatus' --output text)
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
>>>>>>> 5c60689443bb356a3518688f529ba947a5f22eee
