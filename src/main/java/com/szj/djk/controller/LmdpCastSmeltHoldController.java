package com.szj.djk.controller;

import java.util.List;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.szj.djk.common.R;
import com.szj.djk.entity.LmdpCastSmeltHold;
import com.szj.djk.service.LmdpCastSmeltHoldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 【请填写功能名称】Controller
 *
 * @author ruoyi
 * @date 2022-10-17
 */
//熔炼工序
@DS("slave")
@RestController
@RequestMapping("/lmdpCastSmeltHold")
public class LmdpCastSmeltHoldController
{
    @Autowired
    private LmdpCastSmeltHoldService lmdpCastSmeltHoldService;

    @GetMapping("list")
    public R<List<LmdpCastSmeltHold>> list(LmdpCastSmeltHold lmdpCastSmeltHold){
        LambdaQueryWrapper<LmdpCastSmeltHold> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.setEntity(lmdpCastSmeltHold);
        List<LmdpCastSmeltHold> list = lmdpCastSmeltHoldService.list(queryWrapper);
        return R.success(list);
    }
}
