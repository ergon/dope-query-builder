package ch.ergon.dope.resolvable.expression.unaliased.type.collection

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.MembershipType.IN
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.MembershipType.WITHIN
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.TransformationType.ARRAY
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class ArrayForRangeExpression<T : ValidType, U : ValidType>(
    private val range: TypeExpression<ArrayType<T>>,
    private val iteratorName: String? = null,
    private val indexName: String? = null,
    private val transformation: (Iterator<T>, Iterator<NumberType>) -> TypeExpression<U>,
    private val condition: ((Iterator<T>, Iterator<NumberType>) -> TypeExpression<BooleanType>)? = null,
) : TypeExpression<ArrayType<U>>, ForRangeExpression<T, U>(
    transformationType = ARRAY,
    membershipType = IN,
    range = range,
    iteratorName = iteratorName,
    indexName = indexName,
    withAttributeKeys = null,
    transformation = transformation,
    condition = condition,
) {
    fun first() = FirstForRangeExpression(
        range = range,
        iteratorName = iteratorName,
        indexName = indexName,
        transformation = transformation,
        condition = condition,
    )

    fun toObject(attributeKeys: (Iterator<T>, Iterator<NumberType>) -> TypeExpression<StringType>) =
        ObjectForRangeExpression(
            membershipType = IN,
            range = range,
            iteratorName = iteratorName,
            indexName = indexName,
            transformation = transformation,
            condition = condition,
            withAttributeKeys = attributeKeys,
        )
}

fun <T : ValidType, U : ValidType> TypeExpression<ArrayType<T>>.mapIndexed(
    iteratorName: String? = null,
    indexName: String? = null,
    transformation: (Iterator<T>, Iterator<NumberType>) -> TypeExpression<U>,
) = ArrayForRangeExpression(
    range = this,
    iteratorName = iteratorName,
    indexName = indexName,
    transformation = transformation,
)

class ArrayForUnnestedRangeExpression<T : ValidType, U : ValidType>(
    private val range: TypeExpression<ArrayType<T>>,
    private val iteratorName: String? = null,
    private val indexName: String? = null,
    private val transformation: (Iterator<T>, Iterator<NumberType>) -> TypeExpression<U>,
    private val condition: ((Iterator<T>, Iterator<NumberType>) -> TypeExpression<BooleanType>)? = null,
) : TypeExpression<ArrayType<ValidType>>, ForRangeExpression<T, U>(
    transformationType = ARRAY,
    membershipType = WITHIN,
    range = range,
    iteratorName = iteratorName,
    indexName = indexName,
    withAttributeKeys = null,
    transformation = transformation,
    condition = condition,
) {
    fun first() = FirstForUnnestedRangeExpression(
        range = range,
        iteratorName = iteratorName,
        indexName = indexName,
        transformation = transformation,
        condition = condition,
    )

    fun toObject(attributeKeys: (Iterator<T>, Iterator<NumberType>) -> TypeExpression<StringType>) =
        ObjectForRangeExpression(
            membershipType = WITHIN,
            range = range,
            iteratorName = iteratorName,
            indexName = indexName,
            transformation = transformation,
            condition = condition,
            withAttributeKeys = attributeKeys,
        )
}

fun <T : ValidType, U : ValidType> TypeExpression<ArrayType<T>>.mapIndexedUnnested(
    iteratorName: String? = null,
    indexName: String? = null,
    transformation: (Iterator<T>, Iterator<NumberType>) -> TypeExpression<U>,
) = ArrayForUnnestedRangeExpression(
    range = this,
    iteratorName = iteratorName,
    indexName = indexName,
    transformation = transformation,
)
