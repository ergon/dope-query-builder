package ch.ergon.dope.resolvable.expression.unaliased.type

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

sealed class Parameter<T : ValidType>(
    private val value: Any,
    private val parameterName: String?,
) : TypeExpression<T> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery = when (parameterName) {
        null -> {
            val unnamedParameterCount = "\$${manager.parameterManager.count}"
            DopeQuery(
                queryString = unnamedParameterCount,
                parameters = DopeParameters(positionalParameters = listOf(value)),
            )
        }

        else -> DopeQuery(
            queryString = "\$$parameterName",
            parameters = DopeParameters(namedParameters = mapOf(parameterName to value)),
        )
    }
}

class NumberParameter(value: Number, parameterName: String? = null) : Parameter<NumberType>(value, parameterName)

class StringParameter(value: String, parameterName: String? = null) : Parameter<StringType>(value, parameterName)

class BooleanParameter(value: Boolean, parameterName: String? = null) : Parameter<BooleanType>(value, parameterName)

class ArrayParameter<T : ValidType>(value: Collection<Any>, parameterName: String? = null) : Parameter<ArrayType<T>>(value, parameterName)

class ObjectParameter(value: Map<String, Any>, parameterName: String? = null) : Parameter<ObjectType>(value, parameterName)

fun Number.asParameter(parameterName: String? = null) = NumberParameter(this, parameterName)

fun String.asParameter(parameterName: String? = null) = StringParameter(this, parameterName)

fun Boolean.asParameter(parameterName: String? = null) = BooleanParameter(this, parameterName)

@JvmName("numberCollectionAsParameter")
fun Collection<Number>.asParameter(parameterName: String? = null) = ArrayParameter<NumberType>(this, parameterName)

@JvmName("stringCollectionAsParameter")
fun Collection<String>.asParameter(parameterName: String? = null) = ArrayParameter<StringType>(this, parameterName)

@JvmName("booleanCollectionAsParameter")
fun Collection<Boolean>.asParameter(parameterName: String? = null) = ArrayParameter<BooleanType>(this, parameterName)

@JvmName("objectCollectionAsParameter")
fun Collection<Map<String, Any>>.asParameter(parameterName: String? = null) =
    ArrayParameter<ObjectType>(this, parameterName)

fun Map<String, Any>.asParameter(parameterName: String? = null) = ObjectParameter(this, parameterName)
