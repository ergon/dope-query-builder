package ch.ergon.dope.extension.expression.single.type.relational

import ch.ergon.dope.resolvable.expression.single.type.collection.ExistsExpression
import ch.ergon.dope.resolvable.expression.single.type.collection.exists
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonList

@JvmName("existsNumberArray")
fun exists(field: CMJsonList<out Number>): ExistsExpression<NumberType> = exists(field.toDopeType())

@JvmName("existsStringArray")
fun exists(field: CMJsonList<String>): ExistsExpression<StringType> = exists(field.toDopeType())

@JvmName("existsExistsArray")
fun exists(field: CMJsonList<Boolean>): ExistsExpression<BooleanType> = exists(field.toDopeType())
