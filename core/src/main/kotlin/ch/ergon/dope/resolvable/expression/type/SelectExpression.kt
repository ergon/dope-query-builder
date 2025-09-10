package ch.ergon.dope.resolvable.expression.type

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

data class SelectExpression<T : ValidType>(val selectClause: ISelectOffsetClause<T>) : TypeExpression<ArrayType<T>>
