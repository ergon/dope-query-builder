package ch.ergon.dope.couchbase

import ch.ergon.dope.resolvable.expression.type.DopeVariable
import ch.ergon.dope.validtype.ValidType

fun <T : ValidType> DopeVariable<T>.toLetDefinitionDopeQuery(resolver: CouchbaseResolver): CouchbaseDopeQuery {
    val valueDopeQuery = value.toDopeQuery(resolver)
    return CouchbaseDopeQuery(
        queryString = "`$name` = ${valueDopeQuery.queryString}",
        parameters = valueDopeQuery.parameters,
    )
}

fun <T : ValidType> DopeVariable<T>.toWithDefinitionDopeQuery(resolver: CouchbaseResolver): CouchbaseDopeQuery {
    val expressionDopeQuery = value.toDopeQuery(resolver)
    return CouchbaseDopeQuery(
        queryString = "`$name` AS (${expressionDopeQuery.queryString})",
        parameters = expressionDopeQuery.parameters,
    )
}
