package com.logic;

import java.util.ArrayList;

/**
 * @Description [主要用于保存特征信息以及标签值]
 * @Parameter labels: [主要保存标签值]
 * @Author wangliqiang
 * @Date 2019/6/5 14:24
 */
public class CreateDataSet extends Matrix {

    public ArrayList<String> labels;
    public CreateDataSet(){
        super();
        labels = new ArrayList<String>();
    }
}
