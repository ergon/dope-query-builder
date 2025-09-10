package ch.ergon.dope.resolvable.expression.rowscope.windowdefinition

import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType

private const val UNBOUNDED = "UNBOUNDED"
private const val FOLLOWING = "FOLLOWING"
private const val PRECEDING = "PRECEDING"

interface WindowFrameExtent : Resolvable

interface FrameBetween : Resolvable

interface FrameAndBetween : Resolvable

data class Between(val between: FrameBetween, val and: FrameAndBetween) : WindowFrameExtent

fun between(between: FrameBetween, and: FrameAndBetween) = Between(between, and)

class UnboundedFollowing : FrameAndBetween

fun unboundedFollowing() = UnboundedFollowing()

class UnboundedPreceding : FrameBetween, WindowFrameExtent

fun unboundedPreceding() = UnboundedPreceding()

class CurrentRow : FrameBetween, FrameAndBetween, WindowFrameExtent

fun currentRow() = CurrentRow()

data class Following(val offset: TypeExpression<NumberType>) : FrameBetween, FrameAndBetween

fun following(offset: TypeExpression<NumberType>) = Following(offset)

fun following(offset: Number) = following(offset.toDopeType())

data class Preceding(val offset: TypeExpression<NumberType>) : FrameBetween, FrameAndBetween, WindowFrameExtent

fun preceding(offset: TypeExpression<NumberType>) = Preceding(offset)

fun preceding(offset: Number) = preceding(offset.toDopeType())
