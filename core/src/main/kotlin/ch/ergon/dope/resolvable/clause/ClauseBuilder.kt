package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.resolvable.clause.select.FromClause
import ch.ergon.dope.resolvable.clause.select.Fromable
import ch.ergon.dope.resolvable.clause.select.GroupByClause
import ch.ergon.dope.resolvable.clause.select.LimitClause
import ch.ergon.dope.resolvable.clause.select.OffsetClause
import ch.ergon.dope.resolvable.clause.select.OrderByClause
import ch.ergon.dope.resolvable.clause.select.OrderByTypeClause
import ch.ergon.dope.resolvable.clause.select.SelectClause
import ch.ergon.dope.resolvable.clause.select.SelectDistinctClause
import ch.ergon.dope.resolvable.clause.select.SelectRawClause
import ch.ergon.dope.resolvable.clause.select.WhereClause
import ch.ergon.dope.resolvable.clause.select.factory.Build
import ch.ergon.dope.resolvable.clause.select.factory.From
import ch.ergon.dope.resolvable.clause.select.factory.GroupBy
import ch.ergon.dope.resolvable.clause.select.factory.Limit
import ch.ergon.dope.resolvable.clause.select.factory.Offset
import ch.ergon.dope.resolvable.clause.select.factory.OrderBy
import ch.ergon.dope.resolvable.clause.select.factory.OrderByType
import ch.ergon.dope.resolvable.clause.select.factory.Where
import ch.ergon.dope.resolvable.expression.AsteriskExpression
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.SingleExpression
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class ClauseBuilder(private val clauses: List<Clause>) {
    fun select(expression: Expression, vararg expressions: Expression): From = From(addClause(SelectClause(expression, *expressions)))

    fun selectAsterisk(): From = From(addClause(SelectClause(AsteriskExpression())))

    fun selectDistinct(expression: Expression, vararg expressions: Expression): From =
        From(addClause(SelectDistinctClause(expression, *expressions)))

    fun selectRaw(expression: SingleExpression): From = From(addClause(SelectRawClause(expression)))

    fun selectFrom(fromable: Fromable): Where = ClauseBuilder(addClause(SelectClause(AsteriskExpression()))).from(fromable)

    fun from(fromable: Fromable): Where = Where(addClause(FromClause(fromable)))

// TODO DOPE-174
//    fun let(params: ValidType): WhereClause = WhereClause(addClause(Let(params)))

    fun where(condition: TypeExpression<BooleanType>): GroupBy = GroupBy(addClause(WhereClause(condition)))

    fun groupBy(field: Field<out ValidType>, vararg fields: Field<out ValidType>): OrderBy = OrderBy(addClause(GroupByClause(field, *fields)))

    fun orderBy(stringField: Field<StringType>): Limit = Limit(addClause(OrderByClause(stringField)))

    fun orderBy(stringField: Field<StringType>, orderByType: OrderByType): Limit =
        Limit(addClause(OrderByTypeClause(stringField, orderByType)))

    fun limit(numberExpression: TypeExpression<NumberType>): Offset = Offset(addClause(LimitClause(numberExpression)))

    fun offset(numberExpression: TypeExpression<NumberType>): Build = Build(addClause(OffsetClause(numberExpression)))

    private fun addClause(clause: Clause): List<Clause> {
        return clauses + clause
    }
}
