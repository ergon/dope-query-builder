package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.clause.IDeleteClause
import ch.ergon.dope.resolvable.clause.IDeleteUseKeysClause
import ch.ergon.dope.resolvable.clause.ISelectFromClause
import ch.ergon.dope.resolvable.clause.ISelectUseKeysClause
import ch.ergon.dope.resolvable.clause.IUpdateClause
import ch.ergon.dope.resolvable.clause.IUpdateUseKeysClause
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.formatToQueryStringWithSymbol
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

private const val USE_KEYS = "USE KEYS"

class SelectUseKeysClause : ISelectUseKeysClause {
    private lateinit var useKeys: TypeExpression<out ValidType>
    private lateinit var parentClause: ISelectFromClause

    companion object {
        @JvmName("selectSingleUseKeysClauseConstructor")
        fun SelectUseKeysClause(key: TypeExpression<StringType>, parentClause: ISelectFromClause): SelectUseKeysClause {
            val instance = SelectUseKeysClause()
            instance.useKeys = key
            instance.parentClause = parentClause
            return instance
        }

        @JvmName("selectMultipleUseKeysClauseConstructor")
        fun SelectUseKeysClause(keys: TypeExpression<ArrayType<StringType>>, parentClause: ISelectFromClause): SelectUseKeysClause {
            val instance = SelectUseKeysClause()
            instance.useKeys = keys
            instance.parentClause = parentClause
            return instance
        }
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

class DeleteUseKeysClause : IDeleteUseKeysClause {
    private lateinit var useKeys: TypeExpression<out ValidType>
    private lateinit var parentClause: IDeleteClause

    companion object {
        @JvmName("deleteSingleUseKeysClauseConstructor")
        fun DeleteUseKeysClause(key: TypeExpression<StringType>, parentClause: IDeleteClause): DeleteUseKeysClause {
            val instance = DeleteUseKeysClause()
            instance.useKeys = key
            instance.parentClause = parentClause
            return instance
        }

        @JvmName("deleteMultipleUseKeysClauseConstructor")
        fun DeleteUseKeysClause(keys: TypeExpression<ArrayType<StringType>>, parentClause: IDeleteClause): DeleteUseKeysClause {
            val instance = DeleteUseKeysClause()
            instance.useKeys = keys
            instance.parentClause = parentClause
            return instance
        }
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

class UpdateUseKeysClause : IUpdateUseKeysClause {
    private lateinit var useKeys: TypeExpression<out ValidType>
    private lateinit var parentClause: IUpdateClause

    companion object {
        @JvmName("updateSingleUseKeysClauseConstructor")
        fun UpdateUseKeysClause(key: TypeExpression<StringType>, parentClause: IUpdateClause): UpdateUseKeysClause {
            val instance = UpdateUseKeysClause()
            instance.useKeys = key
            instance.parentClause = parentClause
            return instance
        }

        @JvmName("updateMultipleUseKeysClauseConstructor")
        fun UpdateUseKeysClause(keys: TypeExpression<ArrayType<StringType>>, parentClause: IUpdateClause): UpdateUseKeysClause {
            val instance = UpdateUseKeysClause()
            instance.useKeys = keys
            instance.parentClause = parentClause
            return instance
        }
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
