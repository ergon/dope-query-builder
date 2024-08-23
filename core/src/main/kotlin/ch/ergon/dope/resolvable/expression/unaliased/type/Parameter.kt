package ch.ergon.dope.resolvable.expression.unaliased.type

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class ParameterManager {
    var count: Int = 1
        get() = field++

    fun resetCounter() {
        count = 1
    }
}

sealed class Parameter<T : ValidType>(
    private val value: Any,
    private val parameterName: String?,
) : TypeExpression<T> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery = when (parameterName) {
        null -> {
            // val unnamedParameterCount = "\$${manager.parameterManager.count}"
            val unnamedParameterCount = "\$${manager.parameterManager.count}"
            DopeQuery(
                queryString = unnamedParameterCount,
                parameters = mapOf(unnamedParameterCount to value),
                manager = manager,
            )
        }

        else -> DopeQuery(
            queryString = "\$$parameterName",
            parameters = mapOf(parameterName to value),
            manager = manager,
        )
    }
}

class NumberParameter(value: Number, parameterName: String? = null) : Parameter<NumberType>(value, parameterName)

class StringParameter(value: String, parameterName: String? = null) : Parameter<StringType>(value, parameterName)

class BooleanParameter(value: Boolean, parameterName: String? = null) : Parameter<BooleanType>(value, parameterName)

class ArrayParameter<T : ValidType>(value: Collection<Any>, parameterName: String? = null) : Parameter<ArrayType<T>>(value, parameterName)

fun Number.asParameter(parameterName: String? = null) = NumberParameter(this, parameterName)

fun String.asParameter(parameterName: String? = null) = StringParameter(this, parameterName)

fun Boolean.asParameter(parameterName: String? = null) = BooleanParameter(this, parameterName)

@JvmName("numberCollectionAsParameter")
fun Collection<Number>.asParameter(parameterName: String? = null) = ArrayParameter<NumberType>(this, parameterName)

@JvmName("stringCollectionAsParameter")
fun Collection<String>.asParameter(parameterName: String? = null) = ArrayParameter<StringType>(this, parameterName)

@JvmName("booleanParameterAsParameter")
fun Collection<Boolean>.asParameter(parameterName: String? = null) = ArrayParameter<BooleanType>(this, parameterName)
