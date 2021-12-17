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

package ruv;

import ruv.util.Logger;
import ruv.util.Time;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;

import java.util.Properties;

public abstract class BlockchainTest extends AbstractBlockchainTest {

    protected static Tester FORGY;
    protected static Tester ALICE;
    protected static Tester BOB;
    protected static Tester CHUCK;
    protected static Tester DAVE;

    protected static int baseHeight;

    protected static String forgerSecretPhrase = "aSykrgKGZNlSVOMDxkZZgbTvQqJPGtsBggb";
    protected static final String forgerAccountId = "RUV-9KZM-KNYY-QBXZ-5TD8V";

    public static final String aliceSecretPhrase = "hope peace happen touch easy pretend worthless talk them indeed wheel state";
    private static final String bobSecretPhrase2 = "rshw9abtpsa2";
    private static final String chuckSecretPhrase = "eOdBVLMgySFvyiTy8xMuRXDTr45oTzB7L5J";
    private static final String daveSecretPhrase = "t9G2ymCmDsQij7VtYinqrbGCOAtDDA3WiNr";

    protected static boolean isRuvInitialized = false;

    public static void initRuv() {
        if (!isRuvInitialized) {
            Properties properties = ManualForgingTest.newTestProperties();
            properties.setProperty("ruv.isTestnet", "true");
            properties.setProperty("ruv.isOffline", "true");
            properties.setProperty("ruv.enableFakeForging", "true");
            properties.setProperty("ruv.fakeForgingAccount", forgerAccountId);
            properties.setProperty("ruv.timeMultiplier", "1");
            properties.setProperty("ruv.testnetGuaranteedBalanceConfirmations", "1");
            properties.setProperty("ruv.testnetLeasingDelay", "1");
            properties.setProperty("ruv.disableProcessTransactionsThread", "true");
            properties.setProperty("ruv.deleteFinishedShufflings", "false");
            properties.setProperty("ruv.disableSecurityPolicy", "true");
            properties.setProperty("ruv.disableAdminPassword", "true");
            AbstractBlockchainTest.init(properties);
            isRuvInitialized = true;
        }
    }
    
    @BeforeClass
    public static void init() {
        initRuv();
        Ruv.setTime(new Time.CounterTime(Ruv.getEpochTime()));
        baseHeight = blockchain.getHeight();
        Logger.logMessage("baseHeight: " + baseHeight);
        FORGY = new Tester(forgerSecretPhrase);
        ALICE = new Tester(aliceSecretPhrase);
        BOB = new Tester(bobSecretPhrase2);
        CHUCK = new Tester(chuckSecretPhrase);
        DAVE = new Tester(daveSecretPhrase);
    }

    @After
    public void destroy() {
        TransactionProcessorImpl.getInstance().clearUnconfirmedTransactions();
        blockchainProcessor.popOffTo(baseHeight);
    }

    public static void generateBlock() {
        try {
            blockchainProcessor.generateBlock(forgerSecretPhrase, Ruv.getEpochTime());
        } catch (BlockchainProcessor.BlockNotAcceptedException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    public static void generateBlocks(int howMany) {
        for (int i = 0; i < howMany; i++) {
            generateBlock();
        }
    }
}
