# Build and deploy manual

## Required tools:
- Unix-like system (tested with Centos 7)
- git
- JDK 1.8 or higher
- hadoop 2.10.0
- maven

## Build

### Clone project
    git clone https://github.com/Artimeo/DS-BDA.git
    cd homework1/

### Build project
    mvn clean && mvn package

Result will have been located at `target` folder.

## Prepare host
- Replace `/opt/hadoop` in the next commands with `/your/path/to/hadoop` if needed.
- Provide configuration files for hadoop

### Start HDFS
    /opt/hadoop/sbin/start-dfs.sh

### Start Yarn
    ./hadoop/sbin/start-yarn.sh

Now check status with `jps` command

### Optional
    # create folders in hdfs
    hdfs dfs -mkdir /user
    hdfs dfs -mkdir /user/root

    # remove output directory
    hdfs dfs -rm -r /user/root/output

## Generate input data

### Run generation script
    generator.py -c 2
### Place the data into hdfs
    # hdfs dfs -put path/to/local/file/ path/for/hdfs/file/
    hdfs dfs -put ./input /user/root/input

Make sure that you have deleted previous `output` folder from hdfs.

Connect output folder in hdfs with local output folder to be able to save result on disk.

## Run App
    # yarn jar path/to/jar /path/to/hdfs/input/data /path/to/hdfs/output/data
    yarn jar ./target/homework1-1.0-jar-with-dependencies.jar /user/root/input /user/root/output

