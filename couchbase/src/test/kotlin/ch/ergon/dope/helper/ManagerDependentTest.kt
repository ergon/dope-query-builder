package ch.ergon.dope.helper

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.CouchbaseResolver
import kotlin.test.BeforeTest

interface ManagerDependentTest {
    var manager: DopeQueryManager<CouchbaseDopeQuery>

    @BeforeTest
    fun setUp() {
        manager = DopeQueryManager(CouchbaseResolver)
    }
}
