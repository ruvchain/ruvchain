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

package ruv.http.votingsystem;

import ruv.BlockchainTest;
import ruv.http.APICall;
import ruv.util.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class TestGetPolls extends BlockchainTest {

    @Test
    public void accountPollsIncrease() {
        APICall apiCall = new APICall.Builder("getPolls")
                .param("includeVoters", "false")
                .param("account", Long.toUnsignedString(DAVE.getId()))
                .param("firstIndex", 0)
                .param("lastIndex", 100)
                .build();

        JSONObject jsonResponse = apiCall.invoke();
        Logger.logMessage("getPollsResponse:" + jsonResponse.toJSONString());
        JSONArray polls = (JSONArray) jsonResponse.get("polls");
        int initialSize = polls.size();

        APICall createPollApiCall = new TestCreatePoll.CreatePollBuilder().secretPhrase(DAVE.getSecretPhrase()).build();
        String poll = TestCreatePoll.issueCreatePoll(createPollApiCall, false);
        generateBlock();

        jsonResponse = apiCall.invoke();
        Logger.logMessage("getPollsResponse:" + jsonResponse.toJSONString());
        polls = (JSONArray) jsonResponse.get("polls");
        int size = polls.size();

        JSONObject lastPoll = (JSONObject) polls.get(0);
        Assert.assertEquals(poll, lastPoll.get("poll"));
        Assert.assertEquals(size, initialSize + 1);
    }
}