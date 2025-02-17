package ch.ergon.dope.extension.expression.type.relational

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.relational.isLike
import ch.ergon.dope.resolvable.expression.type.relational.isNotLike
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

fun TypeExpression<StringType>.isLike(right: CMJsonField<String>) = isLike(right.toDopeType())

fun String.isLike(right: CMJsonField<String>) = toDopeType().isLike(right.toDopeType())

fun CMJsonField<String>.isLike(right: String) = toDopeType().isLike(right.toDopeType())

fun CMJsonField<String>.isLike(right: TypeExpression<StringType>) = toDopeType().isLike(right)

fun TypeExpression<StringType>.isNotLike(right: CMJsonField<String>) = isNotLike(right.toDopeType())

fun String.isNotLike(right: CMJsonField<String>) = toDopeType().isNotLike(right.toDopeType())

fun CMJsonField<String>.isNotLike(right: String) = toDopeType().isNotLike(right.toDopeType())

fun CMJsonField<String>.isNotLike(right: TypeExpression<StringType>) = toDopeType().isNotLike(right)
