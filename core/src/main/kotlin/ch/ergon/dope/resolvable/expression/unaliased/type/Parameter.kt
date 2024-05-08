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

class Parameter<T : ValidType> : TypeExpression<T> {
    val value: Any
    private val parameterName: String?

    constructor(number: Number, parameterName: String?) {
        this.value = number
        this.parameterName = parameterName
    }

    constructor(string: String, parameterName: String?) {
        this.value = string
        this.parameterName = parameterName
    }

    constructor(boolean: Boolean, parameterName: String?) {
        this.value = boolean
        this.parameterName = parameterName
    }

    constructor(collection: Collection<Any>, parameterName: String?) {
        this.value = collection
        this.parameterName = parameterName
    }

    override fun toQuery(): DopeQuery = when (parameterName) {
        null -> {
            val count = "\$${ParameterManager.count}"
            DopeQuery(
                queryString = count,
                parameters = mapOf(count to value),
            )
        }

        else -> DopeQuery(
            queryString = "\$$parameterName",
            parameters = mapOf(parameterName to value),
        )
    }
}

fun Number.asParameter(parameterName: String? = null) = Parameter<NumberType>(this, parameterName)
fun String.asParameter(parameterName: String? = null) = Parameter<StringType>(this, parameterName)
fun Boolean.asParameter(parameterName: String? = null) = Parameter<BooleanType>(this, parameterName)

@JvmName("numberCollectionAsParameter")
fun Collection<Number>.asParameter(parameterName: String? = null) = Parameter<ArrayType<NumberType>>(this, parameterName)

@JvmName("stringCollectionAsParameter")
fun Collection<String>.asParameter(parameterName: String? = null) = Parameter<ArrayType<StringType>>(this, parameterName)

@JvmName("booleanParameterAsParameter")
fun Collection<Boolean>.asParameter(parameterName: String? = null) = Parameter<ArrayType<BooleanType>>(this, parameterName)
