package ch.ergon.dope.resolvable.expression.unaliased.type

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

object ParameterManager {
    var count: Int = 1
        get() = field++

    fun resetCounter() {
        count = 1
    }
}

sealed class Parameter<T : ValidType>(
    val value: Any,
    val parameterName: String? = null,
) : TypeExpression<T> {
    override fun toDopeQuery(): DopeQuery = when (parameterName) {
        null -> {
            val unnamedParameterCount = "\$${ParameterManager.count}"
            DopeQuery(
                queryString = unnamedParameterCount,
                parameters = mapOf(unnamedParameterCount to value),
            )
        }
        else -> DopeQuery(
            queryString = "\$$parameterName",
            parameters = mapOf(parameterName to value),
        )
    }
}

class NumberParameter(value: Number, parameterName: String?) : Parameter<NumberType>(value, parameterName)

class StringParameter(value: String, parameterName: String?) : Parameter<StringType>(value, parameterName)

class BooleanParameter(value: Boolean, parameterName: String?) : Parameter<BooleanType>(value, parameterName)

class ArrayParameter<T : ValidType>(value: Collection<Any>, parameterName: String?) : Parameter<ArrayType<T>>(value, parameterName)

fun Number.asParameter(parameterName: String? = null) = NumberParameter(this, parameterName)

fun String.asParameter(parameterName: String? = null) = StringParameter(this, parameterName)

fun Boolean.asParameter(parameterName: String? = null) = BooleanParameter(this, parameterName)

@JvmName("numberCollectionAsParameter")
fun Collection<Number>.asParameter(parameterName: String? = null) = ArrayParameter<NumberType>(this, parameterName)

@JvmName("stringCollectionAsParameter")
fun Collection<String>.asParameter(parameterName: String? = null) = ArrayParameter<StringType>(this, parameterName)

@JvmName("booleanParameterAsParameter")
fun Collection<Boolean>.asParameter(parameterName: String? = null) = ArrayParameter<BooleanType>(this, parameterName)
