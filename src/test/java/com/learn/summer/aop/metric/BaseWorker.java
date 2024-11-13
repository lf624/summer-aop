package com.learn.summer.aop.metric;

import com.learn.summer.annotation.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

@Metric("metricInvocationHandler")
public class BaseWorker {

    @Metric("MD5")
    public String md5(String input) {
        return hash("MD5", input);
    }

    String hash(String metric, String input) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(metric);
        }catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(input.getBytes(StandardCharsets.UTF_8));
        byte[] res = md.digest();
        return "0x" + HexFormat.of().formatHex(res);
    }
}
