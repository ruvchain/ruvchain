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

import ruv.Currency;
import ruv.RuvException;
import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

import javax.servlet.http.HttpServletRequest;

public final class CanDeleteCurrency extends APIServlet.APIRequestHandler {

    static final CanDeleteCurrency instance = new CanDeleteCurrency();

    private CanDeleteCurrency() {
        super(new APITag[] {APITag.MS}, "account", "currency");
    }

    @Override
    protected JSONStreamAware processRequest(HttpServletRequest req) throws RuvException {
        Currency currency = ParameterParser.getCurrency(req);
        long accountId = ParameterParser.getAccountId(req, true);
        JSONObject response = new JSONObject();
        response.put("canDelete", currency.canBeDeletedBy(accountId));
        return response;
    }

}
