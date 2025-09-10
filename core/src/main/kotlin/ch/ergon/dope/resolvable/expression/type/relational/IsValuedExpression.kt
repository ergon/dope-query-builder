package ch.ergon.dope.resolvable.expression.type.relational

import ch.ergon.dope.resolvable.expression.type.IField
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

data class IsValuedExpression(
    val field: IField<out ValidType>,
) : TypeExpression<BooleanType>

fun IField<out ValidType>.isValued() = IsValuedExpression(this)

data class IsNotValuedExpression(
    val field: IField<out ValidType>,
) : TypeExpression<BooleanType>

fun IField<out ValidType>.isNotValued() = IsNotValuedExpression(this)
