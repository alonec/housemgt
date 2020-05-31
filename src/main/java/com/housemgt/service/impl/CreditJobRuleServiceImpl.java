package com.housemgt.service.impl;

import com.housemgt.mapper.CreditJobRuleMapper;
import com.housemgt.model.CreditJobRule;
import com.housemgt.service.AreaRuleService;
import com.housemgt.service.CreditJobRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditJobRuleServiceImpl implements CreditJobRuleService {

    @Autowired
    private CreditJobRuleMapper creditJobRuleMapper;

    @Override
    public int insertSelective(CreditJobRule record) {
        if (record == null){
            return 0;
        }
        return creditJobRuleMapper.insertSelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer areaRuleId) {
        if (areaRuleId < 1){
            return 0;
        }
        return creditJobRuleMapper.deleteByPrimaryKey(areaRuleId);
    }

    @Override
    public int updateByPrimaryKeySelective(CreditJobRule record) {
        if (record == null){
            return 0;
        }
        return creditJobRuleMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<CreditJobRule> selectBySerealId(Integer serealId) {
        return creditJobRuleMapper.selectBySerealId(serealId);
    }
}
