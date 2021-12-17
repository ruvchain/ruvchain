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

import ruv.db.BasicDb;
import ruv.db.TransactionalDb;

public final class Db {

    public static final String PREFIX = Constants.isTestnet ? "ruv.testDb" : "ruv.db";
    public static final TransactionalDb db = new TransactionalDb(new BasicDb.DbProperties()
            .maxCacheSize(Ruv.getIntProperty("ruv.dbCacheKB"))
            .dbUrl(Ruv.getStringProperty(PREFIX + "Url"))
            .dbType(Ruv.getStringProperty(PREFIX + "Type"))
            .dbDir(Ruv.getStringProperty(PREFIX + "Dir"))
            .dbParams(Ruv.getStringProperty(PREFIX + "Params"))
            .dbUsername(Ruv.getStringProperty(PREFIX + "Username"))
            .dbPassword(Ruv.getStringProperty(PREFIX + "Password", null, true))
            .maxConnections(Ruv.getIntProperty("ruv.maxDbConnections"))
            .loginTimeout(Ruv.getIntProperty("ruv.dbLoginTimeout"))
            .defaultLockTimeout(Ruv.getIntProperty("ruv.dbDefaultLockTimeout") * 1000)
            .maxMemoryRows(Ruv.getIntProperty("ruv.dbMaxMemoryRows"))
    );

    public static void init() {
        db.init(new RuvDbVersion());
    }

    static void shutdown() {
        db.shutdown();
    }

    private Db() {} // never

}
