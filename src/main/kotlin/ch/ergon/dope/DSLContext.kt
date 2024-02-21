package ch.ergon.dope

import ch.ergon.dope.resolvable.clause.select.Fromable
import ch.ergon.dope.resolvable.clause.select.factory.From
import ch.ergon.dope.resolvable.clause.select.factory.Select
import ch.ergon.dope.resolvable.clause.select.factory.Where
import ch.ergon.dope.resolvable.expression.Expression

class DSLContext {
    private val select = Select()

    fun select(expression: Expression, vararg expressions: Expression): From = select.select(expression, *expressions)

    fun selectAll(): From = select.selectAll()

    fun selectDistinct(expression: Expression, vararg expressions: Expression): From = select.selectDistinct(expression, *expressions)

    fun selectRaw(expression: Expression): From = select.selectRaw(expression)

    fun selectFrom(fromable: Fromable): Where = select.selectFrom(fromable)
}
