package ch.ergon.dope.resolvable.fromable

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

fun Bucket.join(bucket: Bucket, onCondition: TypeExpression<BooleanType>) = StandardJoinOperator(this, bucket, onCondition)
fun Bucket.join(bucket: Bucket, onKeys: Field<out ValidType>) =
    StandardJoinOperator(this, bucket, onKeys)

fun Bucket.innerJoin(bucket: Bucket, onCondition: TypeExpression<BooleanType>) = InnerJoinOperator(this, bucket, onCondition)
fun Bucket.innerJoin(bucket: Bucket, onKeys: Field<out ValidType>) =
    InnerJoinOperator(this, bucket, onKeys)

fun Bucket.leftJoin(bucket: Bucket, onCondition: TypeExpression<BooleanType>) = LeftJoinOperator(this, bucket, onCondition)
fun Bucket.leftJoin(bucket: Bucket, onKeys: Field<out ValidType>) =
    LeftJoinOperator(this, bucket, onKeys)

fun Bucket.rightJoin(bucket: Bucket, onCondition: TypeExpression<BooleanType>) = RightJoinOperator(this, bucket, onCondition)
fun Bucket.rightJoin(bucket: Bucket, onKeys: Field<out ValidType>) =
    RightJoinOperator(this, bucket, onKeys)
