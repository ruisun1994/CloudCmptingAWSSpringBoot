#!/bin/bash
echo "--------Start Run the Tear Down Application-------------"

# Ask user to enter the stack name
echo "Enter The Name You want to delete:"
read stackname


vpcName="$stackname-csye6225-vpc"
echo
echo "--------Check Whether VPC is Existed:"
VPC_ID0=$(aws ec2 describe-vpcs --filter Name=tag:Name,Values="$vpcName" --query 'Vpcs[0].VpcId')&&
#echo $VPC_ID0
if
	[ "$VPC_ID0" == null ];then
	echo "VPC not existed, create it first!"
	exit 0
fi

jsonFileName="$stackname-information.json"
# instanceId=$(/usr/bin/jq '.instanceId' "$jsonFileName" | tr -d '"')
# echo $instanceId
# groupId=$(/usr/bin/jq '.groupId' "$jsonFileName" | tr -d '"')
# subnetId1=$(/usr/bin/jq '.subnetId1' "$jsonFileName" | tr -d '"')
# subnetId2=$(/usr/bin/jq '.subnetId2' "$jsonFileName" | tr -d '"')
routeTableId=$(/usr/bin/jq '.routeTableId' "$jsonFileName" | tr -d '"')
gatewayId=$(/usr/bin/jq '.gatewayId' "$jsonFileName" | tr -d '"')
vpcId=$(/usr/bin/jq '.vpcId' "$jsonFileName" | tr -d '"')
# keyName="$stackname-key"



# echo
# echo "-------Terminate Instance:"
# instanceStateCode=$(aws ec2 terminate-instances --instance-ids "$instanceId" --query "TerminatingInstances[0].CurrentState.Code" --output text)&&



# while [ "$instanceStateCode" != 48  ]; do
# 	echo "Status Code is "
# 	echo $instanceStateCode
# 	echo "Wait Instance from Shutting down to Terminated Status"
# 	sleep 10
# 	instanceStateCode=$(aws ec2 terminate-instances --instance-ids "$instanceId" --query "TerminatingInstances[0].CurrentState.Code" --output text)
# done

# echo $instanceStateCode&&
# echo "Ready to continue to the next work"




# while [ "$instanceStateCode" != 48  ]; do
# 	echo "Status Code is "
# 	echo $instanceStateCode
# 	echo "Wait Instance from Shutting down to Terminated Status"
# 	sleep 10
# 	instanceStateCode=$(aws ec2 terminate-instances --instance-ids "$instanceId" --query "TerminatingInstances[0].CurrentState.Code" --output text)
# done

# echo $instanceStateCode&&
# echo "Ready to continue to the next work"


# echo
# echo "-------Delete EC2 Instance KeyPair:"
# aws ec2 delete-key-pair --key-name "$keyName"&&

# echo
# echo "-------Delete your security group:"
# aws ec2 delete-security-group --group-id "$groupId"&&

# echo
# echo "-------Delete your subnets:"
# aws ec2 delete-subnet --subnet-id "$subnetId1"&&
# aws ec2 delete-subnet --subnet-id "$subnetId2"&&

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


if [ -e "$jsonFileName" ]; then
	rm -rf "$jsonFileName"
fi


echo
echo "-------Delete Successfully:"

