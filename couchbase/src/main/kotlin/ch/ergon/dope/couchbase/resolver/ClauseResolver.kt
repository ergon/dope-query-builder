package ch.ergon.dope.couchbase.resolver

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.queryString
import ch.ergon.dope.couchbase.toWithDefinitionDopeQuery
import ch.ergon.dope.couchbase.util.formatListToQueryStringWithBrackets
import ch.ergon.dope.couchbase.util.formatToQueryString
import ch.ergon.dope.couchbase.util.formatToQueryStringWithSymbol
import ch.ergon.dope.merge
import ch.ergon.dope.resolvable.AliasedSelectClause
import ch.ergon.dope.resolvable.bucket.AliasedBucket
import ch.ergon.dope.resolvable.clause.Clause
import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.clause.joinHint.HashOrNestedLoopHint
import ch.ergon.dope.resolvable.clause.joinHint.HashOrNestedLoopHint.HASH_BUILD
import ch.ergon.dope.resolvable.clause.joinHint.HashOrNestedLoopHint.HASH_PROBE
import ch.ergon.dope.resolvable.clause.joinHint.HashOrNestedLoopHint.NESTED_LOOP
import ch.ergon.dope.resolvable.clause.joinHint.IndexHint
import ch.ergon.dope.resolvable.clause.joinHint.KeysHintClass
import ch.ergon.dope.resolvable.clause.joinHint.KeysOrIndexHint
import ch.ergon.dope.resolvable.clause.model.DeleteClause
import ch.ergon.dope.resolvable.clause.model.LimitClause
import ch.ergon.dope.resolvable.clause.model.OffsetClause
import ch.ergon.dope.resolvable.clause.model.OrderExpression
import ch.ergon.dope.resolvable.clause.model.ReturningClause
import ch.ergon.dope.resolvable.clause.model.ReturningSingleClause
import ch.ergon.dope.resolvable.clause.model.SetAssignment
import ch.ergon.dope.resolvable.clause.model.SetClause
import ch.ergon.dope.resolvable.clause.model.UnsetClause
import ch.ergon.dope.resolvable.clause.model.UpdateClause
import ch.ergon.dope.resolvable.clause.model.WhereClause
import ch.ergon.dope.resolvable.clause.model.WithClause

interface ClauseResolver : SelectClauseResolver {
    fun resolve(clause: Clause) =
        when (clause) {
            is WhereClause -> {
                val parentDopeQuery = clause.parentClause.toDopeQuery(this)
                val whereDopeQuery = clause.whereExpression.toDopeQuery(this)
                CouchbaseDopeQuery(
                    queryString = formatToQueryStringWithSymbol(
                        parentDopeQuery.queryString,
                        "WHERE",
                        whereDopeQuery.queryString,
                    ),
                    parameters = parentDopeQuery.parameters.merge(whereDopeQuery.parameters),
                )
            }

            is ReturningClause -> {
                val parent = clause.parentClause.toDopeQuery(this)
                val returnables = arrayOf(clause.returnable) + clause.additionalReturnables
                val returnableDope = returnables.map {
                    when (it) {
                        is AliasedSelectClause<*> -> it.asAliasedSelectClauseDefinition().toDopeQuery(this)
                        else -> it.toDopeQuery(this)
                    }
                }
                CouchbaseDopeQuery(
                    queryString = formatToQueryStringWithSymbol(
                        parent.queryString,
                        "RETURNING",
                        *returnableDope.map { it.queryString }.toTypedArray(),
                    ),
                    parameters = parent.parameters.merge(*returnableDope.map { it.parameters }.toTypedArray()),
                )
            }

            is ReturningSingleClause -> {
                val parent = clause.parentClause.toDopeQuery(this)
                val single = clause.singleReturnable.toDopeQuery(this)
                CouchbaseDopeQuery(
                    queryString = "${parent.queryString} RETURNING ${clause.returningType.name} ${single.queryString}",
                    parameters = parent.parameters.merge(single.parameters),
                )
            }

            is UpdateClause -> {
                val updatable = when (val u = clause.updatable) {
                    is AliasedBucket -> u.asBucketDefinition().toDopeQuery(this)
                    else -> clause.updatable.toDopeQuery(this)
                }
                CouchbaseDopeQuery(
                    queryString = "UPDATE ${updatable.queryString}",
                    parameters = updatable.parameters,
                )
            }

            is DeleteClause -> {
                val bucket = when (val d = clause.deletable) {
                    is AliasedBucket -> d.asBucketDefinition().toDopeQuery(this)
                    else -> clause.deletable.toDopeQuery(this)
                }
                CouchbaseDopeQuery(
                    queryString = "DELETE FROM ${bucket.queryString}",
                    parameters = bucket.parameters,
                )
            }

            is SetClause -> {
                val parentDopeQuery = clause.parentClause.toDopeQuery(this)
                val firstAssignmentDopeQuery = clause.setAssignment.toDopeQuery(this)
                val additionalAssignmentDopeQueries = clause.setAssignments.map { it.toDopeQuery(this) }
                CouchbaseDopeQuery(
                    queryString = formatToQueryString(
                        "${parentDopeQuery.queryString} SET",
                        firstAssignmentDopeQuery.queryString,
                        *additionalAssignmentDopeQueries.map { it.queryString }.toTypedArray(),
                    ),
                    parameters = parentDopeQuery.parameters.merge(
                        firstAssignmentDopeQuery.parameters,
                        *additionalAssignmentDopeQueries.map { it.parameters }.toTypedArray(),
                    ),
                )
            }

            is UnsetClause -> {
                val parentDopeQuery = clause.parentClause.toDopeQuery(this)
                val firstFieldDopeQuery = clause.field.toDopeQuery(this)
                val additionalFieldDopeQueries = clause.fields.map { it.toDopeQuery(this) }
                CouchbaseDopeQuery(
                    queryString = formatToQueryString(
                        "${parentDopeQuery.queryString} UNSET",
                        firstFieldDopeQuery.queryString,
                        *additionalFieldDopeQueries.map { it.queryString }.toTypedArray(),
                    ),
                    parameters = parentDopeQuery.parameters.merge(
                        firstFieldDopeQuery.parameters,
                        *additionalFieldDopeQueries.map { it.parameters }.toTypedArray(),
                    ),
                )
            }

            is LimitClause -> {
                val parentDopeQuery = clause.parentClause.toDopeQuery(this)
                val numberDopeQuery = clause.numberExpression.toDopeQuery(this)
                CouchbaseDopeQuery(
                    queryString = formatToQueryStringWithSymbol(
                        parentDopeQuery.queryString,
                        "LIMIT",
                        numberDopeQuery.queryString,
                    ),
                    parameters = parentDopeQuery.parameters.merge(numberDopeQuery.parameters),
                )
            }

            is OffsetClause -> {
                val parentDopeQuery = clause.parentClause.toDopeQuery(this)
                val numberDopeQuery = clause.numberExpression.toDopeQuery(this)
                CouchbaseDopeQuery(
                    queryString = formatToQueryStringWithSymbol(
                        parentDopeQuery.queryString,
                        "OFFSET",
                        numberDopeQuery.queryString,
                    ),
                    parameters = parentDopeQuery.parameters.merge(numberDopeQuery.parameters),
                )
            }

            is ISelectOffsetClause<*> -> resolve(clause)

            else -> throw UnsupportedOperationException("Not supported: $clause")
        }

