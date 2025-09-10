package ch.ergon.dope.resolvable.expression.type

import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

sealed class Parameter<T : ValidType>(
    open val value: Any,
    open val parameterName: String?,
) : TypeExpression<T>

data class NumberParameter(
    override val value: Number,
    override val parameterName: String? = null,
) : Parameter<NumberType>(value, parameterName)

data class StringParameter(
    override val value: String,
    override val parameterName: String? = null,
) : Parameter<StringType>(value, parameterName)

data class BooleanParameter(
    override val value: Boolean,
    override val parameterName: String? = null,
) : Parameter<BooleanType>(value, parameterName)

data class ArrayParameter<T : ValidType>(
    override val value: Collection<Any>,
    override val parameterName: String? = null,
) : Parameter<ArrayType<T>>(value, parameterName)

data class ObjectParameter(
    override val value: Map<String, Any>,
    override val parameterName: String? = null,
) : Parameter<ObjectType>(value, parameterName)

fun Number.asParameter(parameterName: String? = null) = NumberParameter(this, parameterName)

fun String.asParameter(parameterName: String? = null) = StringParameter(this, parameterName)

fun Boolean.asParameter(parameterName: String? = null) = BooleanParameter(this, parameterName)

fun Map<String, Any>.asParameter(parameterName: String? = null) = ObjectParameter(this, parameterName)

@JvmName("numberCollectionAsParameter")
fun Collection<Number>.asParameter(parameterName: String? = null) = ArrayParameter<NumberType>(this, parameterName)

@JvmName("stringCollectionAsParameter")
fun Collection<String>.asParameter(parameterName: String? = null) = ArrayParameter<StringType>(this, parameterName)

@JvmName("booleanCollectionAsParameter")
fun Collection<Boolean>.asParameter(parameterName: String? = null) = ArrayParameter<BooleanType>(this, parameterName)

@JvmName("objectCollectionAsParameter")
fun Collection<Map<String, Any>>.asParameter(parameterName: String? = null) =
    ArrayParameter<ObjectType>(this, parameterName)
