package ch.ergon.dope.resolvable.expression.type.range

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.collection.Iterator
import ch.ergon.dope.resolvable.expression.type.range.TransformationType.OBJECT
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class ObjectRangeExpression<T : ValidType, U : ValidType>(
    override val membershipType: MembershipType,
    override val range: TypeExpression<ArrayType<T>>,
    override val iteratorName: String? = null,
    override val withAttributeKeys: ((Iterator<T>) -> TypeExpression<StringType>),
    override val transformation: (Iterator<T>) -> TypeExpression<U>,
    override val condition: ((Iterator<T>) -> TypeExpression<BooleanType>)? = null,
) : TypeExpression<ObjectType>, RangeExpression<T, U>() {
    override val transformationType: TransformationType = OBJECT
}
