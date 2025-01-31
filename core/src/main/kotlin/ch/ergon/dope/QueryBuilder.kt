package ch.ergon.dope

import ch.ergon.dope.resolvable.clause.model.DeleteClause
import ch.ergon.dope.resolvable.clause.model.SelectClause
import ch.ergon.dope.resolvable.clause.model.SelectDistinctClause
import ch.ergon.dope.resolvable.clause.model.SelectRawClause
import ch.ergon.dope.resolvable.clause.model.UpdateClause
import ch.ergon.dope.resolvable.expression.Asterisk
import ch.ergon.dope.resolvable.fromable.Deletable
import ch.ergon.dope.resolvable.fromable.Fromable
import ch.ergon.dope.resolvable.fromable.RawSelectable
import ch.ergon.dope.resolvable.fromable.Selectable
import ch.ergon.dope.resolvable.fromable.Updatable
import ch.ergon.dope.validtype.ValidType

class QueryBuilder {
    fun select(expression: Selectable, vararg expressions: Selectable) = SelectClause(expression, *expressions)

    fun selectAsterisk() = SelectClause(Asterisk())

    fun selectDistinct(expression: Selectable, vararg expressions: Selectable) = SelectDistinctClause(expression, *expressions)

    fun <T : ValidType> selectRaw(expression: RawSelectable<T>) = SelectRawClause(expression)

    fun selectFrom(fromable: Fromable) = SelectClause(Asterisk()).from(fromable)

    fun deleteFrom(deletable: Deletable) = DeleteClause(deletable)

    fun update(updatable: Updatable) = UpdateClause(updatable)
}
