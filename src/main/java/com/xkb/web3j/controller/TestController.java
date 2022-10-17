package com.xkb.web3j.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xkb.web3j.entity.CustomBlock;
import com.xkb.web3j.mapper.CustomBlockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private CustomBlockMapper customBlockMapper;

    @GetMapping("/sqlTest")
    public void sqlTest() {
        QueryWrapper<CustomBlock> wrapper = new QueryWrapper<>();
        wrapper.eq("number", 15767640);
        List<CustomBlock> customBlockList = customBlockMapper.selectList(wrapper);
        System.out.println(customBlockList);
    }
}
