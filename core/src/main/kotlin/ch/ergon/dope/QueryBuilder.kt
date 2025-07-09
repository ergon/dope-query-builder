package ch.ergon.dope

import ch.ergon.dope.resolvable.Deletable
import ch.ergon.dope.resolvable.Fromable
import ch.ergon.dope.resolvable.Selectable
import ch.ergon.dope.resolvable.Updatable
import ch.ergon.dope.resolvable.asterisk
import ch.ergon.dope.resolvable.clause.model.DeleteClause
import ch.ergon.dope.resolvable.clause.model.SelectClause
import ch.ergon.dope.resolvable.clause.model.SelectDistinctClause
import ch.ergon.dope.resolvable.clause.model.SelectRawClause
import ch.ergon.dope.resolvable.clause.model.UpdateClause
import ch.ergon.dope.resolvable.clause.model.WithClause
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.type.DopeVariable
import ch.ergon.dope.validtype.ValidType

object QueryBuilder : QueryProvider {
    fun with(variable: DopeVariable<out ValidType>, vararg additionalVariables: DopeVariable<out ValidType>) =
        WithClause(variable, *additionalVariables)

    override fun select(expression: Selectable, vararg expressions: Selectable) =
        SelectClause(expression, *expressions)

    override fun selectAsterisk() = SelectClause(asterisk())

    override fun selectDistinct(expression: Selectable, vararg expressions: Selectable) =
        SelectDistinctClause(expression, *expressions)

    override fun <T : ValidType> selectRaw(expression: Expression<T>) = SelectRawClause(expression)

    override fun selectFrom(fromable: Fromable) = SelectClause(asterisk()).from(fromable)

    fun deleteFrom(deletable: Deletable) = DeleteClause(deletable)

    fun update(updatable: Updatable) = UpdateClause(updatable)
}
