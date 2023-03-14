package com.szj.djk.controller;
import com.szj.djk.common.R;
import com.szj.djk.entity.OneCastroll;
import com.szj.djk.entity.RollingMachine;
import com.szj.djk.entity.WarnTable;
import com.szj.djk.service.OneCastrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

/**
 * @ClassName OneCastrollController
 * @Author 张高义
 * @Create 2022/10/21 0021 上午 11:03
 */
@RestController
@RequestMapping("/oneCastroll")
public class OneCastrollController {
    @Autowired
    private  OneCastrollService oneCastrollService;

    /**
     * 查询铸轧机最新数据
     */
    @GetMapping("/listNewData")
    public R<List<OneCastroll>> ListNewData(){
        List<OneCastroll> rollingMachines = oneCastrollService.selectRollingNewData();
//        System.out.println("这是铸轧机最新数据"+rollingMachines);
        return  R.success(rollingMachines);
    }
    /**
     * 查询铸轧机特定时间前后的警告数据
     */
    @GetMapping("listSpecial")
    public R<List<WarnTable>> listSpecial(OneCastroll oneCastroll, String rollingName)  {
        int amount = 10000;
        LocalDateTime createTime = oneCastroll.getCreateTime();
        long beforeTime = Timestamp.valueOf(createTime).getTime()-amount;
        long afterTime = Timestamp.valueOf(createTime).getTime()+amount;
        LocalDateTime before = new Date(beforeTime).toInstant().atOffset(ZoneOffset.of("+8")).toLocalDateTime();
        LocalDateTime after = new Date(afterTime).toInstant().atOffset(ZoneOffset.of("+8")).toLocalDateTime();
        List<WarnTable> specialList = oneCastrollService.selectSpecial(before,after,rollingName);

        return R.success(specialList);
    }

}