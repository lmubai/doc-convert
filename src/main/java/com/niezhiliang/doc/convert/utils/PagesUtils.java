package com.niezhiliang.doc.convert.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2018/11/2 上午8:11
 */
public class PagesUtils {
    public static int getPageCount(int pages) {
        int i = pages / 3;
        if (i < 1) {
            return 1;
        } else {
            return i;
        }
    }

    public static List<String> getPaths(String dirPath,int pages) {
        List<String> imgList = new ArrayList<String>();
        for (int i = 0; i < pages; i++) {
            String path = dirPath + "/" + FileUtil.randomFileName("png");
            imgList.add(path);
        }
        return imgList;
    }
}
