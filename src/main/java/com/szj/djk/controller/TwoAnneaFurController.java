package com.szj.djk.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.szj.djk.common.R;
import com.szj.djk.entity.*;
import com.szj.djk.mapper.WarnTableMapper;
import com.szj.djk.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

@Component
@RestController
@RequestMapping("/anneaf2")
public class TwoAnneaFurController {
    @Autowired
    TwoAnneaFurService twoAnneaFurService;
    @Autowired
    private AvaluateService avaluateService;
    @Autowired
    private WarnTableMapper warnTableMapper;
    @Autowired
    private WarnTableService warnTableService;


    @GetMapping("/list")
    public R<List<TwoAnneaFur>> list(TwoAnneaFur twoAnneaFur){
        LambdaQueryWrapper<TwoAnneaFur> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.setEntity(twoAnneaFur)
                .orderByDesc(true,TwoAnneaFur::getTs)
                .last("limit 20");
        List<TwoAnneaFur> list = twoAnneaFurService.list(queryWrapper);
        return R.success(list);
    }

    /**
     * 实时存入退火炉1的异常数据
     */
//    @GetMapping("listone")
    @Scheduled(fixedRate = 20000)
    public R<ValueRange> addwarndata(){
        Avaluate avaluate = new Avaluate();
        ValueRange valueRange = new ValueRange();
        LambdaQueryWrapper<Avaluate> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.setEntity(avaluate);
        avaluateService.list(queryWrapper).forEach(i ->{
            switch (i.getName()){
                case "炉冷却水":
                    valueRange.setShangDD(i.getMaxValue());
                    break;
                case "炉压缩空气":
                    valueRange.setXiaDD(i.getMaxValue());
                    break;
                case "金属料温温度曲线":
                    valueRange.setShangDS(i.getMaxValue());
                    break;
                case "一区炉气温度曲线":
                    valueRange.setXiaDS(i.getMaxValue());
                    break;
                case "二区炉气温度曲线":
                    valueRange.setZhuDD(i.getMaxValue());
                    break;
                case "三区炉气温度曲线":
                    valueRange.setBeiDD(i.getMaxValue());
                    break;
                case "炉气设定温度":
                    valueRange.setBeiDS(i.getMaxValue());
                    break;
            }
        });
//        System.out.println("打印++++" + valueRange.getXiaDD());
        WarnTable warnTable = new WarnTable();
        TwoAnneaFur twoAnneaFur = new TwoAnneaFur();
        LambdaQueryWrapper<TwoAnneaFur> queryWrapperR = new LambdaQueryWrapper<>();
                queryWrapperR.setEntity(twoAnneaFur)
                .orderByDesc(true,TwoAnneaFur::getTs)
                .last("limit 20");
                List<TwoAnneaFur> newlist = twoAnneaFurService.list(queryWrapperR);
//            System.out.println("新的数据"+ newlist);
        /**
         * 炉冷却水:ShangDD  炉压缩空气:XiaDD    金属料温温度曲线:ShangDS 一区炉气温度曲线:XiaDS
         * 二区炉气温度曲线:ZhuDD   三区炉气温度曲线:BeiDD  炉气设定温度:BeiDS
         */
        newlist.forEach(i->{
            if(i.getCoolWaterUpLimit()>valueRange.getShangDD()){
                warnTable.setRollingName("炉冷却水");
                warnTable.setRollingValue(i.getCoolWaterUpLimit());
                warnTable.setRollingProduceTime(i.getTs());
                warnTable.setRollingDeviceNumber("退火炉2#");
                warnTableService.save(warnTable);
            }
//            if(i.getCompressedAirOneLowPressure()>valueRange.getXiaDD()){
//                warnTable.setRollingName("炉压缩空气");
//                warnTable.setRollingValue(i.getCompressedAirOneLowPressure());
//                warnTable.setRollingProduceTime(i.getTs());
//                warnTable.setRollingDeviceNumber("退火炉1#");
//                warnTableService.save(warnTable);
//            }
            if(i.getMeterialT()>valueRange.getShangDS()){
                warnTable.setRollingName("金属料温温度曲线");
                warnTable.setRollingValue(i.getMeterialT());
                warnTable.setRollingProduceTime(i.getTs());
                warnTable.setRollingDeviceNumber("退火炉2#");
                warnTableService.save(warnTable);
            }

            if(i.getZoneOneT()>valueRange.getXiaDS()){
                warnTable.setRollingName("一区炉气温度曲线");
                warnTable.setRollingValue(i.getZoneOneT());
                warnTable.setRollingProduceTime(i.getTs());
                warnTable.setRollingDeviceNumber("退火炉2#");
                warnTableService.save(warnTable);
            }

            if(i.getZoneTwoT()>valueRange.getZhuDD()){
                warnTable.setRollingName("二区炉气温度曲线");
                warnTable.setRollingValue(i.getZoneTwoT());
                warnTable.setRollingProduceTime(i.getTs());
                warnTable.setRollingDeviceNumber("退火炉2#");
                warnTableService.save(warnTable);
            }

            if(i.getZoneThreeT()>valueRange.getBeiDD()){
                warnTable.setRollingName("三区炉气温度曲线");
                warnTable.setRollingValue(i.getZoneThreeT());
                warnTable.setRollingProduceTime(i.getTs());
                warnTable.setRollingDeviceNumber("退火炉2#");
                warnTableService.save(warnTable);
            }
                    if(i.getSetT()>valueRange.getBeiDD()){
                        warnTable.setRollingName("炉气设定温度");
                        warnTable.setRollingValue(i.getSetT());
                        warnTable.setRollingProduceTime(i.getTs());
                        warnTable.setRollingDeviceNumber("退火炉2#");
                        warnTableService.save(warnTable);
                    }
        });
        return  R.success(valueRange);
    }


    /**
     * 查询重卷机特定时间前后的警告数据
     */
    @GetMapping("listSpecial")
    public R<List<WarnTable>> listSpecial(TwoAnneaFur twoAnneaFur, String rollingName)  {
//        System.out.println("触发了+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        int amount = 10000;
        LocalDateTime ts = twoAnneaFur.getTs();
        long beforeTime = Timestamp.valueOf(ts).getTime()-amount;
        long afterTime = Timestamp.valueOf(ts).getTime()+amount;
        LocalDateTime before = new Date(beforeTime).toInstant().atOffset(ZoneOffset.of("+8")).toLocalDateTime();
        LocalDateTime after = new Date(afterTime).toInstant().atOffset(ZoneOffset.of("+8")).toLocalDateTime();
        List<WarnTable> specialList = twoAnneaFurService.selectSpecial(before,after,rollingName);
        return R.success(specialList);
    }

}
