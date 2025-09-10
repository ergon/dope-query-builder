package ch.ergon.dope.resolvable.expression.type.range

import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.validtype.ValidType

const val FOR = "FOR"
const val WHEN = "WHEN"
const val END = "END"

enum class TransformationType(val queryString: String) {
    ARRAY("ARRAY"),
    FIRST("FIRST"),
    OBJECT("OBJECT"),
}

enum class MembershipType(val queryString: String) {
    IN("IN"),
    WITHIN("WITHIN"),
}

sealed class RangeExpression<T : ValidType, U : ValidType> : Resolvable, RangeLike<T, U>
