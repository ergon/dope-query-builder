package ch.ergon.dope.resolvable.expression.unaliased.type.collection

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.TransformationType.OBJECT
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class ObjectForRangeExpression<T : ValidType, U : ValidType>(
    membershipType: MembershipType,
    range: TypeExpression<ArrayType<T>>,
    iteratorName: String? = null,
    indexName: String? = null,
    withAttributeKeys: ((Iterator<T>, Iterator<NumberType>) -> TypeExpression<StringType>),
    transformation: (Iterator<T>, Iterator<NumberType>) -> TypeExpression<U>,
    condition: ((Iterator<T>, Iterator<NumberType>) -> TypeExpression<BooleanType>)? = null,
) : TypeExpression<ObjectType>, ForRangeExpression<T, U>(
    transformationType = OBJECT,
    membershipType = membershipType,
    range = range,
    iteratorName = iteratorName,
    indexName = indexName,
    withAttributeKeys = withAttributeKeys,
    transformation = transformation,
    condition = condition,
)
