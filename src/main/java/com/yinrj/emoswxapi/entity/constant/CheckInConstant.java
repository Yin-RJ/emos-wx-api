package com.yinrj.emoswxapi.entity.constant;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author yinrongjie
 * @version 1.0
 * @date 2022/4/7
 * @description 签到相关的常量类
 */
@Data
@Component
public class CheckInConstant {
    /**
     * 开始签到时间
     */
    public String attendanceStartTime;
    /**
     * 上班时间
     */
    public String attendanceTime;
    /**
     * 结束签到时间
     */
    public String attendanceEndTime;
    /**
     * 开始下班打卡时间
     */
    public String closingStartTime;
    /**
     * 下班时间
     */
    public String closingTime;
    /**
     * 结束下班卡时间
     */
    public String closingEndTime;
}
