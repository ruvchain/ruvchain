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

package ruv.http.accountControl;

import ruv.http.AbstractHttpApiSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        PhasingOnlyTest.class
})

public class AccountControlSuite extends AbstractHttpApiSuite { }
