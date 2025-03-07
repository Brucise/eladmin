package me.zhengjie.modules.pay.rest;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.annotation.Log;
import me.zhengjie.annotation.rest.AnonymousPostMapping;
import me.zhengjie.modules.pay.service.FunPayService;
import me.zhengjie.modules.pay.service.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/iptv")
@RequiredArgsConstructor
@Api(tags = "iptv：订单接口")
public class PayController {

    @Autowired
    private FunPayService funPayService;

    @ApiOperation("用户下单")
    @AnonymousPostMapping(value = "/order")
    public ResponseEntity<Object> createOrder(@Validated @RequestBody OrderDto orderDto) throws Exception {
        return new ResponseEntity<>(funPayService.createOrder(orderDto), HttpStatus.OK);
    }
}