package com.niezhiliang.doc.convert.context;

import com.niezhiliang.doc.convert.entity.BaseParams;
import com.niezhiliang.doc.convert.strategyImpls.Doc2PdfStrategyImpl;
import com.niezhiliang.doc.convert.strategyImpls.Excel2PdfStrategyImpl;
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
}