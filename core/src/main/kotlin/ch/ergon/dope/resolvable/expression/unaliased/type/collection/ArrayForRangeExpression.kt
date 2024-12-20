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

open class ArrayForRangeExpression<T : ValidType, U : ValidType>(
    private val membershipType: MembershipType,
    private val range: TypeExpression<ArrayType<T>>,
    private val iteratorName: String? = null,
    private val transformation: (Iterator<T>) -> TypeExpression<U>,
    private val condition: ((Iterator<T>) -> TypeExpression<BooleanType>)? = null,
) : TypeExpression<ArrayType<U>>, ForRangeExpression<T, U>(
    transformationType = ARRAY,
    membershipType = membershipType,
    range = range,
    iteratorName = iteratorName,
    transformation = transformation,
    condition = condition,
) {
    fun first() = FirstForRangeExpression(
        membershipType = membershipType,
        range = range,
        iteratorName = iteratorName,
        transformation = transformation,
        condition = condition,
    )

    fun toObject(withAttributeKeys: (Iterator<T>) -> TypeExpression<StringType>) =
        ObjectForRangeExpression(
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
) = ArrayForRangeExpression(
    membershipType = IN,
    range = this,
    iteratorName = iteratorName,
    transformation = transformation,
)

fun <T : ValidType> TypeExpression<ArrayType<ValidType>>.mapUnnested(
    iteratorName: String? = null,
    transformation: (Iterator<out ValidType>) -> TypeExpression<T>,
) = ArrayForRangeExpression(
    membershipType = WITHIN,
    range = this,
    iteratorName = iteratorName,
    transformation = transformation,
)

open class ArrayForRangeIndexedExpression<T : ValidType, U : ValidType>(
    private val membershipType: MembershipType,
    private val range: TypeExpression<ArrayType<T>>,
    private val iteratorName: String? = null,
    private val indexName: String? = null,
    private val transformation: (Iterator<T>, Iterator<NumberType>) -> TypeExpression<U>,
    private val condition: ((Iterator<T>, Iterator<NumberType>) -> TypeExpression<BooleanType>)? = null,
) : TypeExpression<ArrayType<U>>, ForRangeIndexedExpression<T, U>(
    transformationType = ARRAY,
    membershipType = membershipType,
    range = range,
    iteratorName = iteratorName,
    indexName = indexName,
    withAttributeKeys = null,
    transformation = transformation,
    condition = condition,
) {
    fun first() = FirstForRangeIndexedExpression(
        membershipType = membershipType,
        range = range,
        iteratorName = iteratorName,
        indexName = indexName,
        transformation = transformation,
        condition = condition,
    )

    fun toObject(attributeKeys: (Iterator<T>, Iterator<NumberType>) -> TypeExpression<StringType>) =
        ObjectForRangeIndexedExpression(
            membershipType = membershipType,
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
) = ArrayForRangeIndexedExpression(
    membershipType = IN,
    range = this,
    iteratorName = iteratorName,
    indexName = indexName,
    transformation = transformation,
)

fun <T : ValidType> TypeExpression<ArrayType<ValidType>>.mapIndexedUnnested(
    iteratorName: String? = null,
    indexName: String? = null,
    transformation: (Iterator<out ValidType>, Iterator<NumberType>) -> TypeExpression<T>,
) = ArrayForRangeIndexedExpression(
    membershipType = WITHIN,
    range = this,
    iteratorName = iteratorName,
    indexName = indexName,
    transformation = transformation,
)
