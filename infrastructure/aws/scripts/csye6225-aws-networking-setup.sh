#!/bin/bash
echo "--------Start Run the Set up Application-------------"

# Ask user to enter the stack name
echo "Enter The Name:"
read stackname

echo "You enter name is"
echo $stackname



#Usefull Variables in the Script
serverZone="us-east-1"
vpcName="$stackname-csye6225-vpc"
gatewayName="$stackname-csye6225-InternetGateway"
routeTableName="$stackname-csye6225-route-table"
# instanceName="$stackname-csye6225-Instance"
jsonFileName="$stackname-information"
#keyName="$stackname-key"

vpcCidrBlock="10.0.0.0/16"
# subNetCidrBlock1="10.0.0.0/24"
# subNetCidrBlock2="10.0.1.0/24"
destinationCidrBlock="0.0.0.0/0"
applicationName="$stackname-csye6225-application"

echo
echo "--------Check Whether VPC is Existed:"
VPC_ID0=$(aws ec2 describe-vpcs --filter Name=tag:Name,Values="$vpcName" --query 'Vpcs[0].VpcId')&&
#echo $VPC_ID0
if
	[ "$VPC_ID0" != null ];then
	echo "VPC has already existed, terminate it first!"
	exit 0
fi

echo
echo "--------Creating VPC:"
#create vpc with cidr block /16
aws_response=$(aws ec2 create-vpc --cidr-block "$vpcCidrBlock") &&
#vpcId=$(echo "$aws_response" | sed -n 's/.*"VpcId": "\(.*\)",/\1/p')
vpcId=$(echo -e "$aws_response" | /usr/bin/jq '.Vpc.VpcId' | tr -d '"') &&
if [ -z "$vpcId" ];then
	echo "Not Successful"
else echo "Create Successful"
fi


echo
echo "--------Creating Internet Gateway:"
gateway_response=$(aws ec2 create-internet-gateway)&&
gatewayId=$(echo -e "$gateway_response" | /usr/bin/jq '.InternetGateway.InternetGatewayId' | tr -d '"') &&
if [ -z "$gatewayId" ];then
	echo "Not Successful"
else echo "Create Successful"
fi

echo
echo "--------Attaching gateway to vpc:"
aws ec2 attach-internet-gateway --vpc-id "$vpcId" --internet-gateway-id "$gatewayId"&&


echo
echo "--------Creating RouteTable:"
route_table_response=$(aws ec2 create-route-table --vpc-id "$vpcId")&&
routeTableId=$(echo -e "$route_table_response" | /usr/bin/jq '.RouteTable.RouteTableId' | tr -d '"')&&
if [ -z "$routeTableId" ];then
	echo "Not Successful"
else echo "Create Successful"
fi

echo
echo "--------Adding Route for the internet gateway:"
route_response=$(aws ec2 create-route --route-table-id "$routeTableId" --destination-cidr-block "$destinationCidrBlock" --gateway-id "$gatewayId")&&
if [ -z "$route_response" ];then
	echo "Not Successful"
else echo "Create Successful"
fi


echo
isActive_response=$(aws ec2 describe-route-tables --route-table-id "$routeTableId")&&

if [ -z "$isActive_response" ];then 
	echo "Route is not Active"
else echo "Route is Active"
fi

echo
echo "--------Renaming:"
aws ec2 create-tags --resources "$vpcId" --tags Key=Name,Value=$vpcName&&
aws ec2 create-tags --resources "$gatewayId" --tags Key=Name,Value=$gatewayName&&
aws ec2 create-tags --resources "$routeTableId" --tags Key=Name,Value=$routeTableName&&



# echo
# echo "--------Associate Route Table with Subnet in VPC:"
# SubnetInfo=$(aws ec2 describe-subnets --filters "Name=vpc-id,Values='$vpcId'")&&
# subnetId1=$(echo -e "$SubnetInfo" | /usr/bin/jq '.Subnets[0].SubnetId' | tr -d '"')&&
# subnetId2=$(echo -e "$SubnetInfo" | /usr/bin/jq '.Subnets[1].SubnetId' | tr -d '"')&&

