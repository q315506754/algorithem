package com.jiangli.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 *

 https://www.jianshu.com/p/d1644e281250

 */
public class JWTTest {
    /**
     * APP登录Token的生成和解析
     * 
     */

    /** token秘钥，请勿泄露，请勿随便修改 backups:JKKLJOoasdlfj */
    public static final String SECRET = "JKKLJOoasdlfj";
    /** token 过期时间: 10天 */
    public static final int calendarField = Calendar.DATE;
    public static final int calendarInterval = 10;

    /**
     * JWT生成Token.<br/>
     * 
     * JWT构成: header, payload, signature
     * 
     * @param user_id
     *            登录成功后用户user_id, 参数user_id不可传空
     */
    public static String createToken(Long user_id) throws Exception {
        Date iatDate = new Date();
        // expire time
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(calendarField, calendarInterval);
        Date expiresDate = nowTime.getTime();

        // header Map
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        // build token
        // param backups {iss:Service, aud:APP}
        String token = JWT.create()
                .withHeader(map) // header

                .withClaim("iss", "Service") // payload
                .withClaim("aud", "APP")
                .withClaim("user_id", null == user_id ? null : user_id.toString())
                .withIssuedAt(iatDate) // sign time
                .withExpiresAt(expiresDate) // expire time

                .sign(Algorithm.HMAC256(SECRET)); // signature

        return token;
    }

    /**
     * 解密Token
     * 
     * @param token
     * @return
     * @throws Exception
     */
    public static Map<String, Claim> verifyToken(String token) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
             e.printStackTrace();
            // token 校验失败, 抛出Token验证非法异常
        }
        return jwt.getClaims();
    }

    /**
     * 根据Token获取user_id
     * 
     * @param token
     * @return user_id
     */
    public static Long getAppUID(String token) {
        Map<String, Claim> claims = verifyToken(token);
        Claim user_id_claim = claims.get("user_id");
        if (null == user_id_claim || StringUtils.isEmpty(user_id_claim.asString())) {
            // token 校验失败, 抛出Token验证非法异常
        }
        return Long.valueOf(user_id_claim.asString());
    }

    public static void main(String[] args) throws Exception {
        String token = createToken(123456L);
        System.out.println(token);
        System.out.println(new String(Base64.decodeBase64("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")));
        System.out.println(new String(Base64.decodeBase64("eyJhdWQiOiJBUFAiLCJ1c2VyX2lkIjoiMTIzNDU2IiwiaXNzIjoiU2VydmljZSIsImV4cCI6MTU5MDAzOTAzNiwiaWF0IjoxNTg5MTc1MDM2fQ")));
        System.out.println(new String(Base64.decodeBase64("P511izFvKaoSjfd_-SXcpzFiR2Y94RX6ciBL0tkt1Gc")));

        Map<String, Claim> stringClaimMap = verifyToken(token);
        //Map<String, Claim> stringClaimMap = verifyToken(token+"2");
        System.out.println(stringClaimMap);
        for (Map.Entry<String, Claim> stringClaimEntry : stringClaimMap.entrySet()) {
            System.out.println(stringClaimEntry.getKey()+"->"+stringClaimEntry.getValue().asString());
        }
    }
}