    fun resolve(withClause: WithClause): CouchbaseDopeQuery {
        val first = withClause.withExpression
        val firstDope = first.toWithDefinitionDopeQuery(this)
        val additionalWithExpressions = withClause.additionalWithExpressions.map { variable ->
            variable.toWithDefinitionDopeQuery(this)
        }
        return CouchbaseDopeQuery(
            queryString = "WITH ${listOf(firstDope, *additionalWithExpressions.toTypedArray()).joinToString(", ") { it.queryString }}",
            parameters = firstDope.parameters.merge(*additionalWithExpressions.map { it.parameters }.toTypedArray()),
        )
    }

    fun resolve(orderExpression: OrderExpression): CouchbaseDopeQuery {
        val exp = orderExpression.expression.toDopeQuery(this)
        return CouchbaseDopeQuery(
            queryString = listOfNotNull(exp.queryString, orderExpression.orderByType?.queryString).joinToString(" "),
            parameters = exp.parameters,
        )
    }

    fun resolve(setAssignment: SetAssignment<*>): CouchbaseDopeQuery {
        val field = setAssignment.field.toDopeQuery(this)
        val value = setAssignment.value.toDopeQuery(this)
        return CouchbaseDopeQuery(
            queryString = "${field.queryString} = ${value.queryString}",
            parameters = field.parameters.merge(value.parameters),
        )
    }

    fun resolve(hashOrNestedLoopHint: HashOrNestedLoopHint): CouchbaseDopeQuery = when (hashOrNestedLoopHint) {
        HASH_BUILD -> CouchbaseDopeQuery("HASH (BUILD)")
        HASH_PROBE -> CouchbaseDopeQuery("HASH (PROBE)")
        NESTED_LOOP -> CouchbaseDopeQuery("NL")
    }

    fun resolve(keysOrIndexHint: KeysOrIndexHint): CouchbaseDopeQuery = when (keysOrIndexHint) {
        is KeysHintClass -> {
            val keys = keysOrIndexHint.keys.toDopeQuery(this)
            CouchbaseDopeQuery(queryString = "KEYS ${keys.queryString}", parameters = keys.parameters)
        }

        is IndexHint -> {
            val refs = keysOrIndexHint.indexReferences.map { it.toDopeQuery(this) }
            CouchbaseDopeQuery(
                queryString = formatToQueryString("INDEX", formatListToQueryStringWithBrackets(refs)),
                parameters = refs.map { it.parameters }.merge(),
            )
        }
    }
}
