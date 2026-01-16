package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.operator.FunctionOperator
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

data class ArrayIfNullExpression<T : ValidType>(
    val array: TypeExpression<ArrayType<T>>,
) : FunctionOperator<T>

fun <T : ValidType> TypeExpression<ArrayType<T>>.ifNull() = ArrayIfNullExpression(this)

fun <T : ValidType> ISelectOffsetClause<T>.ifNull() = asExpression().ifNull()
