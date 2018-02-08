#!/bin/bash
<<<<<<< HEAD

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
=======
echo "--------Start Run the Terminating Application-------------"

vpcName="STACK_NAME-csye6225-vpc"
gatewayName="STACK_NAME-csye6225-InternetGateway"
routeTableName="STACK_NAME-csye6225-route-table"

echo
echo "--------Deleting the Route Table-------------"


echo
echo "--------Detaching the Gateway from VPC-------------"

echo
echo "--------Deleting the Route Table-------------"

echo
echo "--------Deleting the Internet Gateway------------"

echo
echo "--------Deleting the VPC-------------"
>>>>>>> cac576d56e2ac850820c4f13fa7bbcae26a859cb
