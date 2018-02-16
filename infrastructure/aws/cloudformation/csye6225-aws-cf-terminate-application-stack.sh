=#!/bin/bash

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

echo
echo "-------Continue to work:"

aws cloudformation delete-stack --stack-name "$stackname"

sleep 2

stackStatus=$(aws cloudformation describe-stacks --stack-name $stack_Id --query 'Stacks[0].StackStatus' --output text)
echo $stackStatus

while [[  "$stackStatus" == "DELETE_IN_PROGRESS" ]]
do
stackStatus=$(aws cloudformation describe-stacks --stack-name $stack_Id --query 'Stacks[0].StackStatus' --output text)
echo "Please wait a moment!"
echo  $stackStatus
sleep 3
done

if [ "$stackStatus" == "DELETE_COMPLETE" ]; then
	echo "Stack delete sucessfully!"
	exit 0
else
	echo "Failed"
	exit 0
fi
