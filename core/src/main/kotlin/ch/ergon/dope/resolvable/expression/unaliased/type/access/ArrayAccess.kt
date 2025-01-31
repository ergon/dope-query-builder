package ch.ergon.dope.resolvable.expression.unaliased.type.access

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.SingleExpression
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

class ArrayAccess<T : ValidType>(
    private val array: SingleExpression<ArrayType<T>>,
    private val index: TypeExpression<NumberType>,
) : TypeExpression<T> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val arrayDopeQuery = array.toDopeQuery(manager)
        val indexDopeQuery = index.toDopeQuery(manager)
        return DopeQuery(
            queryString = "${arrayDopeQuery.queryString}[${indexDopeQuery.queryString}]",
            parameters = arrayDopeQuery.parameters.merge(indexDopeQuery.parameters),
        )
    }
}

fun <T : ValidType> SingleExpression<ArrayType<T>>.get(index: TypeExpression<NumberType>) = ArrayAccess(this, index)

fun <T : ValidType> SingleExpression<ArrayType<T>>.get(index: Number) = get(index.toDopeType())

fun <T : ValidType> ISelectOffsetClause<T>.get(index: TypeExpression<NumberType>) = asExpression().get(index)

fun <T : ValidType> ISelectOffsetClause<T>.get(index: Number) = asExpression().get(index.toDopeType())
