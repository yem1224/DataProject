package com.finda.server.mydata.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;

/**
 * @since 2021. 06. 10
 * 인증 시 nonce 발생
 */
@RequiredArgsConstructor
@Component
public class NonceGenerator {

    /**
     *  uuid byte 변환
     * @param uuid
     * @return bb.array()
     */
    public static byte[] asBytes(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }

    /**
     * Nonce 발생
     * @return nonce
     */
    public String apply(){
        UUID uuid = UUID.randomUUID();
        byte[] uBytes = asBytes(uuid);
        String nonce = Base64.getUrlEncoder().withoutPadding().encodeToString(uBytes);
        return nonce;
    }
}
