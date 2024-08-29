package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isLike
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isNotLike
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType
import com.schwarz.crystalapi.schema.CMJsonField

fun Field<out ValidType>.isLike(right: CMJsonField<String>) = isLike(right.toDopeType())

@JvmName("isLikeNumber")
fun CMJsonField<out Number>.isLike(right: String) = toDopeType().isLike(right.toDopeType())

@JvmName("isLikeString")
fun CMJsonField<String>.isLike(right: String) = toDopeType().isLike(right.toDopeType())

@JvmName("isLikeString")
fun CMJsonField<String>.isLike(right: TypeExpression<StringType>) = toDopeType().isLike(right)

@JvmName("isLikeBoolean")
fun CMJsonField<Boolean>.isLike(right: String) = toDopeType().isLike(right.toDopeType())

fun Field<out ValidType>.isNotLike(right: CMJsonField<String>) = isNotLike(right.toDopeType())

@JvmName("isNotLikeNumber")
fun CMJsonField<out Number>.isNotLike(right: String) = toDopeType().isNotLike(right.toDopeType())

@JvmName("isNotLikeString")
fun CMJsonField<String>.isNotLike(right: String) = toDopeType().isNotLike(right.toDopeType())

@JvmName("isNotLikeString")
fun CMJsonField<String>.isNotLike(right: TypeExpression<StringType>) = toDopeType().isNotLike(right)

@JvmName("isNotLikeBoolean")
fun CMJsonField<Boolean>.isNotLike(right: String) = toDopeType().isNotLike(right.toDopeType())
