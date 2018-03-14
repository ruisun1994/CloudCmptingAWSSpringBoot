#!/bin/bash
echo "--------Start Run the Tear Down Application-------------"

# Ask user to enter the stack name
echo "Enter The Stack Name You want to delete:"
read stackname

instanceName=$(aws ec2 describe-instances --filter Name=tag:Name,Values="$stackname-csye6225-Instance" --query 'Reservations[0]')
echo $instanceName
#check if already have an instance
if 
	[ "$instanceName" == null ]; then
	echo "No instance existed, please create one firstly"
	exit 0
else
	echo "You enter name is"
	echo $stackname
	echo "Delete in 2 second"
fi


sleep 2

jsonFileName="$stackname-information.json"
echo $jsonFileName

instanceId=$(/usr/bin/jq '.instanceId' "$jsonFileName" | tr -d '"')
echo $instanceId
groupId=$(/usr/bin/jq '.groupId' "$jsonFileName" | tr -d '"')
subnetId1=$(/usr/bin/jq '.subnetId1' "$jsonFileName" | tr -d '"')
subnetId2=$(/usr/bin/jq '.subnetId2' "$jsonFileName" | tr -d '"')
routeTableId=$(/usr/bin/jq '.routeTableId' "$jsonFileName" | tr -d '"')
gatewayId=$(/usr/bin/jq '.gatewayId' "$jsonFileName" | tr -d '"')
vpcId=$(/usr/bin/jq '.vpcId' "$jsonFileName" | tr -d '"')
keyName="$stackname-key"




echo
echo "-------Delete Local pem file:"
if [ -e "$keyName".pem ]; then
	rm -rf "$keyName".pem
	echo
	echo "-------pem file deleted"
fi

if [ -e "$jsonFileName" ]; then
	rm -rf "$jsonFileName"
fi

echo
echo "-------Delete EC2 Instance KeyPair:"
aws ec2 delete-key-pair --key-name "$keyName"&&

echo
echo "-------Terminate Instance:"
terminateInstancesInfo=$(aws ec2 terminate-instances --instance-ids "$instanceId")&&
echo $terminateInstancesInfo
instanceStateCode=0

sleep 120

echo $instanceStateCode&&

echo
echo "-------Delete your security group:"
aws ec2 delete-security-group --group-id "$groupId"&&

echo
echo "-------Delete your subnets:"
aws ec2 delete-subnet --subnet-id "$subnetId1"&&
aws ec2 delete-subnet --subnet-id "$subnetId2"&&

echo
echo "-------Delete your custom route table:"
aws ec2 delete-route-table --route-table-id "$routeTableId"&&

echo
echo "-------Detach your Internet gateway from your VPC:"
aws ec2 detach-internet-gateway --internet-gateway-id "$gatewayId" --vpc-id "$vpcId"&&

echo
echo "-------Delete your Internet gateway:"
aws ec2 delete-internet-gateway --internet-gateway-id "$gatewayId"&&

echo
echo "-------Delete your VPC:"
aws ec2 delete-vpc --vpc-id "$vpcId"&&

echo
echo "-------Delete networking resources sucessfully!"

