package ch.ergon.dope.resolvable.expression.rowscope.windowdefinition

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.orEmpty
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class WindowDefinition(
    private val windowReferenceExpression: TypeExpression<StringType>? = null,
    private val windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
    private val windowOrderClause: List<OrderingTerm>? = null,
    private val windowFrameClause: WindowFrameClause? = null,
) : Resolvable {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val windowReferenceExpressionDopeQuery = windowReferenceExpression?.toDopeQuery(manager)

        val partitionClauseDopeQueries = windowPartitionClause?.map { it.toDopeQuery(manager) }
        val partitionClauseQueryString = partitionClauseDopeQueries?.joinToString(separator = ", ", prefix = "PARTITION BY ") { it.queryString }

        val orderClauseDopeQueries = windowOrderClause?.map { it.toDopeQuery(manager) }
        val orderClauseQueryString = orderClauseDopeQueries?.joinToString(separator = ", ", prefix = "ORDER BY ") { it.queryString }

        val frameClauseDopeQuery = windowFrameClause?.toDopeQuery(manager)

        val queryString = listOfNotNull(
            windowReferenceExpressionDopeQuery?.queryString,
            partitionClauseQueryString,
            orderClauseQueryString,
            frameClauseDopeQuery?.queryString,
        ).joinToString(separator = " ")

        return DopeQuery(
            queryString = queryString,
            parameters = windowReferenceExpressionDopeQuery?.parameters.orEmpty()
                .merge(*partitionClauseDopeQueries?.map { it.parameters }.orEmpty().toTypedArray())
                .merge(*orderClauseDopeQueries?.map { it.parameters }.orEmpty().toTypedArray())
                .merge(frameClauseDopeQuery?.parameters.orEmpty()),
        )
    }
}
