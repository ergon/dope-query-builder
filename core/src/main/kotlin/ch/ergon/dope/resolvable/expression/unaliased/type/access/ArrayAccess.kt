package ch.ergon.dope.resolvable.expression.unaliased.type.access

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

class ArrayAccess<T : ValidType>(
    private val array: TypeExpression<ArrayType<T>>,
    private val index: TypeExpression<NumberType>,
) : TypeExpression<T> {
    override fun toDopeQuery(): DopeQuery {
        val arrayDopeQuery = array.toDopeQuery()
        val indexDopeQuery = index.toDopeQuery()
        return DopeQuery(
            queryString = "${arrayDopeQuery.queryString}[${indexDopeQuery.queryString}]",
            parameters = arrayDopeQuery.parameters + indexDopeQuery.parameters,
        )
    }
}

fun <T : ValidType> TypeExpression<ArrayType<T>>.get(index: TypeExpression<NumberType>) = ArrayAccess(this, index)
