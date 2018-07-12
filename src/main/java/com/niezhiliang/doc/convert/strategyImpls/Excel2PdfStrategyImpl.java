package com.niezhiliang.doc.convert.strategyImpls;

import com.niezhiliang.doc.convert.entity.BaseParams;
import com.niezhiliang.doc.convert.strategys.DocChange;
import com.niezhiliang.doc.convert.utils.FileChangeUtils;
import com.niezhiliang.doc.convert.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Excel2PdfStrategyImpl implements DocChange<BaseParams> {
    @Autowired
    private FileUtil fileUtil;
    @Autowired
    private FileChangeUtils fileChangeUtils;
    @Value("${doc.convert.docpath}")
    private String docPath;

    @Override
    public String doJob(BaseParams baseParams) {
        String fileName = fileUtil.randomFileName("pdf");
        String excelPath = docPath+fileName;

        try {
            fileChangeUtils.excel2pdf(excelPath,baseParams.getFilepath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return excelPath;
    }
}
