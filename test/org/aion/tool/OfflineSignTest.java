/*
 * Copyright (c) 2017-2018 Aion foundation.
 *
 * This file is part of the aion network project.
 *
 * The aion network project is free software: you can redistribute it
 * and/or modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation, either version 3 of
 * the License, or any later version.
 *
 * The aion network project is distributed in the hope that it will
 * be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the aion network project source files.
 * If not, see <https://www.gnu.org/licenses/>.
 *
 * Contributors to the aion source files in decreasing order of code volume:
 *
 * Aion foundation.
 *
 */
package org.aion.tool;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;

import java.security.interfaces.ECKey;
import org.aion.base.util.ByteUtil;
import org.aion.tool.KeyGenerator.KeyPair;
import org.aion.tool.OfflineSignTx.SignedTx;
import org.junit.Test;

public class OfflineSignTest {

    @Test
    public void testSignedTx() throws Exception {
        KeyPair kp = KeyGenerator.genKey();
        KeyPair kp2 = KeyGenerator.genKey();
        System.out.println("pub key1:" + kp.getPubKey());
        System.out.println("sec key1:" + kp.getSecKey());
        System.out.println("address1:" + kp.getAddr());

        System.out.println("pub key2:" + kp2.getPubKey());
        System.out.println("sec key2:" + kp2.getSecKey());
        System.out.println("address2:" + kp2.getAddr());

        String from = "a0ddf224c387720e65058e8434a6012006ebe0756609e29129c5d0b601dca15d";
        String sk = "ad03e484fa9db5d0cc6a21545ca2d08562adab480a5dbdeaddb8c2ac5615a34168055445065a7f33b0899670810fc849e61545b12427a2254f2d9cc09b9316b0";

        //Assign timestamp
        SignedTx stx = OfflineSignTx
            .signedTx(from, kp2.getAddr(), "1", "14", "", "21000", "10000000000",
                sk);

        System.out.println();
        System.out.println("SignedTx raw:" + stx.getRawTx());
        System.out.println("SignedTx hash:" + stx.getMsgHash());

        //no timestamp, given by the sign method
        stx = OfflineSignTx
            .signedTx(kp.getAddr(), kp2.getAddr(), "1", "0", "", "50000", "10000000000",
                kp.getSecKey());

        System.out.println();
        System.out.println("SignedTx raw:" + stx.getRawTx());
        System.out.println("SignedTx hash:" + stx.getMsgHash());

    }

    @Test
    public void rawTxWithoutSign() {

        KeyPair kp = KeyGenerator.genKey();
        System.out.println("pub key:" + kp.getPubKey());
        System.out.println("sec key:" + kp.getSecKey());
        System.out.println("address:" + kp.getAddr());

        String from = kp.getAddr();
        String to = "a0ddf224c387720e65058e8434a6012006ebe0756609e29129c5d0b601dca15e";

        byte[] res = OfflineSignTx.rawTxWithoutSign(from, to, "1", "14", "", 21000, 10000000000L);
        assertNotNull(res);

        System.out.println("unsigned rawdata: " + ByteUtil.toHexString(res));

        // sign transaction
        SignedTx stx = OfflineSignTx.signRawTx(res, kp.getSecKey(), 0);

        System.out.println();
        System.out.println("SignedTx raw:" + stx.getRawTx());
        System.out.println("SignedTx hash:" + stx.getMsgHash());

        AionTransaction tx = new AionTransaction(ByteUtil.hexStringToBytes(stx.getRawTx()));
        tx.rlpParse();
        assertArrayEquals(tx.getEncoded(), ByteUtil.hexStringToBytes(stx.getRawTx()));
    }
}
