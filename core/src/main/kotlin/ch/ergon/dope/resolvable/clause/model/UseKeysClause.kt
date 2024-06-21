package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.clause.IDeleteClause
import ch.ergon.dope.resolvable.clause.IDeleteUseKeysClause
import ch.ergon.dope.resolvable.clause.ISelectFromClause
import ch.ergon.dope.resolvable.clause.ISelectUseKeysClause
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.formatToQueryStringWithSymbol
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

private const val USE_KEYS = "USE KEYS"

sealed class SelectUseKeysClause(
    private val parentClause: ISelectFromClause,
    private val keys: TypeExpression<out ValidType>,
) : ISelectUseKeysClause {
    override fun toDopeQuery(): DopeQuery {
        val parentDopeQuery = parentClause.toDopeQuery()
        val keysDopeQuery = keys.toDopeQuery()
        return DopeQuery(
            queryString = formatToQueryStringWithSymbol(parentDopeQuery.queryString, USE_KEYS, keysDopeQuery.queryString),
            parameters = parentDopeQuery.parameters + keysDopeQuery.parameters,
        )
    }
}

class SelectUseKeysStringClause(key: TypeExpression<StringType>, parentClause: ISelectFromClause) :
    SelectUseKeysClause(parentClause, key)

class SelectUseKeysArrayClause(keys: TypeExpression<ArrayType<StringType>>, parentClause: ISelectFromClause) :
    SelectUseKeysClause(parentClause, keys)

sealed class DeleteUseKeysClause(
    private val parentClause: IDeleteClause,
    private val keys: TypeExpression<out ValidType>,
) : IDeleteUseKeysClause {
    override fun toDopeQuery(): DopeQuery {
        val parentDopeQuery = parentClause.toDopeQuery()
        val keysDopeQuery = keys.toDopeQuery()
        return DopeQuery(
            queryString = formatToQueryStringWithSymbol(parentDopeQuery.queryString, USE_KEYS, keysDopeQuery.queryString),
            parameters = parentDopeQuery.parameters + keysDopeQuery.parameters,
        )
    }
}

class DeleteUseKeysStringClause(key: TypeExpression<StringType>, parentClause: IDeleteClause) :
    DeleteUseKeysClause(parentClause, key)

class DeleteUseKeysArrayClause(keys: TypeExpression<ArrayType<StringType>>, parentClause: IDeleteClause) :
    DeleteUseKeysClause(parentClause, keys)
