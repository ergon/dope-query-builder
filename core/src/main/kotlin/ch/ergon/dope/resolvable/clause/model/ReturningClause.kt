package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.clause.Clause
import ch.ergon.dope.resolvable.clause.IDeleteOffsetClause
import ch.ergon.dope.resolvable.clause.IDeleteReturningClause
import ch.ergon.dope.resolvable.clause.IUpdateLimitClause
import ch.ergon.dope.resolvable.clause.IUpdateReturningClause
import ch.ergon.dope.resolvable.clause.model.ReturningType.RAW
import ch.ergon.dope.resolvable.expression.AsteriskExpression
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.formatToQueryStringWithSymbol
import ch.ergon.dope.resolvable.fromable.Returnable
import ch.ergon.dope.validtype.ValidType

enum class ReturningType(val queryString: String) {
    ELEMENT("ELEMENT"),
    RAW("RAW"),
    VALUE("VALUE"),
}

sealed class ReturningClause(
    private val returnable: Returnable,
    private vararg val additionalReturnables: Returnable,
    private val parentClause: Clause,
) : Resolvable {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val parentDopeQuery = parentClause.toDopeQuery(manager)
        val returnableDopeQuery = returnable.toDopeQuery(manager)
        val additionalReturnableDopeQueries = additionalReturnables.map { it.toDopeQuery(manager) }
        return DopeQuery(
            queryString = formatToQueryStringWithSymbol(
                parentDopeQuery.queryString,
                "RETURNING",
                returnableDopeQuery.queryString,
                *additionalReturnableDopeQueries.map { it.queryString }.toTypedArray(),
            ),
            parameters = parentDopeQuery.parameters.merge(
                returnableDopeQuery.parameters,
                *additionalReturnableDopeQueries.map { it.parameters }.toTypedArray(),
            ),
        )
    }
}

class ReturningExpression(private val typeExpression: TypeExpression<out ValidType>) : Returnable {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        return typeExpression.toDopeQuery(manager)
    }
}

class DeleteReturningClause(
    private val returnable: Returnable,
    private vararg val additionalReturnables: Returnable,
    private val parentClause: IDeleteOffsetClause,
) : IDeleteReturningClause, ReturningClause(
    returnable,
    *additionalReturnables,
    parentClause = parentClause,
) {
    fun thenReturning(typeExpression: TypeExpression<out ValidType>) = DeleteReturningClause(
        this.returnable,
        *this.additionalReturnables,
        ReturningExpression(typeExpression),
        parentClause = this.parentClause,
    )

    fun thenReturningAsterisk() = DeleteReturningClause(
        this.returnable,
        *this.additionalReturnables,
        AsteriskExpression(),
        parentClause = this.parentClause,
    )
}

class UpdateReturningClause(
    private val returnable: Returnable,
    private vararg val additionalReturnables: Returnable,
    private val parentClause: IUpdateLimitClause,
) : IUpdateReturningClause, ReturningClause(
    returnable,
    *additionalReturnables,
    parentClause = parentClause,
) {
    fun thenReturning(typeExpression: TypeExpression<out ValidType>) = UpdateReturningClause(
        this.returnable,
        *this.additionalReturnables,
        ReturningExpression(typeExpression),
        parentClause = this.parentClause,
    )

    fun thenReturningAsterisk() = UpdateReturningClause(
        this.returnable,
        *this.additionalReturnables,
        AsteriskExpression(),
        parentClause = this.parentClause,
    )
}

sealed class ReturningSingleClause(
    private val typeExpression: TypeExpression<out ValidType>,
    private val returningType: ReturningType = RAW,
    private val parentClause: Clause,
) : Resolvable {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val parentDopeQuery = parentClause.toDopeQuery(manager)
        val typeExpressionDopeQuery = typeExpression.toDopeQuery(manager)
        return DopeQuery(
            queryString = formatToQueryStringWithSymbol(
                parentDopeQuery.queryString,
                "RETURNING " + returningType.queryString,
                typeExpressionDopeQuery.queryString,
            ),
            parameters = parentDopeQuery.parameters.merge(
                typeExpressionDopeQuery.parameters,
            ),
        )
    }
}

class DeleteReturningSingleClause(
    typeExpression: TypeExpression<out ValidType>,
    returningType: ReturningType = RAW,
    parentClause: IDeleteOffsetClause,
) : IDeleteReturningClause, ReturningSingleClause(typeExpression, returningType, parentClause = parentClause)

class UpdateReturningSingleClause(
    typeExpression: TypeExpression<out ValidType>,
    returningType: ReturningType = RAW,
    parentClause: IUpdateLimitClause,
) : IUpdateReturningClause, ReturningSingleClause(typeExpression, returningType, parentClause = parentClause)
