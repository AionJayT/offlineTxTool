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

        SignedTx stx = OfflineSignTx
            .signedTx(kp.getAddr(), kp2.getAddr(), "1", "0", "", "50000", "10000000000",
                kp.getSecKey());

        System.out.println();
        System.out.println("SignedTx raw:" + stx.getRawTx());
        System.out.println("SignedTx hash:" + stx.getMsgHash());

    }
}
