package ch.ergon.dope.mongo

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.bucket.Bucket

data class MongoDopeQuery(
    val queryString: String,
    val bucket: Bucket? = null,
    val namedParams: DopeParameters = DopeParameters(),
) : DopeQuery
