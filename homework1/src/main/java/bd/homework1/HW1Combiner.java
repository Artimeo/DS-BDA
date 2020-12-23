package bd.homework1;

import jdk.nashorn.internal.runtime.Context;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.htrace.fasterxml.jackson.annotation.ObjectIdGenerators;
import sun.rmi.runtime.Log;

import java.io.IOException;

/**
 * Combiner:
 * - sums all bytes received from Mapper, outs overall bytes count
 * - sums all requests received from Mapper, outs overall requests count from the concrete IP
 */

public class HW1Combiner extends Reducer<Text, DataWritable, Text, DataWritable> {
    private IntWritable bytes = new IntWritable();
    private IntWritable requests = new IntWritable();
    private DataWritable data = new DataWritable();

    @Override
    public void reduce(Text key, Iterable<DataWritable> values, Context context) throws IOException, InterruptedException {
        int sumBytes = 0;
        int requestsCount = 0;

        for(DataWritable i : values){
            sumBytes += i.getSumBytes().get();
            requestsCount += i.getRequestsCount().get();
        }

        bytes.set(sumBytes);
        requests.set(requestsCount);

        data.set(bytes, requests, new FloatWritable(0));
        context.write(key, data);
    }
}
