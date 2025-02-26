package ch.ergon.dope.resolvable.expression.type.collection

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

class ExistsExpression<T : ValidType>(private val array: TypeExpression<ArrayType<T>>) : TypeExpression<BooleanType> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val arrayDopeQuery = array.toDopeQuery(manager)
        return DopeQuery(
            queryString = "EXISTS ${arrayDopeQuery.queryString}",
            parameters = arrayDopeQuery.parameters,
        )
    }
}

fun <T : ValidType> exists(array: TypeExpression<ArrayType<T>>) = ExistsExpression(array)

fun <T : ValidType> exists(array: Collection<TypeExpression<T>>) = exists(array.toDopeType())

fun <T : ValidType> exists(selectClause: ISelectOffsetClause<T>) = exists(selectClause.asExpression())
