package ch.ergon.dope.mongo

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.bucket.Bucket

data class MongoOldDopeQuery(
    // Represents the filter document for a $match stage (JSON object string)
    val queryString: String,
    val namedParams: DopeParameters = DopeParameters(),
    // Represents the projection document for a $project stage (JSON object string)
    val projections: String = "",
    // Represents the sort document for a $sort stage (JSON object string)
    val sort: String = "",
) : DopeQuery

data class MongoDopeQuery(
    // Represents the aggregate query string
    val queryString: String,
    val bucket: Bucket? = null,
    val namedParams: DopeParameters = DopeParameters(),
) : DopeQuery
