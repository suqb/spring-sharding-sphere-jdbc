package com.suqb.www.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.suqb.www.domain.excel.Entity;
import com.suqb.www.domain.excel.ExportExcelEntity;
import com.suqb.www.exception.NoMoneyException;

import java.util.List;

// 有个很重要的点 DataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
public class DataListener <T> implements ReadListener<T>
{
    List<T> dataList;

    public DataListener(List<T> data)
    {
        this.dataList = data;
        // 这里是demo，所以随便new一个。实际使用如果到了spring,请使用下面的有参构造函数
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(T data, AnalysisContext context)
    {
        dataList.add(data);
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context)
    {
    }


    public void th()
    {
        throw new NoMoneyException();
    }
}