package me.zhengjie.modules.iptv.service;

import me.zhengjie.modules.iptv.service.dto.OrderDto;
import me.zhengjie.modules.iptv.service.dto.PayNotifyDto;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public interface OrderService {
    /**
     * 下单接口
     *
     * @param orderDTO
     */
    Object createOrder(OrderDto orderDTO) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    /**
     * 支付回调接口
     *
     * @param payNotifyDto
     * @return
     */
    String notify(PayNotifyDto payNotifyDto) throws UnsupportedEncodingException, NoSuchAlgorithmException;
}
