package com.housemgt.controller.setparam;

import com.housemgt.common.msg.CodeMsg;
import com.housemgt.common.msg.ResultMsg;
import com.housemgt.model.MetaData;
import com.housemgt.service.setparam.MetaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Iterator;
import java.util.List;

/***
 * 元数据配置
 * @author chenxin
 */
@Controller
public class MetaDataController {

    @Autowired
    private MetaDataService metaDataService;

    @ResponseBody
    @RequestMapping(value = "/rule/meta/add")
    public Object add(@RequestParam("bizType") Integer bizType,
                        @RequestParam("serealName") String serealName) {
        MetaData metaData = new MetaData();
        metaData.setBizType(bizType);
        metaData.setSerealName(serealName);

        // 查询到业务名称
        List<MetaData> metaData1 = metaDataService.selectByBizType(bizType);
        if (metaData1 != null && metaData1.size() > 0) {
            metaData.setBizName(metaData1.get(0).getBizName());
        }
        ResultMsg resultMsg = null;
        try {
            if (metaDataService.insert(metaData) > 0){
                resultMsg = ResultMsg.success();
            } else {
                resultMsg = ResultMsg.error(CodeMsg.ERROR);
                System.out.println("添加失败");
            }
        } catch (Exception e){
            resultMsg = ResultMsg.error(CodeMsg.ERROR);
        }
        return resultMsg;
    }

    @ResponseBody
    @RequestMapping(value = "/rule/meta/delete")
    public Object delete(@RequestParam("serealId") Integer serealId) {
        ResultMsg resultMsg = null;
        try {
            if (metaDataService.deleteBySerealId(serealId) > 0){
                resultMsg = ResultMsg.success();
            } else {
                resultMsg = ResultMsg.error(CodeMsg.ERROR);
                System.out.println("删除失败");
            }
        } catch (Exception e){
            resultMsg = ResultMsg.error(CodeMsg.ERROR);
        }
        return resultMsg;
    }

    @ResponseBody
    @RequestMapping(value = "/rule/meta/update")
    public Object update(@RequestParam("bizName") String bizName,
                         @RequestParam("bizType") Integer bizType,
                         @RequestParam("serealName") String serealName,
                         @RequestParam("serealId") Integer serealId) {
        MetaData metaData = new MetaData();
        metaData.setBizName(bizName);
        metaData.setBizType(bizType);
        metaData.setSerealName(serealName);
        metaData.setSerealId(serealId);
        System.out.println(metaData.toString());
        ResultMsg resultMsg = null;
        try {
            if (metaDataService.updateBySerealId(metaData) > 0){
                resultMsg = ResultMsg.success();
            } else {
                resultMsg = ResultMsg.error(CodeMsg.ERROR);
                System.out.println("修改失败");
            }
        } catch (Exception e){
            resultMsg = ResultMsg.error(CodeMsg.ERROR);
        }
        return resultMsg;
    }

    @ResponseBody
    @RequestMapping(value = "/rule/meta/selectAll")
    public Object selectAll() {

        ResultMsg resultMsg = null;
        try {
            List<MetaData> dataList = metaDataService.selectAll();
            if (dataList != null && dataList.size() > 0){
                resultMsg = ResultMsg.success(dataList);
            } else {
                resultMsg = ResultMsg.success();
            }
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("查询失败");
            resultMsg = ResultMsg.error(CodeMsg.ERROR);
        }
        return resultMsg;
    }

    @ResponseBody
    @RequestMapping(value = "/rule/meta/selectBizType")
    public Object selectBizType() {

        ResultMsg resultMsg = null;
        try {
            List<MetaData> dataList = metaDataService.selectBizType();
            if (dataList != null && dataList.size() > 0){
                resultMsg = ResultMsg.success(dataList);
            } else {
                resultMsg = ResultMsg.success();
            }
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("查询失败");
            resultMsg = ResultMsg.error(CodeMsg.ERROR);
        }
        return resultMsg;
    }

    @ResponseBody
    @RequestMapping(value = "/rule/meta/selectByBizType")
    public Object selectByBizType(@RequestParam("bizType") Integer bizType) {
        ResultMsg resultMsg = null;
        try {
            List<MetaData> dataList = metaDataService.selectAll();
            if (dataList != null && dataList.size() > 0){
                Iterator<MetaData> iterator = dataList.iterator();
                while (iterator.hasNext()){
                    MetaData metaData = iterator.next();
                    if (!metaData.getBizType().equals(bizType)) {
                        iterator.remove();
                    }
                }
                resultMsg = ResultMsg.success(dataList);
            } else {
                resultMsg = ResultMsg.success();
            }
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("查询失败");
            resultMsg = ResultMsg.error(CodeMsg.ERROR);
        }
        return resultMsg;
    }
}
