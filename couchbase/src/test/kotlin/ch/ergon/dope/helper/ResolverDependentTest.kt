package ch.ergon.dope.helper

import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import kotlin.test.BeforeTest

interface ResolverDependentTest {
    var resolver: CouchbaseResolver

    @BeforeTest
    fun setUp() {
        resolver = CouchbaseResolver()
    }
}
