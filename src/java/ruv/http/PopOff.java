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

import ruv.Block;
import ruv.Ruv;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public final class PopOff extends APIServlet.APIRequestHandler {

    static final PopOff instance = new PopOff();

    private PopOff() {
        super(new APITag[] {APITag.DEBUG}, "numBlocks", "height", "keepTransactions");
    }

    @Override
    protected JSONStreamAware processRequest(HttpServletRequest req) {

        int numBlocks = 0;
        try {
            numBlocks = Integer.parseInt(req.getParameter("numBlocks"));
        } catch (NumberFormatException ignored) {}
        int height = 0;
        try {
            height = Integer.parseInt(req.getParameter("height"));
        } catch (NumberFormatException ignored) {}
        boolean keepTransactions = "true".equalsIgnoreCase(req.getParameter("keepTransactions"));
        List<? extends Block> blocks;
        try {
            Ruv.getBlockchainProcessor().setGetMoreBlocks(false);
            if (numBlocks > 0) {
                blocks = Ruv.getBlockchainProcessor().popOffTo(Ruv.getBlockchain().getHeight() - numBlocks);
            } else if (height > 0) {
                blocks = Ruv.getBlockchainProcessor().popOffTo(height);
            } else {
                return JSONResponses.missing("numBlocks", "height");
            }
        } finally {
            Ruv.getBlockchainProcessor().setGetMoreBlocks(true);
        }
        JSONArray blocksJSON = new JSONArray();
        blocks.forEach(block -> blocksJSON.add(JSONData.block(block, true, false)));
        JSONObject response = new JSONObject();
        response.put("blocks", blocksJSON);
        if (keepTransactions) {
            blocks.forEach(block -> Ruv.getTransactionProcessor().processLater(block.getTransactions()));
        }
        return response;
    }

    @Override
    protected final boolean requirePost() {
        return true;
    }

    @Override
    protected boolean requirePassword() {
        return true;
    }

    @Override
    protected final boolean allowRequiredBlockParameters() {
        return false;
    }

    @Override
    protected boolean requireBlockchain() {
        return false;
    }

}
