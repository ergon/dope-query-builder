package ch.ergon.dope.couchbase

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery

data class CouchbaseDopeQuery(
    val queryString: String,
    val parameters: DopeParameters = DopeParameters(),
) : DopeQuery
