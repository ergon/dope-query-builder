package ch.ergon.dope.resolvable.clause.select.factory

import ch.ergon.dope.resolvable.clause.Clause
import ch.ergon.dope.resolvable.clause.ClauseBuilder
import ch.ergon.dope.resolvable.clause.select.Fromable
import ch.ergon.dope.resolvable.expression.Expression

class Select(clauses: List<Clause> = emptyList()) : From(clauses) {
    private val clauseBuilder: ClauseBuilder =
        ClauseBuilder(clauses)

    fun select(expression: Expression, vararg expressions: Expression): From = clauseBuilder.select(expression, *expressions)

    fun selectAll(): From = clauseBuilder.selectAll()

    fun selectDistinct(expression: Expression, vararg expressions: Expression): From = clauseBuilder.selectDistinct(expression, *expressions)

    fun selectRaw(expression: Expression): From = clauseBuilder.selectRaw(expression)

    fun selectFrom(fromable: Fromable): Where = clauseBuilder.selectFrom(fromable)
}
