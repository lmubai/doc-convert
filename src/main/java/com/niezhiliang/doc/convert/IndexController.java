package com.niezhiliang.doc.convert;

import com.niezhiliang.doc.convert.context.DocContext;
import com.niezhiliang.doc.convert.entity.BaseParams;
import com.niezhiliang.doc.convert.strategyImpls.Pdf2imgStrategyImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2018/11/2 上午11:32
 */
@RestController
public class IndexController {
    @Autowired
    private Pdf2imgStrategyImpl pdf2imgStrategy;

    @RequestMapping(value = "1")
    public String index() {
        long start = System.currentTimeMillis();
        BaseParams baseParams = new BaseParams();
        baseParams.setFilepath("/tmp/中国消费者.pdf");
        DocContext context = new DocContext(pdf2imgStrategy);
        context.docConvert(baseParams);
        long end = System.currentTimeMillis();
        System.out.println("共耗时:"+(end - start) /1000.0 +"秒");
        return "共耗时:"+(end - start) /1000.0 +"秒";
    }

    @RequestMapping(value = "2")
    public String index2() {
        long start = System.currentTimeMillis();
        BaseParams baseParams = new BaseParams();
        baseParams.setFilepath("/tmp/1.pdf");
        DocContext context = new DocContext(pdf2imgStrategy);
        context.docConvert(baseParams);
        long end = System.currentTimeMillis();
        System.out.println("共耗时:"+(end - start) /1000.0 +"秒");
        return "共耗时:"+(end - start) /1000.0 +"秒";
    }
}
