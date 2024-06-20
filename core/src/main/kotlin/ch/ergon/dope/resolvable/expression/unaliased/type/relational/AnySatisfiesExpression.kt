package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toArrayType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

class AnySatisfiesExpression<Type : ValidType>(
    private val variable: String,
    private val list: TypeExpression<ArrayType<Type>>,
    private val condition: (Iterator<Type>) -> TypeExpression<BooleanType>,
) : TypeExpression<BooleanType> {
    override fun toDopeQuery(): DopeQuery {
        val listDopeQuery = list.toDopeQuery()
        val conditionDopeQuery = condition(Iterator(variable)).toDopeQuery()
        return DopeQuery(
            queryString = "ANY $variable IN ${listDopeQuery.queryString} SATISFIES ${conditionDopeQuery.queryString} END",
            parameters = listDopeQuery.parameters + conditionDopeQuery.parameters,
        )
    }
}

class Iterator<T : ValidType>(private val variable: String) : TypeExpression<T> {
    override fun toDopeQuery(): DopeQuery = DopeQuery(
        queryString = variable,
        parameters = emptyMap(),
    )
}

fun <T : ValidType> any(
    variable: String,
    list: TypeExpression<ArrayType<T>>,
    condition: (Iterator<T>) -> TypeExpression<BooleanType>,
): AnySatisfiesExpression<T> = AnySatisfiesExpression(variable, list, condition)

fun <T : ValidType> any(
    variable: String,
    list: Collection<TypeExpression<T>>,
    condition: (Iterator<T>) -> TypeExpression<BooleanType>,
): AnySatisfiesExpression<T> = AnySatisfiesExpression(variable, list.toArrayType(), condition)
