package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.resolvable.Selectable
import ch.ergon.dope.resolvable.clause.ISelectClause
import ch.ergon.dope.resolvable.clause.ISelectWithClause
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.ValidType

data class SelectClause(
    val expression: Selectable,
    val expressions: List<Selectable> = emptyList(),
    val parentClause: ISelectWithClause? = null,
) : ISelectClause<ObjectType>

data class SelectRawClause<T : ValidType>(
    val expression: Expression<T>,
    val parentClause: ISelectWithClause? = null,
) : ISelectClause<T>

data class SelectDistinctClause(
    val expression: Selectable,
    val expressions: List<Selectable> = emptyList(),
    val parentClause: ISelectWithClause? = null,
) : ISelectClause<ObjectType>
