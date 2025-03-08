package me.zhengjie.modules.iptv.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import me.zhengjie.base.BaseDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Setter
@Getter
public class OrderDto extends BaseDTO implements Serializable {

    @ApiModelProperty(value = "订单号", required = true)
    private String orderNo;
    @ApiModelProperty(value = "商户号", required = true)
    private String merchant;
    @ApiModelProperty(value = "业务编码", required = true)
    private String businessCode;

    @ApiModelProperty(value = "wahtsapp", required = true)
    @NotBlank
    @NotNull
    private String whatsapp;
    @ApiModelProperty(value = "名字", required = true)
    private String name;
    @ApiModelProperty(value = "手机号", required = true)
    private String phone;
    @ApiModelProperty(value = "邮箱", required = true)
    private String email;
    @ApiModelProperty(value = "下单金额", required = true)
    private String amount;
    @ApiModelProperty(value = "通知地址", required = true)
    private String notifyUrl;
    @ApiModelProperty(value = "回跳地址", required = true)
    private String pageUrl;
    @ApiModelProperty(value = "银行编码", required = true)
    private String bankCode;
    @ApiModelProperty(value = "备注", required = true)
    private String subject;
    @ApiModelProperty(value = "签名", required = true)
    private String sign;
}