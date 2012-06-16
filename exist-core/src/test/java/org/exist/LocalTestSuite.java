package org.exist;

import org.exist.collections.triggers.AllTriggerTests;
import org.exist.fulltext.AllFulltextTests;
import org.exist.xquery.AllXqueryTests;
import org.exist.xquery.OptimizerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
//        XmldbLocalTests.class,
//        AllXupdateTests.class,
        AllXqueryTests.class,
        OptimizerTest.class,
        AllTriggerTests.class,
        AllFulltextTests.class
})
public class LocalTestSuite {
}
