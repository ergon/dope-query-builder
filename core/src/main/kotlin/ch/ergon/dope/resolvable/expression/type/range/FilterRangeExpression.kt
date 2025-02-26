package ch.ergon.dope.resolvable.expression.type.range

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.collection.Iterator
import ch.ergon.dope.resolvable.expression.type.range.MembershipType.IN
import ch.ergon.dope.resolvable.expression.type.range.MembershipType.WITHIN
import ch.ergon.dope.resolvable.expression.type.range.TransformationType.ARRAY
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class FilterRangeExpression<T : ValidType>(
    override val membershipType: MembershipType,
    override val range: TypeExpression<ArrayType<T>>,
    override val iteratorName: String? = null,
    override val condition: (Iterator<T>) -> TypeExpression<BooleanType>,
) : TypeExpression<ArrayType<T>>, RangeExpression<T, T>() {
    override val withAttributeKeys: ((Iterator<T>) -> TypeExpression<StringType>)? = null
    override val transformationType: TransformationType = ARRAY
    override val transformation: (Iterator<T>) -> TypeExpression<T> = { it }

    fun <U : ValidType> map(
        transformation: (Iterator<T>) -> TypeExpression<U>,
    ) = ArrayRangeExpression(
        membershipType = membershipType,
        range = range,
        iteratorName = iteratorName,
        transformation = transformation,
        condition = condition,
    )

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

fun <T : ValidType> TypeExpression<ArrayType<T>>.filter(
    iteratorName: String? = null,
    condition: (Iterator<T>) -> TypeExpression<BooleanType>,
) = FilterRangeExpression(IN, range = this, iteratorName, condition)

fun TypeExpression<ArrayType<ValidType>>.filterUnnested(
    iteratorName: String? = null,
    condition: (Iterator<out ValidType>) -> TypeExpression<BooleanType>,
) = FilterRangeExpression(WITHIN, range = this, iteratorName, condition)

fun <T : ValidType> Collection<TypeExpression<T>>.filter(
    iteratorName: String? = null,
    condition: (Iterator<T>) -> TypeExpression<BooleanType>,
) = toDopeType().filter(iteratorName, condition)

fun Collection<TypeExpression<ValidType>>.filterUnnested(
    iteratorName: String? = null,
    condition: (Iterator<out ValidType>) -> TypeExpression<BooleanType>,
) = toDopeType().filterUnnested(iteratorName, condition)

fun <T : ValidType> ISelectOffsetClause<T>.filter(
    iteratorName: String? = null,
    condition: (Iterator<T>) -> TypeExpression<BooleanType>,
) = asExpression().filter(iteratorName, condition)

fun ISelectOffsetClause<ValidType>.filterUnnested(
    iteratorName: String? = null,
    condition: (Iterator<out ValidType>) -> TypeExpression<BooleanType>,
) = asExpression().filterUnnested(iteratorName, condition)
