package ch.ergon.dope.couchbase.resolver

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolver.expression.queryString
import ch.ergon.dope.couchbase.util.formatBucket
import ch.ergon.dope.couchbase.util.formatListToQueryStringWithBrackets
import ch.ergon.dope.couchbase.util.formatToQueryStringWithSymbol
import ch.ergon.dope.resolvable.bucket.AliasedBucket
import ch.ergon.dope.resolvable.bucket.AliasedBucketDefinition
import ch.ergon.dope.resolvable.bucket.IndexReference
import ch.ergon.dope.resolvable.bucket.UseIndex
import ch.ergon.dope.resolvable.bucket.UseKeysClass

interface KeySpaceResolver : AbstractCouchbaseResolver {
    fun resolve(aliasedBucket: AliasedBucketDefinition): CouchbaseDopeQuery =
        CouchbaseDopeQuery(
            formatBucket(
                aliasedBucket.bucketName,
                aliasedBucket.scopeName,
                aliasedBucket.collectionName,
            ) + " AS `${aliasedBucket.alias}`",
        )

    fun resolve(useKeysClass: UseKeysClass): CouchbaseDopeQuery {
        val bucket = when (val bucket = useKeysClass.bucket) {
            is AliasedBucket -> bucket.asBucketDefinition().toDopeQuery(this)
            else -> useKeysClass.bucket.toDopeQuery(this)
        }
        val keys = useKeysClass.useKeys.toDopeQuery(this)
        return CouchbaseDopeQuery(
            queryString = formatToQueryStringWithSymbol(bucket.queryString, "USE KEYS", keys.queryString),
            parameters = bucket.parameters.merge(keys.parameters),
        )
    }

    fun resolve(indexReference: IndexReference): CouchbaseDopeQuery {
        val name = indexReference.indexName?.let { "`$it`" }
        val type = indexReference.indexType?.queryString
        return CouchbaseDopeQuery(queryString = listOfNotNull(name, type).joinToString(" "))
    }

    fun resolve(useIndex: UseIndex): CouchbaseDopeQuery {
        val bucket = useIndex.bucket
        val bucketDopeQuery = when (bucket) {
            is AliasedBucket -> bucket.asBucketDefinition().toDopeQuery(this)
            else -> bucket.toDopeQuery(this)
        }
        val refs = useIndex.indexReferences.map { it.toDopeQuery(this) }
        return CouchbaseDopeQuery(
            queryString = formatToQueryStringWithSymbol(
                bucketDopeQuery.queryString,
                "USE INDEX",
                formatListToQueryStringWithBrackets(refs, separator = ", ", prefix = "(", postfix = ")"),
            ),
            parameters = bucketDopeQuery.parameters.merge(*refs.map { it.parameters }.toTypedArray()),
        )
    }
}
