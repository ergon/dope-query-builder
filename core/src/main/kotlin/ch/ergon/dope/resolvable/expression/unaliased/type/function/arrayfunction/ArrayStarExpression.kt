package ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ObjectType

class ArrayStarExpression(
    private val objectArray: TypeExpression<ArrayType<ObjectType>>,
) : TypeExpression<ObjectType>, FunctionOperator {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val objectArrayDopeQuery = objectArray.toDopeQuery(manager)
        return DopeQuery(
            queryString = toFunctionQueryString("ARRAY_STAR", objectArrayDopeQuery.queryString),
            parameters = objectArrayDopeQuery.parameters,
        )
    }
}

fun arrayStar(objectArray: TypeExpression<ArrayType<ObjectType>>) = ArrayStarExpression(objectArray)

@JvmName("receiverArrayStar")
fun TypeExpression<ArrayType<ObjectType>>.arrayStar() = arrayStar(this)

fun arrayStar(objectArray: Collection<TypeExpression<ObjectType>>) = arrayStar(objectArray.toDopeType())

@JvmName("collectionReceiverArrayStar")
fun Collection<TypeExpression<ObjectType>>.arrayStar() = arrayStar(this.toDopeType())

fun arrayStar(objectArray: ISelectOffsetClause<ObjectType>) = ArrayStarExpression(objectArray.asExpression())

@JvmName("selectClauseReceiverArrayStar")
fun ISelectOffsetClause<ObjectType>.arrayStar() = arrayStar(this.asExpression())
