# Homework 2
Application that calculate average salary and abroad trips count statistics about citizens. 

- Ingest technology - bash

- Storage technology - HDFS

- Computation technology - Spark SQL (DataFrame, DataSet)

## Required tools:
- Unix-like system (tested with Centos 7)
- git
- JDK 1.8 or higher
- hadoop 2.10.0
- maven
- python 3
- Spark 2.3.1 (install script included)

#### Row input format 
    passport №, month,  salary, age, number of abroad trips

#### Row output format
    age category, average salary, average number of abroad trips

or with --full flag

    passport №, age, age category, average salary, average trips

## Set-up

### Clone project
    git clone https://github.com/Artimeo/DS-BDA.git
    cd homework2/

### If Spark is not installed, run
    ./install.sh

## Build & Run
just run this
    
    ./run.sh

## Or do it by youself
    
### Start HDFS
    /opt/hadoop/sbin/start-dfs.sh

### Start Yarn
    ./hadoop/sbin/start-yarn.sh

Now check status with `jps` command
    
    NodeManager
    NameNode
    DataNode
    SecondaryNameNode
    ResourceManager
    Jps

### Optional
    # create folders in hdfs
    hdfs dfs -mkdir /user
    hdfs dfs -mkdir /user/root

    # remove output directory
    hdfs dfs -rm -r /user/root/output

### Generate input data
    rm -rf input/
    mkdir input/
    ./generate.py
    mv sample.csv input/

### Place input data into hdfs
    # hdfs dfs -put path/to/local/file/ path/for/hdfs/file/
    hdfs dfs -rm -r input
    hdfs dfs -rm -r output
    hdfs dfs -put input input

### Run
    # --full flag shows detailed output file
    /opt/spark-2.3.1-bin-hadoop2.7/bin/spark-submit --class SparkApp --master local --deploy-mode client ./target/homework2-1.0-SNAPSHOT-jar-with-dependencies.jar hdfs://localhost:9000/user/root/input/sample.csv hdfs://localhost:9000/user/root/output --full

### Get data from hdfs to local drive
    rm -rf output/
    mkdir output/
    hdfs dfs -copyToLocal /user/root/output/part* ./output/out.csv
