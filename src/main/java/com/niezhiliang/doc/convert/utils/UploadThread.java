package com.niezhiliang.doc.convert.utils;

import java.io.File;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2018/11/2 上午10:32
 */
public class UploadThread extends Thread {

    private CountDownLatch countDownLatch;

    private List<String> imgsPath;

    private List<String> ossPath;

    private int beginNumber;

    private int endNumber;

    private OssUtil ossUtil;

    public UploadThread(OssUtil ossUtil,List<String> imgsPath, List<String> ossPath, int beginNumber, int endNumber,CountDownLatch countDownLatch) {
        this.ossUtil = ossUtil;
        this.imgsPath = imgsPath;
        this.ossPath = ossPath;
        this.beginNumber = beginNumber;
        this.endNumber = endNumber;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        System.out.println(this.getName() + "子线程上传开始");
        for (int i = beginNumber; i < endNumber; i++) {
            String paht =
                    ossUtil.upload(new File(imgsPath.get(i)),"test"+imgsPath.get(i).replace("/tmp/imgs/",""));
            ossPath.add(paht);
            System.out.println("正在上传"+i+"张图片");
        }
        countDownLatch.countDown();
        System.out.println(this.getName() + "子线程上传结束");
    }
}
