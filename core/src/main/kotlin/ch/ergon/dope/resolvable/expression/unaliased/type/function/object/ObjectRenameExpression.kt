package ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType

class ObjectRenameExpression(
    objectExpression: TypeExpression<ObjectType>,
    oldField: TypeExpression<StringType>,
    newField: TypeExpression<StringType>,
) : FunctionExpression<ObjectType>("OBJECT_RENAME", objectExpression, oldField, newField)

fun TypeExpression<ObjectType>.renameAttribute(
    oldField: TypeExpression<StringType>,
    newField: TypeExpression<StringType>,
) = ObjectRenameExpression(this, oldField, newField)

fun TypeExpression<ObjectType>.renameAttribute(
    oldField: String,
    newField: String,
) = renameAttribute(oldField.toDopeType(), newField.toDopeType())
