package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ObjectType

data class UnpackExpression(
    val objectArray: TypeExpression<ArrayType<ObjectType>>,
) : TypeExpression<ObjectType>

fun TypeExpression<ArrayType<ObjectType>>.unpack() = UnpackExpression(this)

fun Collection<TypeExpression<ObjectType>>.unpack() = toDopeType().unpack()

fun ISelectOffsetClause<ObjectType>.unpack() = asExpression().unpack()
