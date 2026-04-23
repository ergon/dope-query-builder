package ch.ergon.dope.mongo

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.mongo.resolver.MongoResolver
import ch.ergon.dope.resolvable.bucket.Bucket
import ch.ergon.dope.resolvable.clause.IDeleteReturningClause
import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.clause.IUpdateReturningClause
import ch.ergon.dope.validtype.ValidType

/**
 * Represents a resolved MongoDB query. Use `when` to match the specific operation type.
 *
 * - [Aggregation] — SELECT queries, use `collection.aggregate(stages)`
 * - [Delete] — DELETE queries, use `collection.deleteMany(filter)`
 * - [Update] — UPDATE queries, use `collection.updateMany(filter, updateDocument)`
 * - [ExpressionFragment] — internal expression fragment, not returned to users from `.build()`
 */
sealed interface MongoDopeQuery : DopeQuery {
    val parameters: DopeParameters

    /** Aggregation pipeline for SELECT queries. */
    data class Aggregation(
        val stages: List<String>,
        val bucket: Bucket? = null,
        override val parameters: DopeParameters = DopeParameters(),
    ) : MongoDopeQuery

    /** Filter for DELETE queries. */
    data class Delete(
        val filter: String = "{}",
        val bucket: Bucket,
        override val parameters: DopeParameters = DopeParameters(),
    ) : MongoDopeQuery

    /** Filter and update document for UPDATE queries. */
    data class Update(
        val filter: String = "{}",
        val updateDocument: String = "{}",
        val bucket: Bucket,
        override val parameters: DopeParameters = DopeParameters(),
    ) : MongoDopeQuery

    /** Internal expression fragment used during resolution. */
    data class ExpressionFragment(
        val queryString: String,
        override val parameters: DopeParameters = DopeParameters(),
    ) : MongoDopeQuery
}

/** Helper to extract the queryString from an [MongoDopeQuery.ExpressionFragment] result. */
internal val MongoDopeQuery.queryString: String
    get() = (this as MongoDopeQuery.ExpressionFragment).queryString

/** Builds a SELECT query and returns a typed [MongoDopeQuery.Aggregation]. */
fun <T : ValidType> ISelectOffsetClause<T>.buildMongo(resolver: MongoResolver): MongoDopeQuery.Aggregation =
    build(resolver) as MongoDopeQuery.Aggregation

/** Builds a DELETE query and returns a typed [MongoDopeQuery.Delete]. */
fun IDeleteReturningClause.buildMongo(resolver: MongoResolver): MongoDopeQuery.Delete =
    build(resolver) as MongoDopeQuery.Delete

/** Builds an UPDATE query and returns a typed [MongoDopeQuery.Update]. */
fun IUpdateReturningClause.buildMongo(resolver: MongoResolver): MongoDopeQuery.Update =
    build(resolver) as MongoDopeQuery.Update
