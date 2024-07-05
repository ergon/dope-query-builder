package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.resolvable.expression.unaliased.type.relational.ExistsExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.exists
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMList

@JvmName("existsNumberArray")
fun exists(field: CMList<out Number>): ExistsExpression<NumberType> = exists(field.toDopeType())

@JvmName("existsStringArray")
fun exists(field: CMList<String>): ExistsExpression<StringType> = exists(field.toDopeType())

@JvmName("existsExistsArray")
fun exists(field: CMList<Boolean>): ExistsExpression<BooleanType> = exists(field.toDopeType())
