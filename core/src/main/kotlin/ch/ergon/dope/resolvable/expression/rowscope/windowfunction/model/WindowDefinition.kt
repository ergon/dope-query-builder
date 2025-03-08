package ch.ergon.dope.resolvable.expression.rowscope.windowfunction.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class WindowDefinition(
    private val windowReference: TypeExpression<StringType>? = null,
    private val windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
    private val windowOrderClause: List<OrderingTerm>? = null,
    private val windowFrameClause: WindowFrameClause? = null,
) : Resolvable {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val windowReferenceDopeQuery = windowReference?.toDopeQuery(manager)
        val windowPartitionClauseDopeQueries = windowPartitionClause?.map { it.toDopeQuery(manager) }
        val windowOrderClauseDopeQueries = windowOrderClause?.map { it.toDopeQuery(manager) }
        val windowFrameClauseDopeQuery = windowFrameClause?.toDopeQuery(manager)

        return DopeQuery(
            queryString = buildString {
                windowReferenceDopeQuery?.queryString?.let {
                    append(it)
                }

                windowPartitionClauseDopeQueries?.takeIf { it.isNotEmpty() }?.let { queries ->
                    if (isNotEmpty()) append(" ")
                    append("PARTITION BY ")
                    append(queries.joinToString(", ") { it.queryString })
                }

                windowOrderClauseDopeQueries?.takeIf { it.isNotEmpty() }?.let { queries ->
                    if (isNotEmpty()) append(" ")
                    append("ORDER BY ")
                    append(queries.joinToString(", ") { it.queryString })
                }

                windowFrameClauseDopeQuery?.queryString?.let {
                    if (isNotEmpty()) append(" ")
                    append(it)
                }
            },
        )
    }
}
