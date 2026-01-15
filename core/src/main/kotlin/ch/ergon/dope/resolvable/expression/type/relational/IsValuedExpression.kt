package ch.ergon.dope.resolvable.expression.type.relational

import ch.ergon.dope.resolvable.expression.operator.PostfixOperator
import ch.ergon.dope.resolvable.expression.type.IField
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

data class IsValuedExpression(
    val field: IField<out ValidType>,
) : PostfixOperator<BooleanType>(field)

fun IField<out ValidType>.isValued() = IsValuedExpression(this)

data class IsNotValuedExpression(
    val field: IField<out ValidType>,
) : PostfixOperator<BooleanType>(field)

fun IField<out ValidType>.isNotValued() = IsNotValuedExpression(this)
