package ch.ergon.dope.resolvable.expression.type.collection

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.SingleExpression
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.collection.SatisfiesType.ANY
import ch.ergon.dope.resolvable.expression.type.collection.SatisfiesType.EVERY
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

enum class SatisfiesType {
    ANY,
    EVERY,
}

sealed class SatisfiesExpression<T : ValidType>(
    private val satisfiesType: SatisfiesType,
    private val arrayExpression: SingleExpression<ArrayType<T>>,
    private val iteratorName: String? = null,
    private val predicate: (Iterator<T>) -> TypeExpression<BooleanType>,
) : TypeExpression<BooleanType> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val arrayDopeQuery = arrayExpression.toDopeQuery(manager)
        val iteratorVariable = iteratorName ?: manager.iteratorManager.getIteratorName()
        val predicateDopeQuery = predicate(Iterator(iteratorVariable)).toDopeQuery(manager)
        return DopeQuery(
            queryString = "$satisfiesType `$iteratorVariable` IN ${arrayDopeQuery.queryString} SATISFIES ${predicateDopeQuery.queryString} END",
            parameters = arrayDopeQuery.parameters.merge(predicateDopeQuery.parameters),
        )
    }
}

class AnySatisfiesExpression<T : ValidType>(
    arrayExpression: SingleExpression<ArrayType<T>>,
    iteratorName: String? = null,
    predicate: (Iterator<T>) -> TypeExpression<BooleanType>,
) : SatisfiesExpression<T>(ANY, arrayExpression, iteratorName, predicate)

class EverySatisfiesExpression<T : ValidType>(
    arrayExpression: SingleExpression<ArrayType<T>>,
    iteratorName: String? = null,
    predicate: (Iterator<T>) -> TypeExpression<BooleanType>,
) : SatisfiesExpression<T>(EVERY, arrayExpression, iteratorName, predicate)

fun <T : ValidType> SingleExpression<ArrayType<T>>.any(
    iteratorName: String? = null,
    predicate: (Iterator<T>) -> TypeExpression<BooleanType>,
) = AnySatisfiesExpression(this, iteratorName, predicate)

fun <T : ValidType> Collection<TypeExpression<T>>.any(
    iteratorName: String? = null,
    predicate: (Iterator<T>) -> TypeExpression<BooleanType>,
) = toDopeType().any(iteratorName, predicate)

fun <T : ValidType> ISelectOffsetClause<T>.any(
    iteratorName: String? = null,
    predicate: (Iterator<T>) -> TypeExpression<BooleanType>,
) = asExpression().any(iteratorName, predicate)

fun <T : ValidType> SingleExpression<ArrayType<T>>.every(
    iteratorName: String? = null,
    predicate: (Iterator<T>) -> TypeExpression<BooleanType>,
) = EverySatisfiesExpression(this, iteratorName, predicate)

fun <T : ValidType> Collection<TypeExpression<T>>.every(
    iteratorName: String? = null,
    predicate: (Iterator<T>) -> TypeExpression<BooleanType>,
) = toDopeType().every(iteratorName, predicate)

fun <T : ValidType> ISelectOffsetClause<T>.every(
    iteratorName: String? = null,
    predicate: (Iterator<T>) -> TypeExpression<BooleanType>,
) = asExpression().every(iteratorName, predicate)
