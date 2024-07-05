package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

class NotWithinExpression<T : ValidType>(
    private val search: TypeExpression<T>,
    private val target: TypeExpression<ArrayType<T>>,
) : TypeExpression<BooleanType> {
    override fun toDopeQuery(): DopeQuery {
        val searchDopeQuery = search.toDopeQuery()
        val targetDopeQuery = target.toDopeQuery()
        return DopeQuery(
            queryString = "${searchDopeQuery.queryString} NOT WITHIN ${targetDopeQuery.queryString}",
            parameters = searchDopeQuery.parameters + targetDopeQuery.parameters,
        )
    }
}

fun <T : ValidType> TypeExpression<T>.notWithin(target: TypeExpression<ArrayType<T>>) = NotWithinExpression(this, target)
