package com.niezhiliang.doc.convert.strategyImpls;

import com.alibaba.fastjson.JSON;
import com.niezhiliang.doc.convert.entity.BaseParams;
import com.niezhiliang.doc.convert.strategys.DocChange;
import com.niezhiliang.doc.convert.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
    private static final Logger logger = LoggerFactory.getLogger(Pdf2imgStrategyImpl.class);

    @Value("${doc.convert.imgspath}")
    private String imgpath;
    @Autowired
    private OssUtil ossUtil;

    /**
     * 将上传oss的地址以字符串的格式返回
     * @param baseParams
     * @return
     */
    @Override
    public String doJob(BaseParams baseParams) {
        /*******************pdf转imgs(集合里面是生成图片的本地地址)*************************/
        List<String> paths = PdfToImgs.pdf2imgs(baseParams.getFilepath(),imgpath);
        //return JSON.toJSONString(paths);
        /***************图片上传到oss**********************/
        List<String> ossPaths = PagesUtils.getPaths("test",paths.size());
        long start = System.currentTimeMillis();
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
        try {
            // 阻塞当前线程，直到倒数计数器倒数到0
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        logger.info("文件上传耗时"+(end - start) /1000.0+"秒");
        return JSON.toJSONString(ossPaths);
    }
}
