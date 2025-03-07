package me.zhengjie.modules.pay.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import me.zhengjie.base.BaseDTO;

import java.io.Serializable;

@Setter
@Getter
public class OrderDto extends BaseDTO implements Serializable {
    @ApiModelProperty(value = "商户号")
    private String merchant;
    @ApiModelProperty(value = "订单号")
    private Long orderNo;
    @ApiModelProperty(value = "业务编码")
    private String businessCode;
    @ApiModelProperty(value = "名字")
    private String name;
    @ApiModelProperty(value = "手机号")
    private String phone;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "下单金额")
    private String amount;
    @ApiModelProperty(value = "通知地址")
    private String notifyUrl;
    @ApiModelProperty(value = "回跳地址")
    private String pageUrl;
    @ApiModelProperty(value = "银行编码")
    private String bankCode;
    @ApiModelProperty(value = "备注")
    private String subject;
    @ApiModelProperty(value = "签名")
    private String sign;
}