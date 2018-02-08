#!/bin/bash

echo "Enter The Stack Name"
read stackname

echo
echo "-------Test if the Stack existed:"
stack_Id=$(aws cloudformation describe-stacks --stack-name "$stackname-csye6225-stack" --query 'Stacks[0].StackId' --output text)
echo $stack_Id
if 
	[ $? -ne 0 ]; then
	echo "No Stack Found, create firstly"
	exit 0
fi

aws cloudformation delete-stack --stack-name "$stackname-csye6225-stack"&&
echo done