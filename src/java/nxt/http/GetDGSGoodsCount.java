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

import ruv.DigitalGoodsStore;
import ruv.RuvException;
import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

import javax.servlet.http.HttpServletRequest;

public final class GetDGSGoodsCount extends APIServlet.APIRequestHandler {

    static final GetDGSGoodsCount instance = new GetDGSGoodsCount();

    private GetDGSGoodsCount() {
        super(new APITag[] {APITag.DGS}, "seller", "inStockOnly");
    }

    @Override
    protected JSONStreamAware processRequest(HttpServletRequest req) throws RuvException {
        long sellerId = ParameterParser.getAccountId(req, "seller", false);
        boolean inStockOnly = !"false".equalsIgnoreCase(req.getParameter("inStockOnly"));

        JSONObject response = new JSONObject();
        response.put("numberOfGoods", sellerId != 0
                ? DigitalGoodsStore.Goods.getSellerGoodsCount(sellerId, inStockOnly)
                : inStockOnly ? DigitalGoodsStore.Goods.getCountInStock() : DigitalGoodsStore.Goods.getCount());
        return response;
    }

}
