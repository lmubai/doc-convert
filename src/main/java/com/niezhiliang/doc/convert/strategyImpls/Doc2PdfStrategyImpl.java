package com.niezhiliang.doc.convert.strategyImpls;

import com.niezhiliang.doc.convert.entity.BaseParams;
import com.niezhiliang.doc.convert.strategys.DocChange;
import com.niezhiliang.doc.convert.utils.FileChangeUtils;
import com.niezhiliang.doc.convert.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * doc 转 pdf
 */
@Component
public class Doc2PdfStrategyImpl implements DocChange<BaseParams>{
    @Autowired
    private FileChangeUtils fileChangeUtils;
    @Value("${doc.convert.docpath}")
    private String docPath;

    @Override
    public String doJob(BaseParams baseParams) {
        String fileName = FileUtil.randomFileName("pdf");
        String pdfPath = docPath+fileName;

        fileChangeUtils.doc2pdf(pdfPath,baseParams.getFilepath());
        return pdfPath;
    }
}
