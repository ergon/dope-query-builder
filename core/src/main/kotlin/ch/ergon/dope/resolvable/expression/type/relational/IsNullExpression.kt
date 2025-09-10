package ch.ergon.dope.resolvable.expression.type.relational

import ch.ergon.dope.resolvable.expression.type.IField
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

data class IsNullExpression(
    val field: IField<out ValidType>,
) : TypeExpression<BooleanType>

fun IField<out ValidType>.isNull() = IsNullExpression(this)

data class IsNotNullExpression(
    val field: IField<out ValidType>,
) : TypeExpression<BooleanType>

fun IField<out ValidType>.isNotNull() = IsNotNullExpression(this)
