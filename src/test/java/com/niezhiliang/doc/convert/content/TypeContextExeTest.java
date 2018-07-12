package com.niezhiliang.doc.convert.content;

import cc.huluwa.electronic.contract.open.doc.convert.entity.DocAndExlParams;
import cc.huluwa.electronic.contract.open.doc.convert.entity.PdfParams;
import cc.huluwa.electronic.contract.open.doc.convert.enums.DocEnum;
import cc.huluwa.electronic.contract.open.doc.convert.enums.TypeEnum;
import cc.huluwa.electronic.contract.open.doc.convert.strategyimpls.DocStrategyImpl;
import cc.huluwa.electronic.contract.open.doc.convert.utils.Doc2PDF;
import cc.huluwa.electronic.contract.open.doc.convert.utils.Pdf2IMG;
import cc.huluwa.electronic.contract.engine.alioss.util.Oss;
import org.icepdf.core.exceptions.PDFException;
import org.icepdf.core.exceptions.PDFSecurityException;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

@SpringBootTest
@RunWith(SpringRunner.class)
@EnableAutoConfiguration
public class TypeContextExeTest {

    @Autowired
    private DocStrategyImpl docStrategy;
    @Value("${doc.convert.imgpath}")
    private String imgpath;
    @Value("${aliyun.office}")
    private String pdfKey;
    @Value("${doc.convert.docpath}")
    private String docpath;
    @Value("${doc.convert.pdfpath}")
    private String pdfpath;
    @Autowired
    private Oss oss;
    @Autowired
    private Pdf2IMG pdf2IMG;

    @Test
    @Ignore
    public void testDoc2PDF() throws InterruptedException {
            TypeContextExe typeContextExe = new TypeContextExe(docStrategy);
            DocAndExlParams docAndExlParams = new DocAndExlParams();
            docAndExlParams.setPath(docpath+"test.doc");
            docAndExlParams.setType(TypeEnum.DOCTOPDF);
            docAndExlParams.setFileType(DocEnum.DOC);
            docAndExlParams.setUserid(1l);
            docAndExlParams.setUsercontractid(1l);
            typeContextExe.doJob(docAndExlParams);
            Thread.sleep(2000000);
    }

    @Test
    @Ignore
    public void testExcel2PDF() throws InterruptedException {
        TypeContextExe typeContextExe = new TypeContextExe(docStrategy);
        DocAndExlParams docAndExlParams = new DocAndExlParams();
        docAndExlParams.setPath(docpath+"test.xls");
        docAndExlParams.setType(TypeEnum.DOCTOPDF);
        docAndExlParams.setFileType(DocEnum.XLS);
        docAndExlParams.setUserid(1l);
        docAndExlParams.setUsercontractid(1l);
        typeContextExe.doJob(docAndExlParams);
        Thread.sleep(2000000);
    }

    @Test
    public void testPdf2PDF() throws InterruptedException {
        TypeContextExe typeContextExe = new TypeContextExe(docStrategy);
        DocAndExlParams docAndExlParams = new DocAndExlParams();
        docAndExlParams.setPath(docpath+"test.pdf");
        docAndExlParams.setType(TypeEnum.DOCTOPDF);
        docAndExlParams.setFileType(DocEnum.PDF);
        docAndExlParams.setUserid(1l);
        docAndExlParams.setUsercontractid(1l);
        typeContextExe.doJob(docAndExlParams);
        Thread.sleep(2000000);
    }

    @Test
    @Ignore
    public void testupload() {
        oss.upload(new File("/Users/lujunling/Desktop/electronic-contract-open-api/open-api-service/doc-convert/tmp/doc/test.doc"),"office/1531191595607.doc");
        oss.upload(new File("/Users/lujunling/Desktop/electronic-contract-open-api/open-api-service/doc-convert/tmp/pdf/1531191585290.pdf"),"pdf/1531191595607.pdf");
    }

    @Test
    @Ignore
    public void testPdf2Img() {
        PdfParams pdfParams = new PdfParams();
        pdfParams.setPdfpath("/Users/lujunling/Desktop/electronic-contract-open-api/open-api-service/doc-convert/tmp/pdf/1531202890454.pdf");
        try {
            pdfParams = pdf2IMG.pdf2Image(pdfParams);
            for (String str : pdfParams.getSysImgPaths()) {
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (PDFException e) {
            e.printStackTrace();
        } catch (PDFSecurityException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void excelToPdf() {
        String path = docpath+"test.xls";
        try {
            new Doc2PDF().excel2pdf(pdfpath+"大小合同.pdf",path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}