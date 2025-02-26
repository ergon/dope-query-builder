package ch.ergon.dope.resolvable.expression.type.range

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.collection.Iterator
import ch.ergon.dope.resolvable.expression.type.range.MembershipType.IN
import ch.ergon.dope.resolvable.expression.type.range.MembershipType.WITHIN
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class FilterRangeIndexedExpression<T : ValidType>(
    override val membershipType: MembershipType,
    override val range: TypeExpression<ArrayType<T>>,
    override val indexName: String? = null,
    override val iteratorName: String? = null,
    override val condition: (Iterator<NumberType>, Iterator<T>) -> TypeExpression<BooleanType>,
) : TypeExpression<ArrayType<T>>, RangeIndexedExpression<T, T>() {
    override val withAttributeKeys: ((Iterator<NumberType>, Iterator<T>) -> TypeExpression<StringType>)? = null
    override val transformationType: TransformationType = TransformationType.ARRAY
    override val transformation: (Iterator<NumberType>, Iterator<T>) -> TypeExpression<T> = { _, it -> it }

    fun <U : ValidType> map(
        transformation: (Iterator<NumberType>, Iterator<T>) -> TypeExpression<U>,
    ) = ArrayRangeIndexedExpression(
        membershipType = membershipType,
        range = range,
        indexName = indexName,
        iteratorName = iteratorName,
        transformation = transformation,
        condition = condition,
    )

    fun first() = FirstRangeIndexedExpression(
        membershipType = membershipType,
        range = range,
        indexName = indexName,
        iteratorName = iteratorName,
        transformation = transformation,
        condition = condition,
    )

    fun toObject(withAttributeKeys: (Iterator<NumberType>, Iterator<T>) -> TypeExpression<StringType>) =
        ObjectRangeIndexedExpression(
            membershipType = membershipType,
            range = range,
            indexName = indexName,
            iteratorName = iteratorName,
            transformation = transformation,
            condition = condition,
            withAttributeKeys = withAttributeKeys,
        )
}

fun <T : ValidType> TypeExpression<ArrayType<T>>.filterIndexed(
    indexName: String? = null,
    iteratorName: String? = null,
    condition: (Iterator<NumberType>, Iterator<T>) -> TypeExpression<BooleanType>,
) = FilterRangeIndexedExpression(IN, range = this, indexName, iteratorName, condition)

fun TypeExpression<ArrayType<ValidType>>.filterIndexedUnnested(
    indexName: String? = null,
    iteratorName: String? = null,
    condition: (Iterator<NumberType>, Iterator<out ValidType>) -> TypeExpression<BooleanType>,
) = FilterRangeIndexedExpression(WITHIN, range = this, indexName, iteratorName, condition)

fun <T : ValidType> Collection<TypeExpression<T>>.filterIndexed(
    indexName: String? = null,
    iteratorName: String? = null,
    condition: (Iterator<NumberType>, Iterator<T>) -> TypeExpression<BooleanType>,
) = toDopeType().filterIndexed(indexName, iteratorName, condition)

fun Collection<TypeExpression<ValidType>>.filterIndexedUnnested(
    indexName: String? = null,
    iteratorName: String? = null,
    condition: (Iterator<NumberType>, Iterator<out ValidType>) -> TypeExpression<BooleanType>,
) = toDopeType().filterIndexedUnnested(indexName, iteratorName, condition)

fun <T : ValidType> ISelectOffsetClause<T>.filterIndexed(
    indexName: String? = null,
    iteratorName: String? = null,
    condition: (Iterator<NumberType>, Iterator<T>) -> TypeExpression<BooleanType>,
) = asExpression().filterIndexed(indexName, iteratorName, condition)

fun ISelectOffsetClause<ValidType>.filterIndexedUnnested(
    indexName: String? = null,
    iteratorName: String? = null,
    condition: (Iterator<NumberType>, Iterator<out ValidType>) -> TypeExpression<BooleanType>,
) = asExpression().filterIndexedUnnested(indexName, iteratorName, condition)
