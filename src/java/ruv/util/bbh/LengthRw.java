/*
 * Copyright © 2013-2016 The Ruv Core Developers.
 * Copyright © 2016-2019 Jelurida IP B.V.
 *
 * See the LICENSE.txt file at the top-level directory of this distribution
 * for licensing information.
 *
 * Unless otherwise agreed in a custom licensing agreement with Jelurida B.V.,
 * no part of this software, including this file, may be copied, modified,
 * propagated, or distributed except according to the terms contained in the
 * LICENSE.txt file.
 *
 * Removal or modification of this copyright notice is prohibited.
 *
 */

package ruv.util.bbh;

import ruv.RuvException;
import ruv.util.Convert;

import java.nio.ByteBuffer;

public interface LengthRw {
    int getSize();

    int readFromBuffer(ByteBuffer buffer) throws RuvException.NotValidException;

    void writeToBuffer(ByteBuffer buffer, int length);

    boolean validate(int length);

    static StringLengthRw maxStringLen(int maxCharLength) {
        LengthRw lengthRw = LengthRwPrimitiveType.getByMaxLength(Convert.getMaxStringSize(maxCharLength));
        return new StringLengthRw(lengthRw, maxCharLength);
    }
}
