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
    private var count: Int = 1
        get() = field++

    fun getIteratorName() = "iterator$count"

    fun resetCounter() {
        count = 1
    }
}

enum class SatisfiesType {
    ANY,
    EVERY,
}

sealed class SatisfiesExpression<T : ValidType>(
    private val satisfiesType: SatisfiesType,
    private val arrayExpression: TypeExpression<ArrayType<T>>,
    private val iteratorName: String? = null,
    private val predicate: (Iterator<T>) -> TypeExpression<BooleanType>,
) : TypeExpression<BooleanType> {
    override fun toDopeQuery(): DopeQuery {
        val listDopeQuery = arrayExpression.toDopeQuery()
        val iteratorVariable = iteratorName ?: IteratorManager.getIteratorName()

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
    iteratorName: String? = null,
    predicate: (Iterator<T>) -> TypeExpression<BooleanType>,
) : SatisfiesExpression<T>(ANY, arrayExpression, iteratorName, predicate)

class EverySatisfiesExpression<T : ValidType>(
    arrayExpression: TypeExpression<ArrayType<T>>,
    iteratorName: String? = null,
    predicate: (Iterator<T>) -> TypeExpression<BooleanType>,
) : SatisfiesExpression<T>(EVERY, arrayExpression, iteratorName, predicate)

fun <T : ValidType> TypeExpression<ArrayType<T>>.any(
    iteratorName: String? = null,
    predicate: (Iterator<T>) -> TypeExpression<BooleanType>,
): AnySatisfiesExpression<T> = AnySatisfiesExpression(this, iteratorName, predicate)

fun <T : ValidType> Collection<TypeExpression<T>>.any(
    iteratorName: String? = null,
    predicate: (Iterator<T>) -> TypeExpression<BooleanType>,
): AnySatisfiesExpression<T> = AnySatisfiesExpression(toDopeType(), iteratorName, predicate)

fun <T : ValidType> TypeExpression<ArrayType<T>>.every(
    iteratorName: String? = null,
    predicate: (Iterator<T>) -> TypeExpression<BooleanType>,
): EverySatisfiesExpression<T> = EverySatisfiesExpression(this, iteratorName, predicate)

fun <T : ValidType> Collection<TypeExpression<T>>.every(
    iteratorName: String? = null,
    predicate: (Iterator<T>) -> TypeExpression<BooleanType>,
): EverySatisfiesExpression<T> = EverySatisfiesExpression(toDopeType(), iteratorName, predicate)
