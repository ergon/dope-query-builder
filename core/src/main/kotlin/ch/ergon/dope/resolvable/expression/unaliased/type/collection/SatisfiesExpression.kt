package ch.ergon.dope.resolvable.expression.unaliased.type.collection

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.SatisfiesType.ANY
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.SatisfiesType.EVERY
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
    private val arrayExpression: TypeExpression<ArrayType<T>>,
    private val iteratorName: String,
    private val predicate: (Iterator<T>) -> TypeExpression<BooleanType>,
) : TypeExpression<BooleanType> {
    override fun toDopeQuery(): DopeQuery {
        val listDopeQuery = arrayExpression.toDopeQuery()
        val iteratorVariable = if (iteratorName == DEFAULT_ITERATOR_VARIABLE) iteratorName + IteratorManager.count else iteratorName

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
    arrayExpression: TypeExpression<ArrayType<T>>,
    iteratorName: String = DEFAULT_ITERATOR_VARIABLE,
    predicate: (Iterator<T>) -> TypeExpression<BooleanType>,
) : SatisfiesExpression<T>(ANY, arrayExpression, iteratorName, predicate)

class EverySatisfiesExpression<T : ValidType>(
    arrayExpression: TypeExpression<ArrayType<T>>,
    iteratorName: String = DEFAULT_ITERATOR_VARIABLE,
    predicate: (Iterator<T>) -> TypeExpression<BooleanType>,
) : SatisfiesExpression<T>(EVERY, arrayExpression, iteratorName, predicate)

fun <T : ValidType> TypeExpression<ArrayType<T>>.any(
    iteratorName: String = DEFAULT_ITERATOR_VARIABLE,
    predicate: (Iterator<T>) -> TypeExpression<BooleanType>,
): AnySatisfiesExpression<T> = AnySatisfiesExpression(this, iteratorName, predicate)

fun <T : ValidType> Collection<TypeExpression<T>>.any(
    iteratorName: String = DEFAULT_ITERATOR_VARIABLE,
    predicate: (Iterator<T>) -> TypeExpression<BooleanType>,
): AnySatisfiesExpression<T> = AnySatisfiesExpression(toDopeType(), iteratorName, predicate)

fun <T : ValidType> TypeExpression<ArrayType<T>>.every(
    iteratorName: String = DEFAULT_ITERATOR_VARIABLE,
    predicate: (Iterator<T>) -> TypeExpression<BooleanType>,
): EverySatisfiesExpression<T> = EverySatisfiesExpression(this, iteratorName, predicate)

fun <T : ValidType> Collection<TypeExpression<T>>.every(
    iteratorName: String = DEFAULT_ITERATOR_VARIABLE,
    predicate: (Iterator<T>) -> TypeExpression<BooleanType>,
): EverySatisfiesExpression<T> = EverySatisfiesExpression(toDopeType(), iteratorName, predicate)
