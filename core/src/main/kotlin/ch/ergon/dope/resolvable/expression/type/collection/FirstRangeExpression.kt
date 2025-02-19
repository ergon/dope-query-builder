package ch.ergon.dope.resolvable.expression.type.collection

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.collection.TransformationType.FIRST
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class FirstRangeExpression<T : ValidType, U : ValidType>(
    override val membershipType: MembershipType,
    override val range: TypeExpression<ArrayType<T>>,
    override val iteratorName: String? = null,
    override val transformation: (Iterator<T>) -> TypeExpression<U>,
    override val condition: ((Iterator<T>) -> TypeExpression<BooleanType>)? = null,
) : TypeExpression<U>, RangeExpression<T, U>() {
    override val transformationType: TransformationType = FIRST
    override val withAttributeKeys: ((Iterator<T>) -> TypeExpression<StringType>)? = null
}
