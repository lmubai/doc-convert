package com.niezhiliang.doc.convert.utils;


import java.io.*;
import java.nio.channels.FileChannel;
import java.util.Random;

public class FileUtil {

    /**
     *  随机生成不重复的随机数 时间戳加四位随机数
     * @return
     */
    public static String randomFileName() {
        return System.currentTimeMillis()+""+(new Random().nextInt(9999)+1000)+"";
    }

    /**
     *  随机生成不重复的随机数 时间戳加四位随机数带文件后缀
     * @return
     */
    public static String randomFileName(String suffix) {
        return System.currentTimeMillis()+""+(new Random().nextInt(9999)+1000)+"."+suffix;
    }



    /**
     * 判断目录是否存在 不存在创建
     * @param path
     */
    public static void isExists(String path) {
        File file = new File(path);

        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 文件删除
     * @param path
     */
    public static void deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            }
        }
    }

    /**
     * 获取文件名称 带后缀
     * @param path
     */
    public static String getFileName(String path) {

        File file =new File( path.trim());

        return file.getName();
    }


    /**
     *获取不带后缀名的文件名
     * @param path
     * @return
     */
    public static String getFileNameWithoutSuffix(String path){
        File file = new File(path);
        String file_name = file.getName();
        return file_name.substring(0, file_name.lastIndexOf("."));
    }

    /**
     * 获取文件后缀 不带点
     * @return
     */
    public static String getFileSuffix(String path) {
        File file = new File(path);
        String fileName=file.getName();
        return fileName.substring(fileName.lastIndexOf(".")+1);
    }

    /**
     * 获取文件后缀 带点
     * @return
     */
    public static String getFileSuffixWithDone(String path) {
        File file = new File(path);
        String fileName=file.getName();
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 获取文件的大小
     * @param path
     * @return
     */
    public static Long getFileSize(String path) {
        File file = new File(path);
        return file.length();
    }

    /**
     * 文件复制
     * @param sourcePath 源文件路径
     * @param expectPath 目标路径
     */
    public static  void fileChannelCopy(String sourcePath,String expectPath){
        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;

        try {
            fi = new FileInputStream(new File(sourcePath));
            fo = new FileOutputStream( new File(expectPath));
            in = fi.getChannel();//得到对应的文件通道
            out = fo.getChannel();//得到对应的文件通道
			/*
			 * 		 public abstract long transferTo(long position, long count,
             		                     WritableByteChannel target)throws IOException;
			 * 			position - 文件中的位置，从此位置开始传输；必须为非负数
			 * 			count - 要传输的最大字节数；必须为非负数
			 * 			target - 目标通道
			 *			返回：
						实际已传输的字节数，可能为零
			 */
            in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道中
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }finally{
            try {
                fi.close();
                in.close();
                fo.close();
                out.close();
            } catch (IOException e) {

                e.printStackTrace();
            }
        }

    }
}
