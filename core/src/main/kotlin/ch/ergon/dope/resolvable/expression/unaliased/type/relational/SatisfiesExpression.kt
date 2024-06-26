package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.SatisfiesType.ANY
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.SatisfiesType.EVERY
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

object IteratorManager {
    var count: Int = 1
        get() = field++

    fun resetCounter() {
        count = 1
    }
}

const val DEFAULT_ITERATOR_VARIABLE = "iterator"

enum class SatisfiesType {
    ANY,
    EVERY,
}

sealed class SatisfiesExpression<T : ValidType>(
    private val satisfiesType: SatisfiesType,
    private val list: TypeExpression<ArrayType<T>>,
    private val variable: String,
    private val predicate: (Iterator<T>) -> TypeExpression<BooleanType>,
) : TypeExpression<BooleanType> {
    override fun toDopeQuery(): DopeQuery {
        val listDopeQuery = list.toDopeQuery()
        val iteratorVariable = if (variable == DEFAULT_ITERATOR_VARIABLE) variable + IteratorManager.count else variable

        val predicateDopeQuery = predicate(Iterator(iteratorVariable)).toDopeQuery()
        return DopeQuery(
            queryString = "$satisfiesType `$iteratorVariable` IN ${listDopeQuery.queryString} SATISFIES ${predicateDopeQuery.queryString} END",
            parameters = listDopeQuery.parameters + predicateDopeQuery.parameters,
        )
    }
}

class Iterator<T : ValidType>(private val variable: String) : TypeExpression<T> {
    override fun toDopeQuery() = DopeQuery(
        queryString = "`$variable`",
        parameters = emptyMap(),
    )
}

class AnySatisfiesExpression<T : ValidType>(
    list: TypeExpression<ArrayType<T>>,
    variable: String = DEFAULT_ITERATOR_VARIABLE,
    predicate: (Iterator<T>) -> TypeExpression<BooleanType>,
) : SatisfiesExpression<T>(ANY, list, variable, predicate)

class EverySatisfiesExpression<T : ValidType>(
    list: TypeExpression<ArrayType<T>>,
    variable: String = DEFAULT_ITERATOR_VARIABLE,
    predicate: (Iterator<T>) -> TypeExpression<BooleanType>,
) : SatisfiesExpression<T>(EVERY, list, variable, predicate)

fun <T : ValidType> TypeExpression<ArrayType<T>>.any(
    variable: String = DEFAULT_ITERATOR_VARIABLE,
    predicate: (Iterator<T>) -> TypeExpression<BooleanType>,
): AnySatisfiesExpression<T> = AnySatisfiesExpression(this, variable, predicate)

fun <T : ValidType> Collection<TypeExpression<T>>.any(
    variable: String = DEFAULT_ITERATOR_VARIABLE,
    predicate: (Iterator<T>) -> TypeExpression<BooleanType>,
): AnySatisfiesExpression<T> = AnySatisfiesExpression(toDopeType(), variable, predicate)

fun <T : ValidType> TypeExpression<ArrayType<T>>.every(
    variable: String = DEFAULT_ITERATOR_VARIABLE,
    predicate: (Iterator<T>) -> TypeExpression<BooleanType>,
): EverySatisfiesExpression<T> = EverySatisfiesExpression(this, variable, predicate)

fun <T : ValidType> Collection<TypeExpression<T>>.every(
    variable: String = DEFAULT_ITERATOR_VARIABLE,
    predicate: (Iterator<T>) -> TypeExpression<BooleanType>,
): EverySatisfiesExpression<T> = EverySatisfiesExpression(toDopeType(), variable, predicate)
