package conf_1130;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.mockito.exceptions.Reporter;

public class Ex3 {
	public static class Map extends Mapper<LongWritable, Text, Text, Text> {
		private HashSet<String> ip_time = new HashSet<String>();
		private HashSet<String> ip_time2 = new HashSet<String>();
		private String ip_num = null;
		//初始化，获取全局变量。
		public void setup(Context context) throws IOException {
			Configuration conf = context.getConfiguration();
			FileSystem fs = FileSystem.get(conf);
			Path customIPsPath = new Path(conf.get("custom_ips_hdfs_path"));
			InputStream inStream = fs.open(customIPsPath);
			Scanner scanner = new Scanner(inStream);
			while (scanner.hasNext()) {
				String[] line = scanner.nextLine().split("\t");
				ip_time.add(line[0]);
			}
		}

		public void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			
			String[] str = value.toString().split("\t");
			if (ip_time.contains(str[0])) {
				ip_time2.add(str[0]);
			}
			ip_num = String.valueOf(ip_time2.size());
		}
		//释放工作，输出。
		public void cleanup(Context context) throws IOException,
				InterruptedException {
			context.write(new Text("IP number"), new Text(ip_num));
		}
	}
	//没有reduce
	public static class Reduce extends Reducer<Text, Text, Text, Text> {
		public void reduce(Text key, Iterator<Text> values,
				OutputCollector<Text, Text> output, Reporter report)
				throws IOException {
		}
	}

	public static void main(String[] args) throws IOException,
			ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		conf.set("custom_ips_hdfs_path", args[2]);
		Job job = Job.getInstance(conf);
		job.setJarByClass(conf_setup_1130.class);
		job.setMapperClass(Map.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setReducerClass(Reduce.class);
		job.setNumReduceTasks(0);
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		job.waitForCompletion(true);
		return;
	}
}
