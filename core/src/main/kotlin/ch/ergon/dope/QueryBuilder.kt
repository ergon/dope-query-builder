package ch.ergon.dope

import ch.ergon.dope.resolvable.clause.DeleteClause
import ch.ergon.dope.resolvable.clause.FromClause
import ch.ergon.dope.resolvable.clause.SelectClause
import ch.ergon.dope.resolvable.clause.SelectDistinctClause
import ch.ergon.dope.resolvable.clause.SelectRawClause
import ch.ergon.dope.resolvable.expression.AsteriskExpression
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.SingleExpression
import ch.ergon.dope.resolvable.fromable.Bucket
import ch.ergon.dope.resolvable.fromable.Fromable

class QueryBuilder {
    fun select(expression: Expression, vararg expressions: Expression): SelectClause = SelectClause(expression, *expressions)

    fun selectAsterisk(): SelectClause = SelectClause(AsteriskExpression())

    fun selectDistinct(expression: Expression, vararg expressions: Expression): SelectDistinctClause =
        SelectDistinctClause(expression, *expressions)

    fun selectRaw(expression: SingleExpression): SelectRawClause = SelectRawClause(expression)

    fun selectFrom(fromable: Fromable): FromClause = SelectClause(AsteriskExpression()).from(fromable)

    fun deleteFrom(from: Bucket): DeleteClause = DeleteClause(from)
}
