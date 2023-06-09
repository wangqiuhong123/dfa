package com.szj.djk.controller;

import java.util.List;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.szj.djk.common.R;
import com.szj.djk.entity.LmdpQcColdReelReport;
import com.szj.djk.service.LmdpQcColdReelReportService;
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
//冷轧卷质检报告工序
@DS("slave")
@RestController
@RequestMapping("/lmdpQcColdReelReport")
public class LmdpQcColdReelReportController
{
    @Autowired
    private LmdpQcColdReelReportService lmdpQcColdReelReportService;

    @GetMapping("list")
    public R<List<LmdpQcColdReelReport>> list(LmdpQcColdReelReport lmdpQcColdReelReport){
        LambdaQueryWrapper<LmdpQcColdReelReport> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.setEntity(lmdpQcColdReelReport);
        List<LmdpQcColdReelReport> list = lmdpQcColdReelReportService.list(queryWrapper);
        return R.success(list);
    }

}
