package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.NotLikeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isNotLike
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType
import com.schwarz.crystalapi.schema.CMField

fun Field<out ValidType>.isNotLike(right: CMField<String>): NotLikeExpression = isNotLike(right.toDopeType())

@JvmName("isNotLikeNumber")
fun CMField<out Number>.isNotLike(right: String): NotLikeExpression = toDopeType().isNotLike(right.toDopeType())

@JvmName("isNotLikeString")
fun CMField<String>.isNotLike(right: String): NotLikeExpression = toDopeType().isNotLike(right.toDopeType())

@JvmName("isNotLikeString")
fun CMField<String>.isNotLike(right: TypeExpression<StringType>): NotLikeExpression = toDopeType().isNotLike(right)

@JvmName("isNotLikeBoolean")
fun CMField<Boolean>.isNotLike(right: String): NotLikeExpression = toDopeType().isNotLike(right.toDopeType())
