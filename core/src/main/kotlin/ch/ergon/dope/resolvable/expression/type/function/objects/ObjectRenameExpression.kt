package ch.ergon.dope.resolvable.expression.type.function.objects

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType

data class ObjectRenameExpression(
    val objectExpression: TypeExpression<ObjectType>,
    val oldFieldName: TypeExpression<StringType>,
    val newFieldName: TypeExpression<StringType>,
) : FunctionExpression<ObjectType>("OBJECT_RENAME", listOf(objectExpression, oldFieldName, newFieldName))

fun TypeExpression<ObjectType>.renameAttribute(
    oldFieldName: TypeExpression<StringType>,
    newFieldName: TypeExpression<StringType>,
) = ObjectRenameExpression(this, oldFieldName, newFieldName)

fun TypeExpression<ObjectType>.renameAttribute(
    oldFieldName: String,
    newFieldName: String,
) = renameAttribute(oldFieldName.toDopeType(), newFieldName.toDopeType())

fun TypeExpression<ObjectType>.renameAttribute(
    oldFieldName: TypeExpression<StringType>,
    newFieldName: String,
) = renameAttribute(oldFieldName, newFieldName.toDopeType())

fun TypeExpression<ObjectType>.renameAttribute(
    oldFieldName: String,
    newFieldName: TypeExpression<StringType>,
) = renameAttribute(oldFieldName.toDopeType(), newFieldName)
