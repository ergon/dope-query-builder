package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.orEmpty
import ch.ergon.dope.resolvable.Selectable
import ch.ergon.dope.resolvable.clause.ISelectClause
import ch.ergon.dope.resolvable.clause.ISelectWithClause
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.ValidType

class SelectClause(
    private val expression: Selectable,
    private vararg val expressions: Selectable,
    private val parentClause: ISelectWithClause? = null,
) : ISelectClause<ObjectType> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val parentDopeQuery = parentClause?.toDopeQuery(manager)
        val expressionDopeQuery = expression.toDopeQuery(manager)
        val expressionsDopeQuery = expressions.map { it.toDopeQuery(manager) }
        return DopeQuery(
            queryString = parentDopeQuery?.let { "${it.queryString} " }.orEmpty() +
                "SELECT " +
                listOf(expressionDopeQuery, *expressionsDopeQuery.toTypedArray()).joinToString { it.queryString },
            parameters = (parentDopeQuery?.parameters.orEmpty()).merge(
                expressionDopeQuery.parameters,
                *expressionsDopeQuery.map { it.parameters }.toTypedArray(),
            ),
        )
    }
}

class SelectRawClause<T : ValidType>(
    private val expression: Expression<T>,
    private val parentClause: ISelectWithClause? = null,
) : ISelectClause<T> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val parentDopeQuery = parentClause?.toDopeQuery(manager)
        val expressionDopeQuery = expression.toDopeQuery(manager)
        return DopeQuery(
            queryString = parentDopeQuery?.let { "${it.queryString} " }.orEmpty() +
                "SELECT RAW " + expressionDopeQuery.queryString,
            parameters = parentDopeQuery?.parameters.orEmpty().merge(expressionDopeQuery.parameters),
        )
    }
}

class SelectDistinctClause(
    private val expression: Selectable,
    private vararg val expressions: Selectable,
    private val parentClause: ISelectWithClause? = null,
) : ISelectClause<ObjectType> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val parentDopeQuery = parentClause?.toDopeQuery(manager)
        val expressionsDopeQuery = expressions.map { it.toDopeQuery(manager) }
        val expressionDopeQuery = expression.toDopeQuery(manager)
        return DopeQuery(
            queryString = parentDopeQuery?.let { "${it.queryString} " }.orEmpty() +
                "SELECT DISTINCT " +
                listOf(expressionDopeQuery, *expressionsDopeQuery.toTypedArray()).joinToString { it.queryString },
            parameters = expressionDopeQuery.parameters.merge(*expressionsDopeQuery.map { it.parameters }.toTypedArray()),
        )
    }
}
