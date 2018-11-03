package com.niezhiliang.doc.convert.utils;

import org.icepdf.core.pobjects.Document;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2018/11/1 下午2:11
 */
public class PdfToImgs {

    /**
     * pdf转图片
     * @param pdfPath
     * @param dirPath
     */
    public static List<String> pdf2imgs(String pdfPath, String dirPath) {
        Document document = new Document();
        try {
            document.setFile(pdfPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int pages = document.getNumberOfPages();
        List<String> imgList = PagesUtils.getPaths(dirPath,pages);
        FileUtil.isExists(dirPath);
        // 创建一个初始值为5的倒数计数器
        CountDownLatch countDownLatch = new CountDownLatch(3);
        for(int i = 0; i < 3; i++) {
            int begin = i*PagesUtils.getPageCount(pages);
            int end = (i+1)*PagesUtils.getPageCount(pages);
            if (begin >= end) {
                end = begin;
            }
            if (i == 2) {
                Thread thread = new ConvertThrad(countDownLatch, document, imgList,begin,pages);
                thread.start();
            } else {
                Thread thread = new ConvertThrad(countDownLatch, document, imgList,begin,end);
                thread.start();
            }

        }
        try
        {
            // 阻塞当前线程，直到倒数计数器倒数到0
            countDownLatch.await();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        return imgList;
    }
}
