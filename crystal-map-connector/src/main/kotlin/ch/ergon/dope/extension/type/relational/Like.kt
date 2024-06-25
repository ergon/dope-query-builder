package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.LikeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isLike
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeField
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType
import com.schwarz.crystalapi.schema.CMField

fun Field<out ValidType>.isLike(right: CMField<String>): LikeExpression = isLike(right.toDopeField())

@JvmName("isLikeNumber")
fun CMField<out Number>.isLike(right: String): LikeExpression = toDopeField().isLike(right.toDopeType())

@JvmName("isLikeString")
fun CMField<String>.isLike(right: String): LikeExpression = toDopeField().isLike(right.toDopeType())

@JvmName("isLikeString")
fun CMField<String>.isLike(right: TypeExpression<StringType>): LikeExpression = toDopeField().isLike(right)

@JvmName("isLikeBoolean")
fun CMField<Boolean>.isLike(right: String): LikeExpression = toDopeField().isLike(right.toDopeType())
