package com.xkb.web3j.service;

import org.springframework.stereotype.Service;

import java.math.BigInteger;

public interface LatestBlockNumberService {

    void saveLatest(BigInteger latestBlockNumber);

    BigInteger getLast();
}
