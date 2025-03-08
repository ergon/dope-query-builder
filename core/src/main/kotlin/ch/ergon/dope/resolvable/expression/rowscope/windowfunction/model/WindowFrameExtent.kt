package ch.ergon.dope.resolvable.expression.rowscope.windowfunction.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType

interface WindowFrameExtent : Resolvable

interface FrameBetween : Resolvable

interface FrameAndBetween : Resolvable

class Between(private val between: FrameBetween, private val and: FrameAndBetween) : WindowFrameExtent {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val betweenDopeQuery = between.toDopeQuery(manager)
        val andDopeQuery = and.toDopeQuery(manager)
        return DopeQuery(
            "BETWEEN ${betweenDopeQuery.queryString} AND ${andDopeQuery.queryString}",
            betweenDopeQuery.parameters.merge(andDopeQuery.parameters),
        )
    }
}

class UnboundedFollowing : FrameAndBetween {
    override fun toDopeQuery(manager: DopeQueryManager) = DopeQuery("UNBOUNDED FOLLOWING")
}

class UnboundedPreceding : FrameBetween, WindowFrameExtent {
    override fun toDopeQuery(manager: DopeQueryManager) = DopeQuery("UNBOUNDED PRECEDING")
}

class CurrentRow : FrameBetween, FrameAndBetween, WindowFrameExtent {
    override fun toDopeQuery(manager: DopeQueryManager) = DopeQuery("CURRENT ROW")
}

class Following : FrameBetween, FrameAndBetween {
    val offset: TypeExpression<NumberType>

    constructor(offset: TypeExpression<NumberType>) {
        this.offset = offset
    }

    constructor(offset: Number) {
        this.offset = offset.toDopeType()
    }

    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val offsetDopeQuery = offset.toDopeQuery(manager)
        return DopeQuery("${offsetDopeQuery.queryString} FOLLOWING", offsetDopeQuery.parameters)
    }
}

class Preceding : FrameBetween, FrameAndBetween, WindowFrameExtent {
    val offset: TypeExpression<NumberType>

    constructor(offset: TypeExpression<NumberType>) {
        this.offset = offset
    }

    constructor(offset: Number) {
        this.offset = offset.toDopeType()
    }

    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val offsetDopeQuery = offset.toDopeQuery(manager)
        return DopeQuery("${offsetDopeQuery.queryString} PRECEDING", offsetDopeQuery.parameters)
    }
}
