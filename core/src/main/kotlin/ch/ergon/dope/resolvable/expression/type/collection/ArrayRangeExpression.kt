package ch.ergon.dope.resolvable.expression.type.collection

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.collection.MembershipType.IN
import ch.ergon.dope.resolvable.expression.type.collection.MembershipType.WITHIN
import ch.ergon.dope.resolvable.expression.type.collection.TransformationType.ARRAY
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class ArrayRangeExpression<T : ValidType, U : ValidType>(
    override val membershipType: MembershipType,
    override val range: TypeExpression<ArrayType<T>>,
    override val iteratorName: String? = null,
    override val transformation: (Iterator<T>) -> TypeExpression<U>,
    override val condition: ((Iterator<T>) -> TypeExpression<BooleanType>)? = null,
) : TypeExpression<ArrayType<U>>, RangeExpression<T, U>() {
    override val transformationType: TransformationType = ARRAY
    override val withAttributeKeys: ((Iterator<T>) -> TypeExpression<StringType>)? = null

    fun first() = FirstRangeExpression(
        membershipType = membershipType,
        range = range,
        iteratorName = iteratorName,
        transformation = transformation,
        condition = condition,
    )

    fun toObject(withAttributeKeys: (Iterator<T>) -> TypeExpression<StringType>) =
        ObjectRangeExpression(
            membershipType = membershipType,
            range = range,
            iteratorName = iteratorName,
            transformation = transformation,
            condition = condition,
            withAttributeKeys = withAttributeKeys,
        )
}

fun <T : ValidType, U : ValidType> TypeExpression<ArrayType<T>>.map(
    iteratorName: String? = null,
    transformation: (Iterator<T>) -> TypeExpression<U>,
) = ArrayRangeExpression(membershipType = IN, range = this, iteratorName = iteratorName, transformation = transformation)

fun <T : ValidType> TypeExpression<ArrayType<ValidType>>.mapUnnested(
    iteratorName: String? = null,
    transformation: (Iterator<out ValidType>) -> TypeExpression<T>,
) = ArrayRangeExpression(membershipType = WITHIN, range = this, iteratorName = iteratorName, transformation = transformation)

fun <T : ValidType, U : ValidType> Collection<TypeExpression<T>>.map(
    iteratorName: String? = null,
    transformation: (Iterator<T>) -> TypeExpression<U>,
) = toDopeType().map(iteratorName, transformation)

fun <T : ValidType> Collection<TypeExpression<ValidType>>.mapUnnested(
    iteratorName: String? = null,
    transformation: (Iterator<out ValidType>) -> TypeExpression<T>,
) = toDopeType().mapUnnested(iteratorName, transformation)

fun <T : ValidType, U : ValidType> ISelectOffsetClause<T>.map(
    iteratorName: String? = null,
    transformation: (Iterator<T>) -> TypeExpression<U>,
) = asExpression().map(iteratorName, transformation)

fun <T : ValidType> ISelectOffsetClause<ValidType>.mapUnnested(
    iteratorName: String? = null,
    transformation: (Iterator<out ValidType>) -> TypeExpression<T>,
) = asExpression().mapUnnested(iteratorName, transformation)
