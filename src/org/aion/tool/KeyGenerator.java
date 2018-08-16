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

import javax.annotation.Nonnull;
import org.aion.base.util.ByteUtil;
import org.aion.crypto.ECKey;
import org.aion.crypto.ECKeyFac;

public class KeyGenerator {

    public static KeyPair genKey() {
        ECKey key = ECKeyFac.inst().create();
        if (key == null) {
            throw new NullPointerException();
        }

        return new KeyPair(ByteUtil.toHexString(key.getPubKey()),
            ByteUtil.toHexString(key.getPrivKeyBytes()), ByteUtil.toHexString(key.getAddress()));
    }

    public static class KeyPair {

        private String pubKey;
        private String secKey;
        private String address;

        KeyPair(@Nonnull final String pk, @Nonnull final String sk, final String addr) {
            pubKey = pk;
            secKey = sk;
            address = addr;
        }

        public String getPubKey() {
            return pubKey;
        }

        public String getSecKey() {
            return secKey;
        }

        public String getAddr() {
            return address;
        }

    }
}