# echo
# echo "--------Deal with two Subnets:"
# aws ec2 associate-route-table  --subnet-id "$subnetId1" --route-table-id "$routeTableId"&&
# aws ec2 modify-subnet-attribute --subnet-id "$subnetId1" --map-public-ip-on-launch&&

# echo
# echo "--------Create Ket pair:"
# aws ec2 create-key-pair --key-name "$keyName" --query 'KeyMaterial' --output text > "$keyName".pem&&
# chmod 400 "$keyName".pem&&


# echo
# echo "--------Associate Route Table with Subnet in VPC:"
# SubnetInfo=$(aws ec2 describe-subnets --filters "Name=vpc-id,Values='$vpcId'")&&
# subnetId1=$(echo -e "$SubnetInfo" | /usr/bin/jq '.Subnets[0].SubnetId' | tr -d '"')&&
# subnetId2=$(echo -e "$SubnetInfo" | /usr/bin/jq '.Subnets[1].SubnetId' | tr -d '"')&&

# echo
# echo "--------Deal with two Subnets:"
# aws ec2 associate-route-table  --subnet-id "$subnetId1" --route-table-id "$routeTableId"&&
# aws ec2 modify-subnet-attribute --subnet-id "$subnetId1" --map-public-ip-on-launch&&


# echo
# echo "--------Create Ket pair:"
# aws ec2 create-key-pair --key-name "$keyName" --query 'KeyMaterial' --output text > "$keyName".pem&&
# chmod 400 "$keyName".pem&&


# createGroupResponse=$(aws ec2 create-security-group --group-name SSHAccess --description "Security group for SSH access" --vpc-id "$vpcId")&&
# groupId=$(echo -e "$createGroupResponse" | /usr/bin/jq '.GroupId' | tr -d '"')&&
# aws ec2 authorize-security-group-ingress --group-id "$groupId" --protocol tcp --port 22 --cidr "$destinationCidrBlock"&&


# createGroupResponse=$(aws ec2 create-security-group --group-name SSHAccess --description "Security group for SSH access" --vpc-id "$vpcId")&&
# groupId=$(echo -e "$createGroupResponse" | /usr/bin/jq '.GroupId' | tr -d '"')&&
# aws ec2 authorize-security-group-ingress --group-id "$groupId" --protocol tcp --port 22 --cidr "$destinationCidrBlock"&&


# runnningInformation=$(aws ec2 run-instances --image-id ami-a4827dc9 --count 1 --instance-type t2.micro --key-name "$keyName" --security-group-ids "$groupId" --subnet-id "$subnetId1")&&
# instanceId=$(echo -e "$runnningInformation" | /usr/bin/jq '.Instances[0].InstanceId' | tr -d '"')&&
# aws ec2 create-tags --resources "$instanceId" --tags Key=Name,Value=$instanceName&&
# "groupId": "$groupId"
# "instanceId": "$instanceId",
# "subnetId1": "$subnetId1",
# "subnetId2": "$subnetId2",

Vpc_State=$(aws ec2 describe-vpcs --filter Name=tag:Name,Values="$vpcName" --query 'Vpcs[0].State' --output text)
echo $Vpc_State
if [ "$Vpc_State" == "available" ]; then
	echo "Create Successfully"
else
	echo "Failed"
fi

echo
echo "--------Create Application"
aws deploy create-application --application-name "$applicationName"

awsAppName=$(aws deploy get-application --application-name "$applicationName" --query 'application.applicationName' --output text)
if [[ $awsAppName == $applicationName ]]; then
	#statements
	echo "--------Create Application Successfully!"
else
	echo "--------Failed to Create Application!"
fi

echo
echo "--------Writing JSON file:"
cat >./"$jsonFileName".json <<EOF
{
	"routeTableId": "$routeTableId",
	"gatewayId": "$gatewayId",
	"vpcId": "$vpcId",
	"applicationName": "$applicationName"
}
EOF
