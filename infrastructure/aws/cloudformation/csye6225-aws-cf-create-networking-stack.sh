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

echo "-------Start to build the cloudformation:"
aws cloudformation create-stack --stack-name $stackname --template-body file://csye6225-cf-networking.json

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
