package ch.ergon.dope.resolvable.clause.select.factory

import ch.ergon.dope.resolvable.clause.Clause
import ch.ergon.dope.resolvable.clause.ClauseBuilder
import ch.ergon.dope.resolvable.clause.select.Fromable
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.SingleExpression

class Select(clauses: List<Clause> = emptyList()) : From(clauses) {
    private val clauseBuilder: ClauseBuilder =
        ClauseBuilder(clauses)

    fun select(expression: Expression, vararg expressions: Expression): From = clauseBuilder.select(expression, *expressions)

    fun selectAsterisk(): From = clauseBuilder.selectAsterisk()

    fun selectDistinct(expression: Expression, vararg expressions: Expression): From = clauseBuilder.selectDistinct(expression, *expressions)

    fun selectRaw(expression: SingleExpression): From = clauseBuilder.selectRaw(expression)

    fun selectFrom(fromable: Fromable): Where = clauseBuilder.selectFrom(fromable)
}
