package com.niezhiliang.doc.convert.strategyImpls;

import com.niezhiliang.doc.convert.entity.BaseParams;
import com.niezhiliang.doc.convert.strategys.DocChange;
import com.niezhiliang.doc.convert.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2018/11/1 下午2:20
 */
@Component
public class Pdf2imgStrategyImpl implements DocChange<BaseParams> {
    @Value("${doc.convert.imgspath}")
    private String imgpath;
    @Autowired
    private OssUtil ossUtil;

    @Override
    public String doJob(BaseParams baseParams) {
        List<String> paths = PdfToImgs.pdf2imgs(baseParams.getFilepath(),imgpath);
        List<String> ossPaths = new ArrayList<String>();
        System.out.println(System.currentTimeMillis());
        CountDownLatch countDownLatch = new CountDownLatch(3);
        for(int i = 0; i < 3; i++) {
            int begin = i*PagesUtils.getPageCount(paths.size());
            int end = (i+1)*PagesUtils.getPageCount(paths.size());
            if (begin >= end) {
                end = begin;
            }
            if (i == 2) {
                Thread thread = new UploadThread(ossUtil,paths,ossPaths,begin,paths.size(),countDownLatch);
                thread.start();
            } else {
                Thread thread = new UploadThread(ossUtil,paths,ossPaths,begin,end,countDownLatch);
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
        System.out.println(System.currentTimeMillis());
        return null;
    }
}
