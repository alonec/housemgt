package com.housemgt.common.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.housemgt.entity.Excel;
import com.housemgt.mapper.ExcelJdbcTemplate;
import com.housemgt.mapper.ExcelMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 * 这里的 DataListListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
 */

@Slf4j
public class DataListListener extends AnalysisEventListener<Excel> {

    /**
     * 每隔3000条存储数据库，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 6000;
    private List<Excel> excelList = new ArrayList<>();

    /**
     * 数据操作
     */
    private ExcelMapper excelMapper;
    private JdbcTemplate jdbcTemplate;


    /**
     * 线程池
     */
    ForkJoinPool forkJoinPool = new ForkJoinPool(8);

    /**
     * 自动注入的是null，所以通过构造器初始化 excelMapper
     * @param excelMapper
     */
    public DataListListener(ExcelMapper excelMapper){
        this.excelMapper = excelMapper;
    }

    /**
     * 自动注入的是null，所以通过构造器初始化 jdbcTemplate
     * @param jdbcTemplate
     */
    public DataListListener(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    /**
     * 这个每一条数据解析都会来调用
     *
     * @param excel
     * @param analysisContext
     */
    @Override
    public void invoke(Excel excel, AnalysisContext analysisContext) {
        excelList.add(excel);

        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (excelList.size() >= BATCH_COUNT) {
            saveExcelByJoinFork();
            // 存储完成清理 list
            excelList.clear();
        }
    }

    /**
     * 所有数据解析完成了，会来调用
     * 这里处理的是分批剩下的最后一批数据.
     *
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveExcelByJoinFork();
    }

    /**
     * 批量存储数据
     * 通过 mybatis
     */
    private void saveExcelByMyBatis(){
        excelMapper.insertBatch(excelList);
    }

    /**
     * 批量存储数据
     * 通过 jdbcTemplate
     */
    private void saveExcelByJdbcTemplate(){
        new ExcelJdbcTemplate(jdbcTemplate).insertBatchByJdbcTemplate(excelList);
    }

    /**
     * 批量存储数据
     *
     * 依然通过JDBCTemplate
     * 但是使用join/fork工具
     */
    private void saveExcelByJoinFork(){
        InsertBatchTask insertBatchTask = new InsertBatchTask(jdbcTemplate, excelList);
        forkJoinPool.invoke(insertBatchTask);
    }
}
