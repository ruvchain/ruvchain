/*
 * Copyright © 2013-2016 The Ruv Core Developers.
 * Copyright © 2016-2019 Jelurida IP B.V.
 *
 * See the LICENSE.txt file at the top-level directory of this distribution
 * for licensing information.
 *
 * Unless otherwise agreed in a custom licensing agreement with Jelurida B.V.,
 * no part of the Ruv software, including this file, may be copied, modified,
 * propagated, or distributed except according to the terms contained in the
 * LICENSE.txt file.
 *
 * Removal or modification of this copyright notice is prohibited.
 *
 */

package ruv.http;

import ruv.peer.Hallmark;
import org.json.simple.JSONStreamAware;

import javax.servlet.http.HttpServletRequest;

import static ruv.http.JSONResponses.INCORRECT_HALLMARK;
import static ruv.http.JSONResponses.MISSING_HALLMARK;

public final class DecodeHallmark extends APIServlet.APIRequestHandler {

    static final DecodeHallmark instance = new DecodeHallmark();

    private DecodeHallmark() {
        super(new APITag[] {APITag.TOKENS}, "hallmark");
    }

    @Override
    protected JSONStreamAware processRequest(HttpServletRequest req) {

        String hallmarkValue = req.getParameter("hallmark");
        if (hallmarkValue == null) {
            return MISSING_HALLMARK;
        }

        try {

            Hallmark hallmark = Hallmark.parseHallmark(hallmarkValue);

            return JSONData.hallmark(hallmark);

        } catch (RuntimeException e) {
            return INCORRECT_HALLMARK;
        }
    }

    @Override
    protected boolean allowRequiredBlockParameters() {
        return false;
    }

}
