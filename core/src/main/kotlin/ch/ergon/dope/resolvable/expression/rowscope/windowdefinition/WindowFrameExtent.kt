package ch.ergon.dope.resolvable.expression.rowscope.windowdefinition

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.util.formatToQueryString
import ch.ergon.dope.validtype.NumberType

private const val UNBOUNDED = "UNBOUNDED"
private const val FOLLOWING = "FOLLOWING"
private const val PRECEDING = "PRECEDING"

interface WindowFrameExtent : Resolvable

interface FrameBetween : Resolvable

interface FrameAndBetween : Resolvable

class Between(private val between: FrameBetween, private val and: FrameAndBetween) : WindowFrameExtent {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val betweenDopeQuery = between.toDopeQuery(manager)
        val andDopeQuery = and.toDopeQuery(manager)
        return DopeQuery(
            formatToQueryString("BETWEEN", betweenDopeQuery.queryString, "AND", andDopeQuery.queryString, separator = " "),
            betweenDopeQuery.parameters.merge(andDopeQuery.parameters),
        )
    }
}

fun between(between: FrameBetween, and: FrameAndBetween) = Between(between, and)

class UnboundedFollowing : FrameAndBetween {
    override fun toDopeQuery(manager: DopeQueryManager) = DopeQuery(formatToQueryString(UNBOUNDED, FOLLOWING))
}

fun unboundedFollowing() = UnboundedFollowing()

class UnboundedPreceding : FrameBetween, WindowFrameExtent {
    override fun toDopeQuery(manager: DopeQueryManager) = DopeQuery(formatToQueryString(UNBOUNDED, PRECEDING))
}

fun unboundedPreceding() = UnboundedPreceding()

class CurrentRow : FrameBetween, FrameAndBetween, WindowFrameExtent {
    override fun toDopeQuery(manager: DopeQueryManager) = DopeQuery(formatToQueryString("CURRENT", "ROW"))
}

fun currentRow() = CurrentRow()

class Following(private val offset: TypeExpression<NumberType>) : FrameBetween, FrameAndBetween {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val offsetDopeQuery = offset.toDopeQuery(manager)
        return DopeQuery(formatToQueryString(offsetDopeQuery.queryString, FOLLOWING), offsetDopeQuery.parameters)
    }
}

fun following(offset: TypeExpression<NumberType>) = Following(offset)

fun following(offset: Number) = following(offset.toDopeType())

class Preceding(private val offset: TypeExpression<NumberType>) : FrameBetween, FrameAndBetween, WindowFrameExtent {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val offsetDopeQuery = offset.toDopeQuery(manager)
        return DopeQuery(formatToQueryString(offsetDopeQuery.queryString, PRECEDING), offsetDopeQuery.parameters)
    }
}

fun preceding(offset: TypeExpression<NumberType>) = Preceding(offset)

fun preceding(offset: Number) = preceding(offset.toDopeType())
