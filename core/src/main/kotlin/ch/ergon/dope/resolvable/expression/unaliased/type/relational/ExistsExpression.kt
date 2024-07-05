package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

class ExistsExpression<T : ValidType>(private val array: TypeExpression<ArrayType<T>>) : TypeExpression<BooleanType> {
    override fun toDopeQuery(): DopeQuery {
        val arrayDopeQuery = array.toDopeQuery()
        return DopeQuery(
            queryString = "EXISTS ${arrayDopeQuery.queryString}",
            parameters = arrayDopeQuery.parameters,
        )
    }
}

fun <T : ValidType> exists(array: TypeExpression<ArrayType<T>>) = ExistsExpression(array)
