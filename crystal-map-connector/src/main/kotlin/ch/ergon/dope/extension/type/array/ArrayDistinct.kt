package ch.ergon.dope.extension.type.array

import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.ArrayDistinctExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayDistinct
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonList

@JvmName("numberArrayDistinct")
fun arrayDistinct(array: CMJsonList<Number>): ArrayDistinctExpression<NumberType> =
    arrayDistinct(array.toDopeType())

@JvmName("stringArrayDistinct")
fun arrayDistinct(array: CMJsonList<String>): ArrayDistinctExpression<StringType> =
    arrayDistinct(array.toDopeType())

@JvmName("booleanArrayDistinct")
fun arrayDistinct(array: CMJsonList<Boolean>): ArrayDistinctExpression<BooleanType> =
    arrayDistinct(array.toDopeType())
