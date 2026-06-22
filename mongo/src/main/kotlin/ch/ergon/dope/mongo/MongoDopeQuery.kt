package ch.ergon.dope.mongo

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.mongo.resolver.MongoResolver
import ch.ergon.dope.resolvable.bucket.Bucket
import ch.ergon.dope.resolvable.clause.IDeleteReturningClause
import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.clause.IUpdateReturningClause
import ch.ergon.dope.validtype.ValidType

sealed interface MongoDopeQuery : DopeQuery {
    data class Aggregation(
        val stages: List<String>,
        val bucket: Bucket? = null,
        val groupSpec: GroupSpec? = null,
    ) : MongoDopeQuery

    data class GroupSpec(
        val accumulators: List<String>,
        val aggregateProjections: List<Pair<String, String>>,
    )

    data class Delete(
        val filter: String = "{}",
        val bucket: Bucket,
    ) : MongoDopeQuery

    data class Update(
        val filter: String = "{}",
        val updateDocument: String = "{}",
        val bucket: Bucket,
    ) : MongoDopeQuery

    data class ExpressionFragment(
        val queryString: String,
    ) : MongoDopeQuery
}

internal val MongoDopeQuery.queryString: String
    get() = (this as? MongoDopeQuery.ExpressionFragment)?.queryString
        ?: error("Expected an expression fragment, got $this")

fun <T : ValidType> ISelectOffsetClause<T>.buildMongo(resolver: MongoResolver): MongoDopeQuery.Aggregation =
    build(resolver).let { it as? MongoDopeQuery.Aggregation ?: error("Expected a Mongo aggregation query, got $it") }

fun IDeleteReturningClause.buildMongo(resolver: MongoResolver): MongoDopeQuery.Delete =
    build(resolver).let { it as? MongoDopeQuery.Delete ?: error("Expected a Mongo delete query, got $it") }

fun IUpdateReturningClause.buildMongo(resolver: MongoResolver): MongoDopeQuery.Update =
    build(resolver).let { it as? MongoDopeQuery.Update ?: error("Expected a Mongo update query, got $it") }
