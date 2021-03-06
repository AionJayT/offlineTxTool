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

import java.math.BigInteger;
import javax.annotation.Nonnull;
import org.aion.base.type.Address;
import org.aion.base.util.ByteUtil;
import org.aion.crypto.ECKey;
import org.aion.crypto.ECKeyFac;

public class OfflineSignTx {
    public static class SignedTx {
        private String rawTx;
        private String msgHash;
        public SignedTx(@Nonnull final String _rawTx, @Nonnull final String _msgHash) {
            rawTx = _rawTx;
            msgHash = _msgHash;
        }

        public String getMsgHash() {
            return msgHash;
        }

        public String getRawTx() {
            return rawTx;
        }
    }

    public static SignedTx signedTx(
        @Nonnull final String from,
        @Nonnull final String to,
        @Nonnull final String value,
        @Nonnull final String nonce,
        @Nonnull final String data,
        @Nonnull final String nrg,
        @Nonnull final String nrgPrice,
        @Nonnull final String secKey) throws Exception {
        try {
            return signTx(from, to, new BigInteger(value), new BigInteger(nonce), data,
                Long.valueOf(nrg), Long.valueOf(nrgPrice), secKey);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public static SignedTx signedTx(
        @Nonnull final String from,
        @Nonnull final String to,
        @Nonnull final String value,
        @Nonnull final String nonce,
        @Nonnull final String data,
        @Nonnull final String nrg,
        @Nonnull final String nrgPrice,
        @Nonnull final String secKey,
        @Nonnull final String timeStamp
        ) throws Exception {
        try {
            return signTx(from, to, new BigInteger(value), new BigInteger(nonce), data,
                Long.valueOf(nrg), Long.valueOf(nrgPrice), secKey, Long.valueOf(timeStamp));
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public static SignedTx signTx(
        @Nonnull final String from,
        @Nonnull final String to,
        @Nonnull final BigInteger value,
        @Nonnull final BigInteger nonce,
        @Nonnull final String data,
        final long nrg,
        final long nrgPrice,
        @Nonnull final String secKey) {
        return signTx(from, to, value, nonce, data, nrg, nrgPrice, secKey, 0);
    }
    public static SignedTx signTx(
        @Nonnull final String from,
        @Nonnull final String to,
        @Nonnull final BigInteger value,
        @Nonnull final BigInteger nonce,
        @Nonnull final String data,
        final long nrg,
        final long nrgPrice,
        @Nonnull final String secKey,
        final long timeStamp
        ) {
        ByteUtil.hexStringToBytes(data);

        AionTransaction tx = new AionTransaction(nonce.toByteArray(), Address.wrap(from), Address.wrap(to), value.toByteArray(), ByteUtil.hexStringToBytes(data), nrg, nrgPrice);

        return getSignedTx(secKey, timeStamp, tx);
    }

    private static SignedTx getSignedTx(@Nonnull final String secKey, final long timeStamp,
        AionTransaction tx) {
        ECKey key = ECKeyFac.inst().fromPrivate(ByteUtil.hexStringToBytes(secKey));
        if (key == null) {
            throw new NullPointerException("can't gen the eckey by the input seckey");
        }

        if (timeStamp == 0) {
            tx.sign(key);
        } else {
            tx.sign(key, timeStamp);
        }

        String raw = ByteUtil.toHexString(tx.getEncoded());
        String msgHash = ByteUtil.toHexString(tx.getHash());

        return new SignedTx(raw, msgHash);
    }

    public static SignedTx signRawTx(@Nonnull final byte[] rawTx, @Nonnull final String secKey, final long timeStamp) {

        ECKey key = ECKeyFac.inst().fromPrivate(ByteUtil.hexStringToBytes(secKey));
        if (key == null) {
            throw new NullPointerException("can't gen the eckey by the input seckey");
        }

        AionTransaction tx = new AionTransaction(null);
        tx.setEncodedRaw(rawTx);
        tx.setTimeStamp(timeStamp);
        return getSignedTx(secKey, timeStamp, tx);
    }

    public static byte[] rawTxWithoutSign(
        @Nonnull final String from,
        @Nonnull final String to,
        @Nonnull final String value,
        @Nonnull final String nonce,
        @Nonnull final String data,
        final long nrg,
        final long nrgPrice
    ) {
        ByteUtil.hexStringToBytes(data);
        AionTransaction tx = new AionTransaction(new BigInteger(nonce).toByteArray(), Address.wrap(from), Address.wrap(to), new BigInteger(value).toByteArray(), ByteUtil.hexStringToBytes(data), nrg, nrgPrice);

        return tx.getEncodedRaw();
    }
}
