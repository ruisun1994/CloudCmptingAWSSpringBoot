#!/bin/bash

echo "Enter The Stack Name"
read stackname

echo
echo "-------Test if the Stack existed:"
stack_Id=$(aws cloudformation describe-stacks --stack-name "$stackname" --query 'Stacks[0].StackId' --output text)
echo $stack_Id
if 
	[ $? -ne 0 ]; then
	echo "No Stack Found, create firstly"
	exit 0
fi

aws cloudformation delete-stack --stack-name "$stackname"

stackStatus=$(aws cloudformation describe-stacks --stack-name $stackname --query 'Stacks[0].StackStatus' --output text)
sleep 100
stackStatus=$(aws cloudformation describe-stacks --stack-name $stackname --query 'Stacks[0].StackStatus' --output text)

if [ "$stackStatus" == "DELETE_COMPLETE" ]; then
	#statements
	echo "stack delete sucessfully!"
	exit 0
else
	echo "failed"
	exit 0
fi
