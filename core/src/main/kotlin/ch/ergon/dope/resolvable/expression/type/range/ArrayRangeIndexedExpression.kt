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

class ArrayRangeIndexedExpression<T : ValidType, U : ValidType>(
    override val membershipType: MembershipType,
    override val range: TypeExpression<ArrayType<T>>,
    override val indexName: String? = null,
    override val iteratorName: String? = null,
    override val transformation: (Iterator<NumberType>, Iterator<T>) -> TypeExpression<U>,
    override val condition: ((Iterator<NumberType>, Iterator<T>) -> TypeExpression<BooleanType>)? = null,
) : TypeExpression<ArrayType<U>>, RangeIndexedExpression<T, U>() {
    override val transformationType: TransformationType = TransformationType.ARRAY
    override val withAttributeKeys: ((Iterator<NumberType>, Iterator<T>) -> TypeExpression<StringType>)? = null

    fun first() = FirstRangeIndexedExpression(
        membershipType = membershipType,
        range = range,
        iteratorName = iteratorName,
        indexName = indexName,
        transformation = transformation,
        condition = condition,
    )

    fun toObject(attributeKeys: (Iterator<NumberType>, Iterator<T>) -> TypeExpression<StringType>) =
        ObjectRangeIndexedExpression(
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
    indexName: String? = null,
    iteratorName: String? = null,
    transformation: (Iterator<NumberType>, Iterator<T>) -> TypeExpression<U>,
) = ArrayRangeIndexedExpression(
    membershipType = IN,
    range = this,
    indexName = indexName,
    iteratorName = iteratorName,
    transformation = transformation,
)

fun <T : ValidType> TypeExpression<ArrayType<ValidType>>.mapIndexedUnnested(
    indexName: String? = null,
    iteratorName: String? = null,
    transformation: (Iterator<NumberType>, Iterator<out ValidType>) -> TypeExpression<T>,
) = ArrayRangeIndexedExpression(
    membershipType = WITHIN,
    range = this,
    indexName = indexName,
    iteratorName = iteratorName,
    transformation = transformation,
)

fun <T : ValidType, U : ValidType> Collection<TypeExpression<T>>.mapIndexed(
    indexName: String? = null,
    iteratorName: String? = null,
    transformation: (Iterator<NumberType>, Iterator<T>) -> TypeExpression<U>,
) = toDopeType().mapIndexed(indexName, iteratorName, transformation)

fun <T : ValidType> Collection<TypeExpression<ValidType>>.mapIndexedUnnested(
    indexName: String? = null,
    iteratorName: String? = null,
    transformation: (Iterator<NumberType>, Iterator<out ValidType>) -> TypeExpression<T>,
) = toDopeType().mapIndexedUnnested(indexName, iteratorName, transformation)

fun <T : ValidType, U : ValidType> ISelectOffsetClause<T>.mapIndexed(
    indexName: String? = null,
    iteratorName: String? = null,
    transformation: (Iterator<NumberType>, Iterator<T>) -> TypeExpression<U>,
) = asExpression().mapIndexed(indexName, iteratorName, transformation)

fun <T : ValidType> ISelectOffsetClause<ValidType>.mapIndexedUnnested(
    indexName: String? = null,
    iteratorName: String? = null,
    transformation: (Iterator<NumberType>, Iterator<out ValidType>) -> TypeExpression<T>,
) = asExpression().mapIndexedUnnested(indexName, iteratorName, transformation)
