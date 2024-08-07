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

class SelectUseKeys private constructor(
    private val useKeys: TypeExpression<out ValidType>,
    private val parentClause: ISelectFromClause,
) : ISelectUseKeysClause {
    companion object {
        @JvmName("selectSingleUseKeysClauseConstructor")
        fun SelectUseKeysClause(key: TypeExpression<StringType>, parentClause: ISelectFromClause) =
            SelectUseKeys(key, parentClause)

        @JvmName("selectMultipleUseKeysClauseConstructor")
        fun SelectUseKeysClause(keys: TypeExpression<ArrayType<StringType>>, parentClause: ISelectFromClause) =
            SelectUseKeys(keys, parentClause)
    }

    override fun toDopeQuery(): DopeQuery {
        val parentDopeQuery = parentClause.toDopeQuery()
        val keysDopeQuery = useKeys.toDopeQuery()
        return DopeQuery(
            queryString = formatToQueryStringWithSymbol(parentDopeQuery.queryString, USE_KEYS, keysDopeQuery.queryString),
            parameters = parentDopeQuery.parameters + keysDopeQuery.parameters,
        )
    }
}

class DeleteUseKeys private constructor(
    private val useKeys: TypeExpression<out ValidType>,
    private val parentClause: IDeleteClause,
) : IDeleteUseKeysClause {
    companion object {
        @JvmName("deleteSingleUseKeysClauseConstructor")
        fun DeleteUseKeysClause(key: TypeExpression<StringType>, parentClause: IDeleteClause) =
            DeleteUseKeys(key, parentClause)

        @JvmName("deleteMultipleUseKeysClauseConstructor")
        fun DeleteUseKeysClause(keys: TypeExpression<ArrayType<StringType>>, parentClause: IDeleteClause) =
            DeleteUseKeys(keys, parentClause)
    }

    override fun toDopeQuery(): DopeQuery {
        val parentDopeQuery = parentClause.toDopeQuery()
        val keysDopeQuery = useKeys.toDopeQuery()
        return DopeQuery(
            queryString = formatToQueryStringWithSymbol(parentDopeQuery.queryString, USE_KEYS, keysDopeQuery.queryString),
            parameters = parentDopeQuery.parameters + keysDopeQuery.parameters,
        )
    }
}
