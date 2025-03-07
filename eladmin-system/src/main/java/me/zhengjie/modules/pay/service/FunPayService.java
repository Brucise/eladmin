package me.zhengjie.modules.pay.service;

import me.zhengjie.modules.pay.service.dto.OrderDto;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public interface FunPayService {
    /**
     * 下单接口
     *
     * @param orderDTO
     */
    public Object createOrder(OrderDto orderDTO) throws UnsupportedEncodingException, NoSuchAlgorithmException;
}
