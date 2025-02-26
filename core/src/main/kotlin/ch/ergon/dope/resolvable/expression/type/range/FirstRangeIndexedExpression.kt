package ch.ergon.dope.resolvable.expression.type.range

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.collection.Iterator
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class FirstRangeIndexedExpression<T : ValidType, U : ValidType>(
    override val membershipType: MembershipType,
    override val range: TypeExpression<ArrayType<T>>,
    override val indexName: String? = null,
    override val iteratorName: String? = null,
    override val transformation: (Iterator<NumberType>, Iterator<T>) -> TypeExpression<U>,
    override val condition: ((Iterator<NumberType>, Iterator<T>) -> TypeExpression<BooleanType>)? = null,
) : TypeExpression<U>, RangeIndexedExpression<T, U>() {
    override val transformationType: TransformationType = TransformationType.FIRST
    override val withAttributeKeys: ((Iterator<NumberType>, Iterator<T>) -> TypeExpression<StringType>)? = null
}
