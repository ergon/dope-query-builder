package ch.ergon.dope.resolvable.expression.type.relational

import ch.ergon.dope.resolvable.expression.type.IField
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

data class IsMissingExpression(
    val field: IField<out ValidType>,
) : TypeExpression<BooleanType>

fun IField<out ValidType>.isMissing() = IsMissingExpression(this)

data class IsNotMissingExpression(
    val field: IField<out ValidType>,
) : TypeExpression<BooleanType>

fun IField<out ValidType>.isNotMissing() = IsNotMissingExpression(this)
