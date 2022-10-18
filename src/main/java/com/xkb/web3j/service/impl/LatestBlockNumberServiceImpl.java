package com.xkb.web3j.service.impl;

import com.xkb.web3j.service.LatestBlockNumberService;
import com.xkb.web3j.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class LatestBlockNumberServiceImpl implements LatestBlockNumberService {

    @Autowired
    private RedisService redisService;

    @Value("${redis.key.prefix.authCode}")
    private String REDIS_KEY_PREFIX_AUTH_CODE;

    private final String REDIS_KEY = REDIS_KEY_PREFIX_AUTH_CODE + "latestBlockNumber";

    @Override
    public void saveLatest(BigInteger latestBlockNumber) {
        redisService.set(REDIS_KEY, latestBlockNumber.toString(10));
    }

    @Override
    public BigInteger getLast() {
        return new BigInteger(redisService.get(REDIS_KEY));
    }
}
