package ch.ergon.dope

import ch.ergon.dope.couchbase.resolvable.expression.type.asParameter
import com.schwarz.crystalapi.ITypeConverter

fun <Convertable : Any, JsonNumberType : Number> Convertable.asParameter(
    converter: ITypeConverter<Convertable, JsonNumberType>,
    parameterName: String? = null,
) = requireValidConvertable(converter.write(this), Number::class).asParameter(parameterName)

fun <Convertable : Any> Convertable.asParameter(
    converter: ITypeConverter<Convertable, String>,
    parameterName: String? = null,
) = requireValidConvertable(converter.write(this), String::class).asParameter(parameterName)

fun <Convertable : Any> Convertable.asParameter(
    converter: ITypeConverter<Convertable, Boolean>,
    parameterName: String? = null,
) = requireValidConvertable(converter.write(this), Boolean::class).asParameter(parameterName)
