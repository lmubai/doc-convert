package com.niezhiliang.doc.convert.context;

import com.niezhiliang.doc.convert.entity.BaseParams;
import com.niezhiliang.doc.convert.strategyImpls.Doc2PdfStrategyImpl;
import com.niezhiliang.doc.convert.strategyImpls.Excel2PdfStrategyImpl;
import com.niezhiliang.doc.convert.strategyImpls.Pdf2imgStrategyImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
@EnableAutoConfiguration
public class DocContextTest {
    @Value("${doc.convert.docpath}")
    private String docPath;
    @Autowired
    private Doc2PdfStrategyImpl doc2PdfStrategy;
    @Autowired
    private Excel2PdfStrategyImpl excel2PdfStrategy;
    @Autowired
    private Pdf2imgStrategyImpl pdf2imgStrategy;

    /**
     * doc转pdf
     */
    @Test
    public void docToPdf() {
        BaseParams baseParams = new BaseParams();
        baseParams.setFilepath(docPath+"短信对接文档.doc");
        DocContext context = new DocContext(doc2PdfStrategy);
        context.docConvert(baseParams);
    }


    @Test
    public void excelToPdf() {
        BaseParams baseParams = new BaseParams();
        baseParams.setFilepath(docPath+"个人信息表填写模板.xls");
        DocContext context = new DocContext(excel2PdfStrategy);
        context.docConvert(baseParams);
    }

    @Test
    public void pdfToImgs() {//100892  75733  74752 83025 91760 87374
        //10个线程 55433 59150
        //5个线程 69222 56878  58516
        //4个线程 67932 64588 59493
        //3个线程 55544 53733 53023  69795 50010 51710
        //2个线程 65448 58113 55174
        //1个线程 88000
        long start = System.currentTimeMillis();
        BaseParams baseParams = new BaseParams();
        baseParams.setFilepath(docPath+"1.pdf");
        DocContext context = new DocContext(pdf2imgStrategy);
        context.docConvert(baseParams);
        long end = System.currentTimeMillis();
        System.out.println("共耗时:"+(end - start) /1000.0 +"秒");
    }
}