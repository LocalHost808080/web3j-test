package com.xkb.web3j.web3jethereumtest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xkb.web3j.entity.CustomBlock;
import com.xkb.web3j.mapper.CustomBlockMapper;
import org.bouncycastle.util.encoders.Hex;
import org.junit.jupiter.api.Test;
import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.web3j.abi.TypeEncoder;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.contracts.token.ERC20BasicInterface;
import org.web3j.crypto.Hash;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.Web3Sha3;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class Web3jEthereumTestApplicationTests {

    @Autowired
    private Web3j web3j;

    @Autowired
    private CustomBlockMapper customBlockMapper;

    @Test
    void contextLoads() {}

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
    void sqlTest() {
        QueryWrapper<CustomBlock> wrapper = new QueryWrapper<>();
        wrapper.eq("number", 15767640);
        List<CustomBlock> customBlockList = customBlockMapper.selectList(wrapper);
        System.out.println("1");
    }

    @Test
    void sha3Test() {
        String string = "Transfer(address,address,uint256)";
        String h2 = "0x" + Hex.toHexString(string.getBytes(StandardCharsets.UTF_8));
        System.out.println("Hex: " + h2);

        String d5 = Hash.sha3(h2);
        System.out.println("Sha3" + d5);
    }

    @Test
    void supportsInterfaceTest() {
        // const is721 = await contract.methods.supportsInterface('0x80ac58cd').call();
        // if(is721) {
        //     return "ERC721";
        // }

    }

    @Test
    void pythonInterpreterTest() {

        PythonInterpreter interpreter = new PythonInterpreter();
        // interpreter.execfile("src/main/resources/python/eth_contract_service.py");
        interpreter.execfile("src/main/resources/python/plus.py");

        // 第一个参数为期望获得的函数（变量）的名字，第二个参数为期望返回的对象类型
        PyFunction pyFunction = interpreter.get("add", PyFunction.class);
        int a = 5, b = 10;

        // 调用函数，如果函数需要参数，在Java中必须先将参数转化为对应的“Python类型”
        PyObject pyobj = pyFunction.__call__(new PyInteger(a), new PyInteger(b));
        System.out.println("the anwser is: " + pyobj);
    }

    @Test
    void pythonCalling() {

        PythonInterpreter interpreter = new PythonInterpreter();
        // interpreter.execfile("src/main/resources/python/eth_contract_service.py");

        // 第一个参数为期望获得的函数（变量）的名字，第二个参数为期望返回的对象类型
        PyFunction pyFunction = interpreter.get("is_erc20_contract", PyFunction.class);
        String contractAddr = "0xdd125523d2139e8c1b3f6e4eccc8c2f488e132da";
        byte[] bytecode = contractAddr.getBytes(StandardCharsets.UTF_8);

        // 调用函数，如果函数需要参数，在Java中必须先将参数转化为对应的“Python类型”
        PyObject pyobj = pyFunction.__call__(new PyString(String.valueOf(bytecode)));
        System.out.println("the anwser is: " + pyobj);
    }
}
