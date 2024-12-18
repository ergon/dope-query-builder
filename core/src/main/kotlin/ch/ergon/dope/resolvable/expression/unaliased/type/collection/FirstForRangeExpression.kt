package ch.ergon.dope.resolvable.expression.unaliased.type.collection

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.MembershipType.IN
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.MembershipType.WITHIN
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.TransformationType.FIRST
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

class FirstForRangeExpression<T : ValidType, U : ValidType>(
    range: TypeExpression<ArrayType<T>>,
    iteratorName: String? = null,
    indexName: String? = null,
    transformation: (Iterator<T>, Iterator<NumberType>) -> TypeExpression<U>,
    condition: ((Iterator<T>, Iterator<NumberType>) -> TypeExpression<BooleanType>)? = null,
) : TypeExpression<U>, ForRangeExpression<T, U>(
    transformationType = FIRST,
    membershipType = IN,
    range = range,
    iteratorName = iteratorName,
    indexName = indexName,
    withAttributeKeys = null,
    transformation = transformation,
    condition = condition,
)

class FirstForUnnestedRangeExpression<T : ValidType, U : ValidType>(
    range: TypeExpression<ArrayType<T>>,
    iteratorName: String? = null,
    indexName: String? = null,
    transformation: (Iterator<T>, Iterator<NumberType>) -> TypeExpression<U>,
    condition: ((Iterator<T>, Iterator<NumberType>) -> TypeExpression<BooleanType>)? = null,
) : TypeExpression<ValidType>, ForRangeExpression<T, U>(
    transformationType = FIRST,
    membershipType = WITHIN,
    range = range,
    iteratorName = iteratorName,
    indexName = indexName,
    withAttributeKeys = null,
    transformation = transformation,
    condition = condition,
)
