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

import ruv.RuvException;
import ruv.Shuffler;
import ruv.Shuffling;
import ruv.util.JSON;
import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

import javax.servlet.http.HttpServletRequest;

public final class StartShuffler extends APIServlet.APIRequestHandler {

    static final StartShuffler instance = new StartShuffler();

    private StartShuffler() {
        super(new APITag[]{APITag.SHUFFLING}, "secretPhrase", "shufflingFullHash", "recipientSecretPhrase", "recipientPublicKey");
    }

    @Override
    protected JSONStreamAware processRequest(HttpServletRequest req) throws RuvException {
        byte[] shufflingFullHash = ParameterParser.getBytes(req, "shufflingFullHash", true);
        String secretPhrase = ParameterParser.getSecretPhrase(req, true);
        byte[] recipientPublicKey = ParameterParser.getPublicKey(req, "recipient");
        try {
            Shuffler shuffler = Shuffler.addOrGetShuffler(secretPhrase, recipientPublicKey, shufflingFullHash);
            return shuffler != null ? JSONData.shuffler(shuffler, false) : JSON.emptyJSON;
        } catch (Shuffler.ShufflerLimitException e) {
            JSONObject response = new JSONObject();
            response.put("errorCode", 7);
            response.put("errorDescription", e.getMessage());
            return JSON.prepare(response);
        } catch (Shuffler.DuplicateShufflerException e) {
            JSONObject response = new JSONObject();
            response.put("errorCode", 8);
            response.put("errorDescription", e.getMessage());
            return JSON.prepare(response);
        } catch (Shuffler.InvalidRecipientException e) {
            return JSONResponses.incorrect("recipientPublicKey", e.getMessage());
        } catch (Shuffler.ControlledAccountException e) {
            JSONObject response = new JSONObject();
            response.put("errorCode", 9);
            response.put("errorDescription", e.getMessage());
            return JSON.prepare(response);
        } catch (Shuffler.ShufflerException e) {
            if (e.getCause() instanceof RuvException.InsufficientBalanceException) {
                Shuffling shuffling = Shuffling.getShuffling(shufflingFullHash);
                if (shuffling == null) {
                    return JSONResponses.NOT_ENOUGH_FUNDS;
                }
                return JSONResponses.notEnoughHolding(shuffling.getHoldingType());
            }
            JSONObject response = new JSONObject();
            response.put("errorCode", 10);
            response.put("errorDescription", e.getMessage());
            return JSON.prepare(response);
        }
    }

    @Override
    protected boolean requirePost() {
        return true;
    }

    @Override
    protected boolean allowRequiredBlockParameters() {
        return false;
    }

    @Override
    protected boolean requireFullClient() {
        return true;
    }

}
