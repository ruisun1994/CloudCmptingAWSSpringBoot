#!/bin/bash

echo "Enter Your Stack Name:"
read stackname
instanceName="$stackname-csye6225-instance"

vpcId=$(aws ec2 describe-vpcs --filters "Name=cidr-block-association.cidr-block,Values=10.0.0.0/16" --query "Vpcs[0].VpcId" --output text)
echo $vpcId

subnetId=$(aws ec2 describe-subnets --filters "Name=cidrBlock,Values=10.0.0.0/24" --query "Subnets[0].SubnetId" --output text)
echo $subnetId

webpolicy=$(aws ec2 describe-security-groups --filters Name=tag-key,Values="Web" --query "SecurityGroups[0].GroupId" --output text)
echo $webpolicy

dbpolicy=$(aws ec2 describe-security-groups --filters Name=tag-key,Values="db" --query "SecurityGroups[0].GroupId" --output text)
echo $dbpolicy


subnetgroupname=$(aws rds describe-db-subnet-groups --query "DBSubnetGroups[1].DBSubnetGroupName" --output text)
echo $subnetgroupname


aws cloudformation create-stack --template-body file://./csye6225-cf-application.json --stack-name ${stackname} --capabilities "CAPABILITY_NAMED_IAM" --parameters ParameterKey=InstanceName,ParameterValue=$instanceName ParameterKey=SubnetId,ParameterValue=$subnetId ParameterKey=WebPolicy,ParameterValue=$webpolicy ParameterKey=DBPolicy,ParameterValue=$dbpolicy ParameterKey=DBSubnetGroup,ParameterValue=$subnetgroupname

aws cloudformation wait stack-create-complete --stack-name ${stackname} 
echo done
