package ch.ergon.dope

import ch.ergon.dope.resolvable.clause.model.DeleteClause
import ch.ergon.dope.resolvable.clause.model.SelectClause
import ch.ergon.dope.resolvable.clause.model.SelectDistinctClause
import ch.ergon.dope.resolvable.clause.model.SelectRawClause
import ch.ergon.dope.resolvable.clause.model.UpdateClause
import ch.ergon.dope.resolvable.expression.AsteriskExpression
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.SingleExpression
import ch.ergon.dope.resolvable.fromable.Deletable
import ch.ergon.dope.resolvable.fromable.Fromable
import ch.ergon.dope.resolvable.fromable.Updatable
import ch.ergon.dope.validtype.ValidType

class QueryBuilder {
    fun select(expression: Expression, vararg expressions: Expression) = SelectClause(expression, *expressions)

    fun selectAsterisk() = SelectClause(AsteriskExpression())

    fun selectDistinct(expression: Expression, vararg expressions: Expression) = SelectDistinctClause(expression, *expressions)

    fun <T : ValidType> selectRaw(expression: SingleExpression<T>) = SelectRawClause(expression)

    fun selectFrom(fromable: Fromable) = SelectClause(AsteriskExpression()).from(fromable)

    fun deleteFrom(deletable: Deletable) = DeleteClause(deletable)

    fun update(updatable: Updatable) = UpdateClause(updatable)
}
