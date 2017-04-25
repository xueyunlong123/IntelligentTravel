package com.alpha.common.export;

import com.alpha.common.annotation.ColumnNum;
import com.alpha.common.annotation.RowNum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by weilai on 17/2/28.
 */

@Data
public class PlatformStatementListForChannelExportExcel {
    @RowNum
    private int rowNum;
    /**
     * 出单时间
     */
    @ColumnNum(value = 1,title = "出单时间")
    private Date generateOrderTime;

    /**
     * 订单号
     */
    @ColumnNum(value = 2,title = "订单编号")
    private String orderNo;

    /**
     * 渠道编号
     */
    @ColumnNum(value = 3,title = "渠道编号")
    private Long channelId;

    /**
     * 渠道名称
     */
    @ColumnNum(value = 4,title = "渠道名称")
    private String channelName;

    /**
     * 代理人姓名
     */
    @ColumnNum(value = 5,title = "代理人姓名")
    private String agentName;
    /**
     * 车牌号
     */
    @ColumnNum(value = 6,title = "车牌号")
    private String licenseNo;

    /**
     * 被保险人姓名
     */
    @ColumnNum(value = 7,title = "被保险人姓名")
    private String insuredName;

    /**
     * 承保公司
     */
    @ColumnNum(value = 8,title = "承保公司")
    private String insuranceCompanyName;

    /**
     * 支付方式
     */
    @ColumnNum(value = 9,title = "支付方式")
    private String payType;

    /**
     * 保费
     */
    @ColumnNum(value = 10,title = "保费")
    private BigDecimal insuranceFee;

    /**
     * 交强险费率
     */
    @ColumnNum(value = 11,title = "交强险费率")
    private BigDecimal forceInsuranceRate;

    /**
     * 商业险费率
     */
    @ColumnNum(value = 12,title = "商业险费率")
    private BigDecimal businessInsuranceRate;

    /**
     * 平台服务费
     */
    @ColumnNum(value = 13,title = "平台佣金")
    private BigDecimal platformCommission;

    /**
     * 渠道获得佣金
     */
    @ColumnNum(value = 14,title = "渠道应得佣金")
    private BigDecimal channelCommission;

    /**
     * 结算状态
     */
    @ColumnNum(value = 15,title = "结算状态")
    private String status;

}
