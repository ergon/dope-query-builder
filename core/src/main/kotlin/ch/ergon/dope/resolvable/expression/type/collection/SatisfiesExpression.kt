package ch.ergon.dope.resolvable.expression.type.collection

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
    val satisfiesType: SatisfiesType,
    open val arrayExpression: SingleExpression<ArrayType<T>>,
    open val iteratorName: String? = null,
    open val predicate: (Iterator<T>) -> TypeExpression<BooleanType>,
) : TypeExpression<BooleanType>

data class AnySatisfiesExpression<T : ValidType>(
    override val arrayExpression: SingleExpression<ArrayType<T>>,
    override val iteratorName: String? = null,
    override val predicate: (Iterator<T>) -> TypeExpression<BooleanType>,
) : SatisfiesExpression<T>(ANY, arrayExpression, iteratorName, predicate)

data class EverySatisfiesExpression<T : ValidType>(
    override val arrayExpression: SingleExpression<ArrayType<T>>,
    override val iteratorName: String? = null,
    override val predicate: (Iterator<T>) -> TypeExpression<BooleanType>,
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
