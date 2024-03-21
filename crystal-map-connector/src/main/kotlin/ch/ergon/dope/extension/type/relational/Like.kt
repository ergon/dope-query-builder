package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.asField
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.LikeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isLike
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType
import com.schwarz.crystalapi.schema.CMField

fun Field<out ValidType>.isLike(right: CMField<String>): LikeExpression = isLike(right.asField())

@JvmName("isLikeNumber")
fun CMField<Number>.isLike(right: String): LikeExpression = asField().isLike(right.toStringType())

@JvmName("isLikeString")
fun CMField<String>.isLike(right: String): LikeExpression = asField().isLike(right.toStringType())

@JvmName("isLikeString")
fun CMField<String>.isLike(right: TypeExpression<StringType>): LikeExpression = asField().isLike(right)

@JvmName("isLikeBoolean")
fun CMField<Boolean>.isLike(right: String): LikeExpression = asField().isLike(right.toStringType())
