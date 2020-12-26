#!/bin/bash
/opt/hadoop-2.10.0/sbin/start-dfs.sh
/opt/hadoop-2.10.0/sbin/start-yarn.sh
mvn clean
mvn package
rm -rf spark-warehouse/

rm -rf input/
mkdir input/
./generate.py
mv sample.csv input/

rm -rf output/
mkdir output/
hdfs dfs -rm -r input
hdfs dfs -rm -r output
hdfs dfs -put input input

/opt/spark-2.3.1-bin-hadoop2.7/bin/spark-submit --class SparkApp --master local --deploy-mode client ./target/homework2-1.0-SNAPSHOT-jar-with-dependencies.jar hdfs://localhost:9000/user/root/input/sample.csv hdfs://localhost:9000/user/root/output --full

hdfs dfs -copyToLocal /user/root/output/part* ./output/out.csv
cat ./output/out.csv
