package ch.ergon.dope

import ch.ergon.dope.resolvable.Fromable
import ch.ergon.dope.resolvable.Selectable
import ch.ergon.dope.resolvable.clause.model.FromClause
import ch.ergon.dope.resolvable.clause.model.SelectClause
import ch.ergon.dope.resolvable.clause.model.SelectDistinctClause
import ch.ergon.dope.resolvable.clause.model.SelectRawClause
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.ValidType

interface QueryProvider {
    fun select(expression: Selectable, vararg expressions: Selectable): SelectClause

    fun selectAsterisk(): SelectClause

    fun selectDistinct(expression: Selectable, vararg expressions: Selectable): SelectDistinctClause

    fun <T : ValidType> selectRaw(expression: Expression<T>): SelectRawClause<T>

    fun selectFrom(fromable: Fromable): FromClause<ObjectType>
}
