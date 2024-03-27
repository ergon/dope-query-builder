package ch.ergon.dope.resolvable.expression.unaliased.type

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.ValidType

class ValuedParameter<T : ValidType>(val value: TypeExpression<T>, private val parameterName: String?) : TypeExpression<T> {
    override fun toQueryString(): String = when (parameterName) {
        null -> "\$${ParameterCounter.count}"
        else -> "\$$parameterName"
    }
}

fun <T : ValidType> TypeExpression<T>.asParameter(parameterName: String) = ValuedParameter(this, parameterName)

fun String.asParameter(parameterName: String) = ValuedParameter(this.toStringType(), parameterName)

fun <T : ValidType> TypeExpression<T>.asParameter() = ValuedParameter(this, null)

fun String.asParameter() = ValuedParameter(this.toStringType(), null)

private object ParameterCounter {
    var count: Int = 1
        get() = field++
}

fun resetCounter() {
    ParameterCounter.count = 1
}
