package com.szj.djk.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 重卷机对象 rewinder
 * @author 张高义
 * @date 2022-09-25
 */
@Data
public class Rewinder {

    private static final long serialVersionUID = 1L;

    /** id */
    private Long rewinderId;

    /** 指标名称 */

    private String indicatorName;

    /** 数值 */
    private String value;

    /** 最小数值 */
    private String minValue;

    /** 最大数值 */
    private String maxValue;

    /** 数值产生时间 */
    @JsonFormat(pattern = "yyyy-MM-dd :hh:mm:ss")
    private Date produceTime;

}