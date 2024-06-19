package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.clause.IDeleteClause
import ch.ergon.dope.resolvable.clause.IDeleteUseKeysClause
import ch.ergon.dope.resolvable.clause.ISelectFromClause
import ch.ergon.dope.resolvable.clause.ISelectUseKeysClause
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.StringType

private const val USE_KEYS = "USE KEYS"

sealed class SelectUseKeysClause(
    private val parentDopeQuery: DopeQuery,
    private val keysDopeQuery: DopeQuery,
) : ISelectUseKeysClause {
    override fun toDopeQuery() = DopeQuery(
        queryString = "${parentDopeQuery.queryString} $USE_KEYS ${keysDopeQuery.queryString}",
        parameters = parentDopeQuery.parameters + keysDopeQuery.parameters,
    )
}

class SelectUseKeysStringClause(key: TypeExpression<StringType>, parentClause: ISelectFromClause) :
    SelectUseKeysClause(parentClause.toDopeQuery(), key.toDopeQuery())

class SelectUseKeysArrayClause(keys: TypeExpression<ArrayType<StringType>>, parentClause: ISelectFromClause) :
    SelectUseKeysClause(parentClause.toDopeQuery(), keys.toDopeQuery())

sealed class DeleteUseKeysClause(
    private val parentDopeQuery: DopeQuery,
    private val keysDopeQuery: DopeQuery,
) : IDeleteUseKeysClause {
    override fun toDopeQuery() = DopeQuery(
        queryString = "${parentDopeQuery.queryString} $USE_KEYS ${keysDopeQuery.queryString}",
        parameters = parentDopeQuery.parameters + keysDopeQuery.parameters,
    )
}

class DeleteUseKeysStringClause(key: TypeExpression<StringType>, parentClause: IDeleteClause) :
    DeleteUseKeysClause(parentClause.toDopeQuery(), key.toDopeQuery())

class DeleteUseKeysArrayClause(keys: TypeExpression<ArrayType<StringType>>, parentClause: IDeleteClause) :
    DeleteUseKeysClause(parentClause.toDopeQuery(), keys.toDopeQuery())
