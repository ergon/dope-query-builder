package ch.ergon.dope.extension.type.access

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.access.ArrayAccess
import ch.ergon.dope.resolvable.expression.unaliased.type.access.get
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonList
import com.schwarz.crystalapi.schema.CMObjectList
import com.schwarz.crystalapi.schema.Schema

@JvmName("getCMNumberAccess")
fun CMJsonList<Number>.get(index: TypeExpression<NumberType>): ArrayAccess<NumberType> = toDopeType().get(index)

@JvmName("getCMNumberAccess")
fun CMJsonList<Number>.get(index: Int): ArrayAccess<NumberType> = toDopeType().get(index.toDopeType())

@JvmName("getCMStringAccess")
fun CMJsonList<String>.get(index: TypeExpression<NumberType>): ArrayAccess<StringType> = toDopeType().get(index)

@JvmName("getCMStringAccess")
fun CMJsonList<String>.get(index: Int): ArrayAccess<StringType> = toDopeType().get(index.toDopeType())

@JvmName("getCMBooleanAccess")
fun CMJsonList<Boolean>.get(index: TypeExpression<NumberType>): ArrayAccess<BooleanType> = toDopeType().get(index)

@JvmName("getCMBooleanAccess")
fun CMJsonList<Boolean>.get(index: Int): ArrayAccess<BooleanType> = toDopeType().get(index.toDopeType())

fun <S : Schema> CMObjectList<S>.get(index: TypeExpression<NumberType>): ArrayAccess<ObjectType> = toDopeType().get(index)

fun <S : Schema> CMObjectList<S>.get(index: Int): ArrayAccess<ObjectType> = toDopeType().get(index.toDopeType())
