package ch.ergon.dope.resolvable.expression.unaliased.type.collection

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.TransformationType.FIRST
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

class FirstForRangeExpression<T : ValidType, U : ValidType>(
    membershipType: MembershipType,
    range: TypeExpression<ArrayType<T>>,
    iteratorName: String? = null,
    transformation: (Iterator<T>) -> TypeExpression<U>,
    condition: ((Iterator<T>) -> TypeExpression<BooleanType>)? = null,
) : TypeExpression<U>, ForRangeExpression<T, U>(
    transformationType = FIRST,
    membershipType = membershipType,
    range = range,
    iteratorName = iteratorName,
    transformation = transformation,
    condition = condition,
)

class FirstForRangeIndexedExpression<T : ValidType, U : ValidType>(
    membershipType: MembershipType,
    range: TypeExpression<ArrayType<T>>,
    iteratorName: String? = null,
    indexName: String? = null,
    transformation: (Iterator<T>, Iterator<NumberType>) -> TypeExpression<U>,
    condition: ((Iterator<T>, Iterator<NumberType>) -> TypeExpression<BooleanType>)? = null,
) : TypeExpression<U>, ForRangeIndexedExpression<T, U>(
    transformationType = FIRST,
    membershipType = membershipType,
    range = range,
    iteratorName = iteratorName,
    indexName = indexName,
    transformation = transformation,
    condition = condition,
)
