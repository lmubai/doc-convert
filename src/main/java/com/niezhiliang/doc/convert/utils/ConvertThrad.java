package com.niezhiliang.doc.convert.utils;

import org.icepdf.core.pobjects.Document;
import org.icepdf.core.util.GraphicsRenderingHints;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2018/11/1 下午2:51
 */
public class ConvertThrad extends Thread  {

    private CountDownLatch countDownLatch;

    private Document document;

    private int beginNumber;

    private int endNumber;

    private List<String> paths;

    public ConvertThrad(CountDownLatch countDownLatch,Document document,List<String> paths,int beginNumber,int endNumber)
    {
        this.countDownLatch = countDownLatch;
        this.document = document;
        this.beginNumber = beginNumber;
        this.endNumber = endNumber;
        this.paths = paths;
    }

    public void run() {
        System.out.println(this.getName() + "子线程开始");
        BufferedImage image = null;
        //缩放比例
        float scale = 2.0f;
        //旋转角度
        float rotation = 0f;
        for (int i = beginNumber; i < endNumber; i++) {
            System.out.println(this.getName() +"开始转换第"+i +"页图片");
            try {
                image = (BufferedImage)
                        document.getPageImage(i, GraphicsRenderingHints.SCREEN, org.icepdf.core.pobjects.Page.BOUNDARY_CROPBOX, rotation, scale);
                RenderedImage rendImage = image;
                String path = paths.get(i);
                File file = new File(path);
                ImageIO.write(rendImage, "png", file);
            } catch (Exception e) {
                e.printStackTrace();
            }
            image.flush();
        }
        document.dispose();
        System.out.println(this.getName() + "子线程结束");

        // 倒数器减1
        countDownLatch.countDown();
    }
}
