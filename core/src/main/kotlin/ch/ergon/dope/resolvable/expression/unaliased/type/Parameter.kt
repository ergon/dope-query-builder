package ch.ergon.dope.resolvable.expression.unaliased.type

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.ValidType

class Parameter<T : ValidType>(val previousValue: TypeExpression<T>, private var parameterName: String?) : TypeExpression<T> {
    override fun toQueryString(): String = when (parameterName) {
        null -> "\$${ParameterCounter.count}"
        else -> "\$$parameterName"
    }
}

fun <T : ValidType> TypeExpression<T>.asParameter(parameterName: String) = Parameter(this, parameterName)

fun <T : ValidType> TypeExpression<T>.asParameter() = Parameter(this, null)

object ParameterCounter {
    var count: Int = 1
        get() = field++

    fun resetCounter() {
        count = 1
    }
}
