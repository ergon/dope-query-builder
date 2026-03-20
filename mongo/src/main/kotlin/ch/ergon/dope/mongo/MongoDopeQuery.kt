package ch.ergon.dope.mongo

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.bucket.Bucket

data class MongoDopeQuery(
    val queryString: String = "",
    val stages: List<String> = emptyList(),
    val bucket: Bucket? = null,
    val namedParameters: DopeParameters = DopeParameters(),
) : DopeQuery
