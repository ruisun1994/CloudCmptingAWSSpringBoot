#!/bin/bash

vpcCidrBlock="10.0.0.0/16"

echo Hello World.
aws ec2 create-vpc --cidr-block "$vpcCidrBlock"
aws ec2 create-internet-gateway
