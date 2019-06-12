package com.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * @Description HBase 工具类
 * @Author wangliqiang
 * @Date 2019/6/3 11:08
 */
public class HbaseUtils {

    private static Admin admin = null;
    private static Connection conn = null;

    static {
        // 创建hbase配置对象
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.rootdir", "hdfs://flink01:9000/hbase");
        conf.set("hbase.zookeeper.quorum", "flink01");
        conf.set("hbase.client.scanner.time.period", "600000");
        conf.set("hbase.rpc.timeout", "600000");

        try {
            conn = ConnectionFactory.createConnection(conf);
            // 得到管理程序
            admin = conn.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建表
     *
     * @param tabname
     * @param familyname
     * @throws IOException
     */
    public void createTable(String tabname, String familyname) throws IOException {
        HTableDescriptor tab = new HTableDescriptor(tabname);
        // 添加列族，每个表至少有一个列族
        HColumnDescriptor colDesc = new HColumnDescriptor(familyname);

        tab.addFamily(colDesc);
        // 创建表
        admin.createTable(tab);
        System.out.println("hbase createTable, over");
    }

    /**
     * 插入数据 (多条数据)
     * create "userflaginfo,baseinfo"
     *
     * @param tablename
     * @param rowkey
     * @param familyname
     * @param datamap
     * @throws IOException
     */
    public static void put(String tablename, String rowkey, String familyname, Map<String, String> datamap) throws IOException {
        Table table = conn.getTable(TableName.valueOf(tablename));
        // 将字符串转换为byte[]
        byte[] rowkeybyte = Bytes.toBytes(rowkey);
        Put put = new Put(rowkeybyte);
        if (datamap != null) {
            Set<Map.Entry<String, String>> set = datamap.entrySet();
            for (Map.Entry<String, String> entry : set) {
                String key = entry.getKey();
                Object value = entry.getValue();
                put.addColumn(Bytes.toBytes(familyname), Bytes.toBytes(key), Bytes.toBytes(value + ""));

            }
        }
        table.put(put);
        table.close();
        System.out.println("hbase put, ok");
    }

    /**
     * 获取数据
     *
     * @param tablename
     * @param rowkey
     * @param familyname
     * @param column
     * @return
     */
    public static String getData(String tablename, String rowkey, String familyname, String column) throws IOException {
        Table table = conn.getTable(TableName.valueOf(tablename));
        // 将字符串转换为byte[]
        byte[] rowkeybyte = Bytes.toBytes(rowkey);
        Get get = new Get(rowkeybyte);
        Result result = table.get(get);
        byte[] resultbytes = result.getValue(familyname.getBytes(), column.getBytes());
        if (resultbytes == null) {
            return null;
        }

        return new String(resultbytes);
    }

    /**
     * 插入数据 (一条)
     * create "userflaginfo,baseinfo"
     *
     * @param tablename
     * @param rowkey
     * @param familyname
     * @param column
     * @param data
     */
    public static void putdata(String tablename, String rowkey, String familyname, String column, String data) throws IOException {
        Table table = conn.getTable(TableName.valueOf(tablename));
        Put put = new Put(rowkey.getBytes());
        put.addColumn(familyname.getBytes(), column.getBytes(), data.getBytes());
        table.put(put);
    }

    public static void main(String[] args) throws IOException {
        String string = getData("baseuserscaninfo", "1", "time", "");
        System.out.println(string);
    }

}
