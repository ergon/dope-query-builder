package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.ISelectClause
import ch.ergon.dope.resolvable.formatToQueryString
import ch.ergon.dope.resolvable.fromable.RawSelectable
import ch.ergon.dope.resolvable.fromable.Selectable
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.ValidType

class SelectClause(
    private val expression: Selectable,
    private vararg val expressions: Selectable,
) : ISelectClause<ObjectType> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val expressionDopeQuery = expression.toDopeQuery(manager)
        val expressionsDopeQuery = expressions.map { it.toDopeQuery(manager) }
        return DopeQuery(
            queryString = formatToQueryString(
                "SELECT",
                expressionDopeQuery.queryString,
                *expressionsDopeQuery.map { it.queryString }.toTypedArray(),
            ),
            parameters = expressionDopeQuery.parameters.merge(*expressionsDopeQuery.map { it.parameters }.toTypedArray()),
        )
    }
}

class SelectRawClause<T : ValidType>(private val expression: RawSelectable<T>) : ISelectClause<T> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val expressionDopeQuery = expression.toDopeQuery(manager)
        return DopeQuery(
            queryString = formatToQueryString("SELECT RAW", expressionDopeQuery.queryString),
            parameters = expressionDopeQuery.parameters,
        )
    }
}

class SelectDistinctClause(private val expression: Selectable, private vararg val expressions: Selectable) : ISelectClause<ObjectType> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val expressionsDopeQuery = expressions.map { it.toDopeQuery(manager) }
        val expressionDopeQuery = expression.toDopeQuery(manager)
        return DopeQuery(
            queryString = formatToQueryString(
                "SELECT DISTINCT",
                expressionDopeQuery.queryString,
                *expressionsDopeQuery.map { it.queryString }.toTypedArray(),
            ),
            parameters = expressionDopeQuery.parameters.merge(*expressionsDopeQuery.map { it.parameters }.toTypedArray()),
        )
    }
}
