package com.xkb.web3j.web3jethereumtest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xkb.web3j.entity.CustomBlock;
import com.xkb.web3j.mapper.CustomBlockMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;

@SpringBootTest
class Web3jEthereumTestApplicationTests {

    @Autowired
    private CustomBlockMapper customBlockMapper;

    @Test
    void contextLoads() {
    }

    @Test
    void bigIntegerTest() {

        //     customTransaction.setValue(new BigDecimal(txInfo.getValue())
        //                 .divide(new BigDecimal(10).pow(18), 18, RoundingMode.DOWN));

        BigInteger bigIntValue = new BigInteger(String.valueOf(2000000000000000000L));
        System.out.println("BigInteger value: " + bigIntValue);

        BigDecimal bigDecValue = new BigDecimal(bigIntValue);
        System.out.println("BigDecimal value: " + bigDecValue);

        BigDecimal divideRes = bigDecValue.divide(new BigDecimal(10).pow(18), 18, RoundingMode.DOWN);
        System.out.println("divide result: " + divideRes);
    }

    @Test
    void SqlTest() {
        QueryWrapper<CustomBlock> wrapper = new QueryWrapper<>();
        wrapper.eq("number", 15767640);
        List<CustomBlock> customBlockList = customBlockMapper.selectList(wrapper);
        System.out.println("1");
    }
}
