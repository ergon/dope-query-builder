package ch.ergon.dope.couchbase

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.type.DopeVariable
import ch.ergon.dope.validtype.ValidType

fun <T : ValidType> DopeVariable<T>.toLetDefinitionDopeQuery(manager: DopeQueryManager<CouchbaseDopeQuery>): CouchbaseDopeQuery {
    val valueDopeQuery = value.toDopeQuery(manager)
    return CouchbaseDopeQuery(
        queryString = "`$name` = ${valueDopeQuery.queryString}",
        parameters = valueDopeQuery.parameters,
    )
}

fun <T : ValidType> DopeVariable<T>.toWithDefinitionDopeQuery(manager: DopeQueryManager<CouchbaseDopeQuery>): CouchbaseDopeQuery {
    val expressionDopeQuery = value.toDopeQuery(manager)
    return CouchbaseDopeQuery(
        queryString = "`$name` AS (${expressionDopeQuery.queryString})",
        parameters = expressionDopeQuery.parameters,
    )
}
