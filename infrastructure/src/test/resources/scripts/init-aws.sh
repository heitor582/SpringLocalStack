#!/bin/bash
chmod +x /etc/localstack/init/ready.d/init-aws.sh
awslocal sqs create-queue --queue-name payment-total-queue
awslocal sqs create-queue --queue-name payment-surplus-queue
awslocal sqs create-queue --queue-name payment-partial-queue