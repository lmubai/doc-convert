package com.niezhiliang.doc.convert.context;

import com.niezhiliang.doc.convert.entity.BaseParams;
import com.niezhiliang.doc.convert.strategys.DocChange;

public class DocContext<T extends BaseParams> {

    private DocChange docChange;

    public DocContext(DocChange docChange) {
        this.docChange = docChange;
    }

    public String docConvert(T t){

        return this.docChange.doJob(t);
    }
}