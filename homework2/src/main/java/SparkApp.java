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
        if (args.length < 2) {
            System.out.println("Usage: java -jar App.jar inputFile.csv outputDirectory");
            System.exit(1);
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

        Dataset<Row> results = spark.sql("select passportNumber, Avg(Salary) as average_salary, Avg(trips) as average_trips from people group by passportNumber");
        results.show();
        results.repartition(1)
                .write()
                .mode ("overwrite")
                .format("com.databricks.spark.csv")
                .option("header", "false")
                .save(output);
    }
}
