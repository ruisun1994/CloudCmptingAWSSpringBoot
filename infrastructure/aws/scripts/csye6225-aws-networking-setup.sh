#!/bin/bash
echo "--------Start Run the Set up Application-------------"

# Ask user to enter the stack name
echo "Enter The Stack Name:"
read stackname

echo "You enter name is"
echo $stackname
echo "Create in 2 second" 

sleep 2

#Useful Variables in the Script
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
echo "--------Creating VPC-------------"
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
echo "--------Createing Two subnets------"
aws ec2 create-subnet --vpc-id "$vpcId" --cidr-block "$subNetCidrBlock1"&&
aws ec2 create-subnet --vpc-id "$vpcId" --cidr-block "$subNetCidrBlock2"&&

echo
echo "--------Creating Internet Gatewy-------------"
gateway_response=$(aws ec2 create-internet-gateway)&&
gatewayId=$(echo -e "$gateway_response" | /usr/bin/jq '.InternetGateway.InternetGatewayId' | tr -d '"') &&
if [ -z "$gatewayId" ];then 
	echo "Not Successful"
else echo "Create Successful"
fi

echo
echo "--------Renaming Internet Gateway-------------"
aws ec2 create-tags --resources "$gatewayId" --tags Key=Name,Value=$gatewayName&&

echo
echo "--------Attaching gateway to vpc------------"
aws ec2 attach-internet-gateway --vpc-id "$vpcId" --internet-gateway-id "$gatewayId"&&

echo
echo "--------Creating route table for vpc-----------"
route_table_response=$(aws ec2 create-route-table --vpc-id "$vpcId")&&
routeTableId=$(echo -e "$route_table_response" | /usr/bin/jq '.RouteTable.RouteTableId' | tr -d '"')&&
if [ -z "$routeTableId" ];then 
	echo "Not Successful"
else echo "Create Successful"
fi

echo
echo "--------Renaming Route Table--------------"
aws ec2 create-tags --resources "$routeTableId" --tags Key=Name,Value=$routeTableName&&

echo
echo "--------Adding Route for the internet gateway-------"
route_response=$(aws ec2 create-route --route-table-id "$routeTableId" --destination-cidr-block "$destinationCidrBlock" --gateway-id "$gatewayId")&&
if [ -z "$route_response" ];then 
	echo "Not Successful"
else echo "Create Successful"
fi

echo
echo "--------Confirm your route whether active-------------"
isActive_response=$(aws ec2 describe-route-tables --route-table-id "$routeTableId")&&
if [ -z "$isActive_response" ];then 
	echo "Not Successful"
else echo "Create Successful"
fi


echo
echo "---------Associate Route Table with Subnet in VPC-----------"
SubnetInfo=$(aws ec2 describe-subnets --filters "Name=vpc-id,Values='$vpcId'")&&
subnetId1=$(echo -e "$SubnetInfo" | /usr/bin/jq '.Subnets[0].SubnetId' | tr -d '"')&&
subnetId2=$(echo -e "$SubnetInfo" | /usr/bin/jq '.Subnets[1].SubnetId' | tr -d '"')&&

echo
echo "---------Deal with two Subnets-----------"
aws ec2 associate-route-table  --subnet-id "$subnetId1" --route-table-id "$routeTableId"&&
aws ec2 modify-subnet-attribute --subnet-id "$subnetId1" --map-public-ip-on-launch&&

echo
echo "---------Launch the instance with public subnet----"
aws ec2 create-key-pair --key-name "$keyName" --query 'KeyMaterial' --output text > "$keyName".pem&&
chmod 400 "$keyName".pem&&
createGroupResponse=$(aws ec2 create-security-group --group-name SSHAccess --description "Security group for SSH access" --vpc-id "$vpcId")&&
groupId=$(echo -e "$createGroupResponse" | /usr/bin/jq '.GroupId' | tr -d '"')&&
aws ec2 authorize-security-group-ingress --group-id "$groupId" --protocol tcp --port 22 --cidr "$destinationCidrBlock"&&


runnningInformation=$(aws ec2 run-instances --image-id ami-a4827dc9 --count 1 --instance-type t2.micro --key-name "$keyName" --security-group-ids "$groupId" --subnet-id "$subnetId1")&&
instanceId=$(echo -e "$runnningInformation" | /usr/bin/jq '.Instances[0].InstanceId' | tr -d '"')&&
echo $instanceId
aws ec2 create-tags --resources "$instanceId" --tags Key=Name,Value=$instanceName&&

# instanceStatus=$(aws ec2 describe-instances --instance-id "$instanceId" --query 'Reservations[0].Instances[0].State.Name' --output text)
# echo $instanceStatus

echo
echo "-----------Writing JSON file-------"
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
echo "-----------Success!!!---------------"

