package com.niezhiliang.doc.convert.utils;

import com.aspose.cells.Workbook;
import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 文档转换通用类
 */
@Component
public class FileChangeUtils {

    public  boolean getLicense() {
        boolean result = false;
        try {
            InputStream is = FileChangeUtils.class.getClassLoader().getResourceAsStream("license.xml");
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * doc转pdf
     * @param pdfPath
     * @param wordPath
     */
    public void doc2pdf(String pdfPath , String wordPath) {
        // 验证License 若不验证则转化出的pdf文档会有水印产生
        if (!getLicense()) {
            return;
        }
        try {
            long old = System.currentTimeMillis();
            File file = new File(pdfPath);//新建一个空白pdf文档
            //sdasdasdas
            FileOutputStream os = new FileOutputStream(file);
            Document doc = new Document(wordPath);//Address是将要被转化的word文档
            doc.save(os, SaveFormat.PDF);//全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            long now = System.currentTimeMillis();
            System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒");  //转化用时
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * @param excelPath excel file path
     * @param pdfPath pdf file path
     */
    public void excel2pdf(String pdfPath, String excelPath) throws Exception {
        // 验证License
        if (!getLicense()) {
            return;
        }
        try {
            long old = System.currentTimeMillis();
            Workbook wb = new Workbook(new FileInputStream(excelPath));
            File file = new File(pdfPath);// 输出路径
            FileOutputStream fileOS = new FileOutputStream(file);

            wb.save(fileOS, com.aspose.cells.SaveFormat.PDF);

            long now = System.currentTimeMillis();
            System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒\n\n" + "文件保存在:" + file.getPath());
        } catch (Exception e) {
            throw new Exception("convert excel to pdf failed .");
        }
    }
}
