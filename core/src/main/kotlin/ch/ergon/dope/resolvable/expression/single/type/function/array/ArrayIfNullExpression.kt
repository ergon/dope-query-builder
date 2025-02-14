package ch.ergon.dope.resolvable.expression.single.type.function.array

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.util.operator.FunctionOperator
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

class ArrayIfNullExpression<T : ValidType>(
    private val array: TypeExpression<ArrayType<T>>,
) : TypeExpression<T>, FunctionOperator {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val arrayDopeQuery = array.toDopeQuery(manager)
        return DopeQuery(
            queryString = toFunctionQueryString("ARRAY_IFNULL", arrayDopeQuery),
            parameters = arrayDopeQuery.parameters,
        )
    }
}

fun <T : ValidType> arrayIfNull(array: TypeExpression<ArrayType<T>>) = ArrayIfNullExpression(array)

fun <T : ValidType> arrayIfNull(selectClause: ISelectOffsetClause<T>) = arrayIfNull(selectClause.asExpression())
