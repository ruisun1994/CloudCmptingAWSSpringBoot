#!/bin/bash

echo "-------Enter The Stack Name:"
read stackname

echo "-------Start to build the cloudformation:"
aws cloudformation create-stack --stack-name "$stackname-csye6225-stack" --template-body file://csye6225-cf-networking.json --parameters ParameterKey=STACK_NAME,ParameterValue=$stackname&&

echo "-------Stack created!"
