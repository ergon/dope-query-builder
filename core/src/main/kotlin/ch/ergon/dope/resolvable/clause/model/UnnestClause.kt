package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.resolvable.clause.ISelectFromClause
import ch.ergon.dope.resolvable.expression.type.AliasedTypeExpression
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

data class UnnestClause<T : ValidType, U : ValidType>(
    val arrayTypeField: Field<ArrayType<T>>,
    val parentClause: ISelectFromClause<U>,
) : ISelectFromClause<U>

data class AliasedUnnestClause<T : ValidType, U : ValidType>(
    val aliasedTypeExpression: AliasedTypeExpression<ArrayType<T>>,
    val parentClause: ISelectFromClause<U>,
) : ISelectFromClause<U>
