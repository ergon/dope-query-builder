package ch.ergon.dope.resolvable.expression.unaliased.type

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.ValidType

class Parameter<T : ValidType>(
    val value: TypeExpression<T>,
    private val parameterName: String = "${ParameterCounter.count}",
) : TypeExpression<T> {
    override fun toQueryString(): String = "\$$parameterName"
}

fun <T : ValidType> TypeExpression<T>.asParameter(parameterName: String) = Parameter(this, parameterName)

fun String.asParameter(parameterName: String) = Parameter(this.toStringType(), parameterName)

fun <T : ValidType> TypeExpression<T>.asParameter() = Parameter(this)

fun String.asParameter() = Parameter(this.toStringType())

private object ParameterCounter {
    var count: Int = 1
        get() = field++
}

fun resetCounter() {
    ParameterCounter.count = 1
}
