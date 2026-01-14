package ch.ergon.dope.resolvable.expression.type.range

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.collection.Iterator
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

interface RangeLike<T : ValidType, U : ValidType, V : ValidType> : TypeExpression<V> {
    val transformationType: TransformationType
    val membershipType: MembershipType
    val range: TypeExpression<ArrayType<T>>
    val iteratorName: String?
    val withAttributeKeys: ((Iterator<T>) -> TypeExpression<StringType>)?
    val transformation: (Iterator<T>) -> TypeExpression<U>
    val condition: ((Iterator<T>) -> TypeExpression<BooleanType>)?
}

interface RangeIndexedLike<T : ValidType, U : ValidType, V : ValidType> : TypeExpression<V> {
    val transformationType: TransformationType
    val membershipType: MembershipType
    val range: TypeExpression<ArrayType<T>>
    val indexName: String?
    val iteratorName: String?
    val withAttributeKeys: ((Iterator<NumberType>, Iterator<T>) -> TypeExpression<StringType>)?
    val transformation: (Iterator<NumberType>, Iterator<T>) -> TypeExpression<U>
    val condition: ((Iterator<NumberType>, Iterator<T>) -> TypeExpression<BooleanType>)?
}
