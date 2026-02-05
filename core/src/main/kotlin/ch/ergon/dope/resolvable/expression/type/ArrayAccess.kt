package ch.ergon.dope.resolvable.expression.type

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.SingleExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

data class ArrayAccess<T : ValidType>(
    val array: SingleExpression<ArrayType<T>>,
    val index: TypeExpression<NumberType>,
) : TypeExpression<T>

fun <T : ValidType> SingleExpression<ArrayType<T>>.get(index: TypeExpression<NumberType>) = ArrayAccess(this, index)

fun <T : ValidType> SingleExpression<ArrayType<T>>.get(index: Number) = get(index.toDopeType())

fun <T : ValidType> ISelectOffsetClause<T>.get(index: TypeExpression<NumberType>) = asExpression().get(index)

fun <T : ValidType> ISelectOffsetClause<T>.get(index: Number) = asExpression().get(index.toDopeType())
