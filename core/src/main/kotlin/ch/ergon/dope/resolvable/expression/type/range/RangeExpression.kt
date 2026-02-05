package ch.ergon.dope.resolvable.expression.type.range

import ch.ergon.dope.validtype.ValidType

enum class TransformationType {
    ARRAY,
    FIRST,
    OBJECT,
}

enum class MembershipType {
    IN,
    WITHIN,
}

sealed class RangeExpression<T : ValidType, U : ValidType, V : ValidType> : RangeLike<T, U, V>
