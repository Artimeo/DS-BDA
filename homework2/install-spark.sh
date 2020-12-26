#!/bin/bash
wget https://archive.apache.org/dist/spark/spark-2.3.1/spark-2.3.1-bin-hadoop2.7.tgz
tar xvzf spark-2.3.1-bin-hadoop2.7.tgz -C /opt
rm -rf spark-2.3.1-bin-hadoop2.7.tgz

export HADOOP_PREFIX=/opt/hadoop-2.10.0/
export SPARK_HOME=/opt/spark-2.3.1-bin-hadoop2.7
export HADOOP_CONF_DIR=$HADOOP_PREFIX/etc/hadoop
