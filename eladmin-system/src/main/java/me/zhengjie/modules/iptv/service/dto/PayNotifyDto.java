package me.zhengjie.modules.iptv.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import me.zhengjie.base.BaseDTO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Setter
@Getter
public class PayNotifyDto extends BaseDTO implements Serializable {

    @ApiModelProperty(value = "下单金额", required = true)
    private String amount;
    @ApiModelProperty(value = "实际支付金额", required = true)
    private BigDecimal payAmount;
    @ApiModelProperty(value = "业务编码", required = true)
    private String businessCode;
    @ApiModelProperty(value = "支付订单号", required = true)
    private String orderNo;
    @ApiModelProperty(value = "签名", required = true)
    private String sign;
    @ApiModelProperty(value = "商户编号", required = true)
    private String merchant;
    @ApiModelProperty(value = "状态", required = true)
    private String status;
    @ApiModelProperty(value = "成功原因/失败原因", required = true)
    private String message;
    @ApiModelProperty(value = "商户订单号", required = true)
    private String merchantOrderNo;
    @ApiModelProperty(value = "代收时间： yyyy-MM-dd HH:mm:ss", hidden = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;
    @ApiModelProperty(value = "手续费", hidden = true)
    private BigDecimal fee; // 手续费
}