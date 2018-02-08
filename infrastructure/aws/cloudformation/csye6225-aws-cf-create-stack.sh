#!/bin/bash
<<<<<<< HEAD

echo "-------Enter The Stack Name:"
read stackname

#Prepare the Variables
VpcName=$stackname-csye6225-vpc
KEY_NAME=$stackname-key

echo
echo "-------Test if the vpc existed:"
echo "-------The VpcId is:"
VPC_ID0=$(aws ec2 describe-vpcs --filter Name=tag:Name,Values="$VpcName" --query 'Vpcs[0].VpcId')&&
#echo $VPC_ID0
if
	[ $VPC_ID0 == null ];then
	echo "Concerning VPC not existed, create one first!"
	exit 0
fi

VPC_ID=$(echo $VPC_ID0 | tr -d \")
#echo $VPC_ID

echo
echo "-------Test if the Stack existed:"
stack_Id=$(aws cloudformation describe-stacks --stack-name "$stackname-csye6225-stack" --query 'Stacks[0].StackId') 
if
	[ $? -eq 0 ]; then
	echo "Stack Already existed, terminated it first!"
	exit 0
fi


echo
echo "create stack named $stackname-csye6225-stack in 2 second"
sleep 2


echo "-------The SubnetId is:"
SUBNET_ID=$(aws ec2 describe-subnets --filters "Name=vpc-id, Values=$VPC_ID" --query "Subnets[0].SubnetId" --output text)&&
echo $SUBNET_ID


echo "-------Start to build the cloudformation:"
aws cloudformation create-stack --stack-name "$stackname-csye6225-stack" --template-body file://csye6225-cf-networking.json --parameters ParameterKey=ParamSubnetID,ParameterValue=$SUBNET_ID ParameterKey=ParamKeyName,ParameterValue=$KEY_NAME ParameterKey=ParamVpcID,ParameterValue=$VPC_ID&&

echo done
=======
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
>>>>>>> cac576d56e2ac850820c4f13fa7bbcae26a859cb
