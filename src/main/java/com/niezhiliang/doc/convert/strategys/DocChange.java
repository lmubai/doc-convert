package com.niezhiliang.doc.convert.strategys;


import com.niezhiliang.doc.convert.entity.BaseParams;

public interface DocChange<T extends BaseParams> {

    String doJob(T t);
}
