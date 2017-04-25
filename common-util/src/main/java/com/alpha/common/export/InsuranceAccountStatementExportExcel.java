package com.alpha.common.export;

import com.alpha.common.annotation.ColumnNum;
import com.alpha.common.annotation.RowNum;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by weilai on 17/2/21.
 */
@Data
public class InsuranceAccountStatementExportExcel implements Serializable {

    @RowNum
    private int rowNum;

    /**
     * 出单时间
     */
    @ColumnNum(value = 1,title = "出单时间")
    private Date generateOrderTime;

    /**
     * 订单编号
     */
    @ColumnNum(value = 2,title = "订单编号")
    private String orderNo;

    /**
     * 车牌号
     */
    @ColumnNum(value = 3,title = "车牌号")
    private String licenseNo;

    /**
     * 被保险人姓名
     */
    @ColumnNum(value = 4,title = "被保险人姓名")
    private String insuredName;

    /**
     * 承保公司
     */
    @ColumnNum(value = 5,title = "承保公司")
    private String insuranceCompanyName;

    /**
     * 保险经纪公司
     */
    @ColumnNum(value = 6,title = "保险经纪公司")
    private String insuranceBrokerCompanyName;

    /**
     * 保费
     */
    @ColumnNum(value = 7,title = "保费")
    private BigDecimal insuranceFee;

    /**
     * 交强险费率
     */
    @ColumnNum(value = 8,title = "交强险费率")
    private BigDecimal forceInsuranceRate;

    /**
     * 交强险保单号
     */
    @ColumnNum(value = 9,title = "交强险保单号")
    private String forceInsuranceNo;

    /**
     * 商业险费率
     */
    @ColumnNum(value = 10,title = "商业险费率")
    private BigDecimal businessInsuranceRate;

    /**
     * 商业险保单号
     */
    @ColumnNum(value = 11,title = "商业险保单号")
    private String businessInsuranceNo;

    /**
     * 通道费
     */
    @ColumnNum(value = 12,title = "通道费")
    private BigDecimal crossFee;

    /**
     * 总佣金
     */
    @ColumnNum(value = 13,title = "平台所得佣金")
    private BigDecimal platformCommission;

    /**
     * 结算状态
     */
    @ColumnNum(value = 14,title = "结算状态")
    private String status;



}
