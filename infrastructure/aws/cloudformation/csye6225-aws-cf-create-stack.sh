#!/bin/bash
echo "--------Start Run the creation Application-------------"

#Create the VPC

#Usefull Variables in the Script
serverZone="us-east-1"
vpcName="STACK_NAME-csye6225-vpc"
gatewayName="STACK_NAME-csye6225-InternetGateway"
routeTableName="STACK_NAME-csye6225-route-table"
vpcCidrBlock="10.0.0.0/16"
subNetCidrBlock="10.0.1.0/24"
destinationCidrBlock="0.0.0.0/0"

echo
echo "--------Creating VPC-------------"
#create vpc with cidr block /16
aws_response=$(aws ec2 create-vpc --cidr-block "$vpcCidrBlock")
#vpcId=$(echo "$aws_response" | sed -n 's/.*"VpcId": "\(.*\)",/\1/p')
vpcId=$(echo -e "$aws_response" | /usr/bin/jq '.Vpc.VpcId' | tr -d '"') 
echo $vpcId

echo
echo "-------Renaming VPC-------------"
# echo "$vpcId"
# echo "$vpcName"
aws ec2 create-tags --resources "$vpcId" --tags Key=Name,Value=$vpcName

echo
echo "--------Creating Internet Gatewy-------------"
gateway_response=$(aws ec2 create-internet-gateway)
gatewayId=$(echo -e "$gateway_response" | /usr/bin/jq '.InternetGateway.InternetGatewayId' | tr -d '"') 
echo $gatewayId

echo
echo "--------Renaming Internet Gatewy-------------"
aws ec2 create-tags --resources "$gatewayId" --tags Key=Name,Value=$gatewayName

echo
echo "--------Attaching gateway to vpc------------"
attach_response=$(aws ec2 attach-internet-gateway --vpc-id "$vpcId" --internet-gateway-id "$gatewayId")


echo
echo "--------Creating route table for vpc-----------"
route_table_response=$(aws ec2 create-route-table --vpc-id "$vpcId")
routeTableId=$(echo -e "$route_table_response" | /usr/bin/jq '.RouteTable.RouteTableId' | tr -d '"')
echo $routeTableId

echo
echo "--------Renaming Route Table--------------"
aws ec2 create-tags --resources "$routeTableId" --tags Key=Name,Value=$routeTableName

echo
echo "-----------Adding Route for the internet gateway---------------"
route_response=$(aws ec2 create-route --route-table-id "$routeTableId" --destination-cidr-block "$destinationCidrBlock" --gateway-id "$gatewayId")

echo
echo "-----------Success!!!---------------"