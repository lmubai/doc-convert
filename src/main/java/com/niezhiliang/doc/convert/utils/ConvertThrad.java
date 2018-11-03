package com.niezhiliang.doc.convert.utils;

import org.icepdf.core.pobjects.Document;
import org.icepdf.core.util.GraphicsRenderingHints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger logger = LoggerFactory.getLogger(ConvertThrad.class);

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
        logger.info(this.getName() + "子线程开始");
        BufferedImage image = null;
        //缩放比例
        float scale = 2.0f;
        //旋转角度
        float rotation = 0f;
        try {
            for (int i = beginNumber; i < endNumber; i++) {
                logger.info(this.getName() +"开始转换第"+i +"页图片");
                image = (BufferedImage)
                        document.getPageImage(i, GraphicsRenderingHints.SCREEN, org.icepdf.core.pobjects.Page.BOUNDARY_CROPBOX, rotation, scale);
                RenderedImage rendImage = image;
                String path = paths.get(i);
                File file = new File(path);
                ImageIO.write(rendImage, "png", file);
                image.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.dispose();
            logger.info(this.getName() + "子线程结束");
            // 倒数器减1
            countDownLatch.countDown();
        }
    }
}
