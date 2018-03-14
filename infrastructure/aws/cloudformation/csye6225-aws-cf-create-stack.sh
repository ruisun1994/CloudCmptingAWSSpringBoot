#!/bin/bash


echo "Enter The Stack Name:"
read stackname

VpcName="$stackname-csye6225-vpc"
GatewayName="$stackname-csye6225-InternetGateway"
RouteTableName="$stackname-csye6225-public-route-table"
PrivateRouteTableName="$stackname-csye6225-private-route-table"

aws cloudformation create-stack --stack-name ${stackname} --template-body file://./csye6225-cf-networking.json --parameters ParameterKey=vpcName,ParameterValue=$VpcName ParameterKey=gatewayName,ParameterValue=$GatewayName ParameterKey=routeTableName,ParameterValue=$RouteTableName ParameterKey=privaterouteTableName,ParameterValue=$PrivateRouteTableName

aws cloudformation wait stack-create-complete --stack-name ${stackname}

echo done
