#!/bin/bash
echo "--------Start Run the Set up Application-------------"

# Ask user to enter the stack name
echo "Enter The Stack Name:"
read stackname

echo "You enter name is"
echo $stackname

sleep 2

#Usefull Variables in the Script
serverZone="us-east-1"
vpcName="$stackname-csye6225-vpc"
gatewayName="$stackname-csye6225-InternetGateway"
routeTableName="$stackname-csye6225-route-table"
instanceName="$stackname-csye6225-Instance"
jsonFileName="$stackname-information"
keyName="$stackname-key"

vpcCidrBlock="10.0.0.0/16"
subNetCidrBlock1="10.0.0.0/24"
subNetCidrBlock2="10.0.1.0/24"
destinationCidrBlock="0.0.0.0/0"


echo
#create vpc with cidr block /16
aws_response=$(aws ec2 create-vpc --cidr-block "$vpcCidrBlock") &&
#vpcId=$(echo "$aws_response" | sed -n 's/.*"VpcId": "\(.*\)",/\1/p')
vpcId=$(echo -e "$aws_response" | /usr/bin/jq '.Vpc.VpcId' | tr -d '"') &&
if [ -z "$vpcId" ];then 
	echo "Not Successful"
else echo "Create Successful"
fi

aws ec2 create-tags --resources "$vpcId" --tags Key=Name,Value=$vpcName&&


echo
aws ec2 create-subnet --vpc-id "$vpcId" --cidr-block "$subNetCidrBlock1"&&
aws ec2 create-subnet --vpc-id "$vpcId" --cidr-block "$subNetCidrBlock2"&&

echo
gateway_response=$(aws ec2 create-internet-gateway)&&
gatewayId=$(echo -e "$gateway_response" | /usr/bin/jq '.InternetGateway.InternetGatewayId' | tr -d '"') &&
if [ -z "$gatewayId" ];then 
	echo "Not Successful"
else echo "Create Successful"
fi

echo
route_table_response=$(aws ec2 create-route-table --vpc-id "$vpcId")&&
routeTableId=$(echo -e "$route_table_response" | /usr/bin/jq '.RouteTable.RouteTableId' | tr -d '"')&&
if [ -z "$routeTableId" ];then 
	echo "Not Successful"
else echo "Create Successful"
fi

echo
route_response=$(aws ec2 create-route --route-table-id "$routeTableId" --destination-cidr-block "$destinationCidrBlock" --gateway-id "$gatewayId")&&
if [ -z "$route_response" ];then 
	echo "Not Successful"
else echo "Create Successful"
fi

echo
isActive_response=$(aws ec2 describe-route-tables --route-table-id "$routeTableId")&&
if [ -z "$isActive_response" ];then 
	echo "Not Successful"
else echo "Create Successful"
fi


echo
SubnetInfo=$(aws ec2 describe-subnets --filters "Name=vpc-id,Values='$vpcId'")&&
subnetId1=$(echo -e "$SubnetInfo" | /usr/bin/jq '.Subnets[0].SubnetId' | tr -d '"')&&
subnetId2=$(echo -e "$SubnetInfo" | /usr/bin/jq '.Subnets[1].SubnetId' | tr -d '"')&&

echo
aws ec2 associate-route-table  --subnet-id "$subnetId1" --route-table-id "$routeTableId"&&
aws ec2 modify-subnet-attribute --subnet-id "$subnetId1" --map-public-ip-on-launch&&

echo
aws ec2 create-key-pair --key-name "$keyName" --query 'KeyMaterial' --output text > "$keyName".pem&&
chmod 400 "$keyName".pem&&
createGroupResponse=$(aws ec2 create-security-group --group-name SSHAccess --description "Security group for SSH access" --vpc-id "$vpcId")&&
groupId=$(echo -e "$createGroupResponse" | /usr/bin/jq '.GroupId' | tr -d '"')&&
aws ec2 authorize-security-group-ingress --group-id "$groupId" --protocol tcp --port 22 --cidr "$destinationCidrBlock"&&


runnningInformation=$(aws ec2 run-instances --image-id ami-a4827dc9 --count 1 --instance-type t2.micro --key-name "$keyName" --security-group-ids "$groupId" --subnet-id "$subnetId1")&&
instanceId=$(echo -e "$runnningInformation" | /usr/bin/jq '.Instances[0].InstanceId' | tr -d '"')&&
aws ec2 create-tags --resources "$instanceId" --tags Key=Name,Value=$instanceName&&

# instanceStatus=$(aws ec2 describe-instances --instance-id "$instanceId" --query 'Reservations[0].Instances[0].State.Name' --output text)
# echo $instanceStatus

echo
cat >./"$jsonFileName".json <<EOF
{
	"instanceId": "$instanceId",
	"groupId": "$groupId",
	"subnetId1": "$subnetId1",
	"subnetId2": "$subnetId2",
	"routeTableId": "$routeTableId",
	"gatewayId": "$gatewayId",
	"vpcId": "$vpcId"
}
EOF

echo

