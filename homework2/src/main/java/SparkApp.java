import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.*;
import scala.Tuple3;
import scala.collection.mutable.MutableList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.apache.commons.lang.StringUtils.split;
import static org.apache.commons.lang3.StringUtils.SPACE;

public class SparkApp {
    public static void main(String[] args) {
        boolean printFullResults = false;
        if (args.length < 2) {
            System.out.println("Usage: java -jar App.jar inputFile.csv outputDirectory");
            System.exit(1);
        }
        if (args.length == 3 && args[2].equals("--full")) {
            printFullResults = true;
        }
        String input = args[0];
        String output = args[1];


        SparkSession spark = SparkSession
                .builder()
                .master("local")
                .appName("SparkApp")
                .getOrCreate();

        // Read the input file as string lines
        JavaRDD<String> linesRDD = spark.read().textFile(input).javaRDD();
        // Gets RDD of class Person from each line
        JavaRDD<Person> personsRDD = linesRDD
                .map(line -> Person.fromString(line));

        // Apply a schema to an RDD of JavaBeans to get a DataFrame
        Dataset<Row> personsDF = spark.createDataFrame(personsRDD, Person.class);
        personsDF.printSchema();
        // Register the DataFrame as a temporary view
        personsDF.createOrReplaceTempView("people");

        // chooses output style - full or compact
        String sqlExp = printFullResults
                ? "select passportNumber, age, category, Avg(salary) as average_salary, Avg(trips) as average_trips from people group by passportNumber, age, category"
                : "select category, Avg(salary) as average_salary, avg(trips) as average_trips from people group by passportNumber, category";
        Dataset<Row> results = spark.sql(sqlExp);
        results.show();

        // writes data into hdfs
        results.repartition(1)
                .write()
                .mode ("overwrite")
                .format("com.databricks.spark.csv")
                .option("header", "false")
                .save(output);
    }
}
