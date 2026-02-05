package ch.ergon.dope.couchbase.resolver

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolver.expression.queryString
import ch.ergon.dope.couchbase.util.formatToQueryString
import ch.ergon.dope.merge
import ch.ergon.dope.orEmpty
import ch.ergon.dope.resolvable.clause.model.WindowDeclaration
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.Between
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.Following
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OrderingTerm
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverWindowDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverWindowReference
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.Preceding
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowFrameClause

interface WindowResolver : AbstractCouchbaseResolver {
    fun resolve(overDefinition: OverDefinition): CouchbaseDopeQuery = when (overDefinition) {
        is OverWindowDefinition -> {
            val win = overDefinition.windowDefinition.toDopeQuery(this)
            CouchbaseDopeQuery("OVER (${win.queryString})", win.parameters)
        }

        is OverWindowReference -> CouchbaseDopeQuery("OVER `${overDefinition.windowReference}`")
    }

    fun resolve(windowDefinition: WindowDefinition): CouchbaseDopeQuery {
        val ref = windowDefinition.windowReferenceExpression?.toDopeQuery(this)
        val parts = mutableListOf<String>()
        val params = mutableListOf<DopeParameters>()
        if (ref != null) {
            parts += ref.queryString; params += ref.parameters
        }
        windowDefinition.windowPartitionClause?.map { it.toDopeQuery(this) }?.let { list ->
            parts += list.joinToString(", ", prefix = "PARTITION BY ") { it.queryString }
            params += list.map { it.parameters }
        }
        windowDefinition.windowOrderClause?.map { it.toDopeQuery(this) }?.let { list ->
            parts += list.joinToString(", ", prefix = "ORDER BY ") { it.queryString }
            params += list.map { it.parameters }
        }
        val frame = windowDefinition.windowFrameClause?.toDopeQuery(this)
        if (frame != null) {
            parts += frame.queryString; params += listOf(frame.parameters)
        }
        return CouchbaseDopeQuery(parts.joinToString(" "), params.merge())
    }

    fun resolve(windowFrameClause: WindowFrameClause): CouchbaseDopeQuery {
        val extent = windowFrameClause.windowFrameExtent.toDopeQuery(this)
        val windowFrameQueryString = listOfNotNull(
            windowFrameClause.windowFrameType.queryString,
            extent.queryString,
            windowFrameClause.windowFrameExclusion?.queryString,
        ).joinToString(" ")
        return CouchbaseDopeQuery(windowFrameQueryString, extent.parameters)
    }

    fun resolve(between: Between): CouchbaseDopeQuery {
        val betweenDopeQuery = between.between.toDopeQuery(this)
        val andDopeQuery = between.and.toDopeQuery(this)
        return CouchbaseDopeQuery(
            formatToQueryString("BETWEEN", betweenDopeQuery.queryString, "AND", andDopeQuery.queryString, separator = " "),
            betweenDopeQuery.parameters.merge(andDopeQuery.parameters),
        )
    }

    fun resolve(a: Following): CouchbaseDopeQuery {
        val off = a.offset.toDopeQuery(this)
        return CouchbaseDopeQuery(formatToQueryString(off.queryString, "FOLLOWING"), off.parameters)
    }

    fun resolve(a: Preceding): CouchbaseDopeQuery {
        val off = a.offset.toDopeQuery(this)
        return CouchbaseDopeQuery(formatToQueryString(off.queryString, "PRECEDING"), off.parameters)
    }

    fun resolve(orderingTerm: OrderingTerm): CouchbaseDopeQuery {
        val expressionDopeQuery = orderingTerm.expression.toDopeQuery(this)
        val orderingQueryString =
            expressionDopeQuery.queryString + (
                orderingTerm.orderType?.let { " $it" }
                    ?: ""
                ) + (orderingTerm.nullsOrder?.let { " " + it.queryString } ?: "")
        return CouchbaseDopeQuery(orderingQueryString, expressionDopeQuery.parameters)
    }

    fun resolve(windowDeclaration: WindowDeclaration): CouchbaseDopeQuery {
        val windowDefinitionDopeQuery = windowDeclaration.windowDefinition?.toDopeQuery(this)
        return CouchbaseDopeQuery(
            "`${windowDeclaration.reference}` AS (${windowDefinitionDopeQuery?.queryString.orEmpty()})",
            windowDefinitionDopeQuery?.parameters.orEmpty(),
        )
    }
}
