package com.szj.djk.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.szj.djk.common.R;
import com.szj.djk.entity.RollingMachine;
import com.szj.djk.entity.WarnTable;
import com.szj.djk.service.WarnTableService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @ClassName WarnTableController
 * @Author 张高义
 * @Create 2023/3/2 0002 下午 14:40
 */
@RestController
@RequestMapping("/warnTable")
public class WarnTableController {
    @Autowired
    private WarnTableService warnTableService;
    @GetMapping("/listWarnNewData")
    public R<List<WarnTable>> listWarnNewData(@Param("rollingDeviceNumber") String rollingDeviceNumber){
        List<WarnTable> newWarnData = warnTableService.selectWarnTableNewData(rollingDeviceNumber);
        return R.success(newWarnData);
    }

    @GetMapping("/listWarnHistoryData")
    public R<List<WarnTable>> listWarnHistoryData(@Param("rollingDeviceNumber"+"rollingName") String rollingDeviceNumber){

        List<WarnTable> historyWarnData = warnTableService.selectWarnTableHistoryData(rollingDeviceNumber);
        return R.success(historyWarnData);
    }
    @GetMapping("/listDuringWarnData")
    public R<List<WarnTable>> listDuringWarnData(WarnTable warnTable,String begin,String end) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginDate = sdf.parse(begin);
        Date endDate = sdf.parse(end);
        List<WarnTable> DuringDataList = warnTableService.selectDuringWarnData(warnTable,beginDate,endDate);
        return R.success(DuringDataList);
    }
}
