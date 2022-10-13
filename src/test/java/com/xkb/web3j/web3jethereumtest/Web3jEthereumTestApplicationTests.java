package com.xkb.web3j.web3jethereumtest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

@SpringBootTest
class Web3jEthereumTestApplicationTests {

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
}
