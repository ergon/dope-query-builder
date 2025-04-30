package ch.ergon.dope.resolvable.expression.type.function.`object`

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType

class ObjectRenameExpression(
    objectExpression: TypeExpression<ObjectType>,
    oldFieldName: TypeExpression<StringType>,
    newFieldName: TypeExpression<StringType>,
) : FunctionExpression<ObjectType>("OBJECT_RENAME", objectExpression, oldFieldName, newFieldName)

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
