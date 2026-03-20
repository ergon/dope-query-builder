package ch.ergon.dope.mongo

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.bucket.Bucket

/**
 * Represents a resolved MongoDB query.
 *
 * For **aggregation** (SELECT): use [stages] with `collection.aggregate(stages)`.
 *
 * For **delete**: use [filter] with `collection.deleteMany(filter)`.
 *
 * For **update**: use [filter] and [updateDocument] with `collection.updateMany(filter, updateDocument)`.
 *
 * @param queryString expression fragment used internally during resolution
 * @param stages aggregation pipeline stages as JSON strings
 * @param bucket the target MongoDB collection
 * @param namedParameters query parameters for parameterized queries
 * @param filter JSON filter document for delete/update operations
 * @param updateDocument JSON update document for update operations
 */
data class MongoDopeQuery(
    val stages: List<String> = emptyList(),
    val bucket: Bucket? = null,
    val namedParameters: DopeParameters = DopeParameters(),
    val filter: String = "{}",
    val updateDocument: String = "{}",
    val queryString: String = "",
) : DopeQuery
