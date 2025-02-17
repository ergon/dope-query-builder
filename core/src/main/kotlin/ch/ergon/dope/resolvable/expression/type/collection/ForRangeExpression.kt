package ch.ergon.dope.resolvable.expression.unaliased.type.collection

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.MembershipType.IN
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.MembershipType.WITHIN
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

const val FOR = "FOR"
const val WHEN = "WHEN"
const val END = "END"

enum class TransformationType(val type: String) {
    ARRAY("ARRAY"),
    FIRST("FIRST"),
    OBJECT("OBJECT"),
}

enum class MembershipType(val type: String) {
    IN("IN"),
    WITHIN("WITHIN"),
}

abstract class ForRangeExpression<T : ValidType, U : ValidType>(
    private val transformationType: TransformationType,
    private val membershipType: MembershipType,
    private val range: TypeExpression<ArrayType<T>>,
    private val iteratorName: String? = null,
    private val withAttributeKeys: ((Iterator<T>) -> TypeExpression<StringType>)? = null,
    private val transformation: (Iterator<T>) -> TypeExpression<U>,
    private val condition: ((Iterator<T>) -> TypeExpression<BooleanType>)? = null,
) : Resolvable {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val rangeDopeQuery = range.toDopeQuery(manager)
        val iteratorVariable = iteratorName ?: manager.iteratorManager.getIteratorName()
        val iterator = Iterator<T>(iteratorVariable)
        val withAttributeKeysDopeQuery = withAttributeKeys?.let { it(iterator) }?.toDopeQuery(manager)
        val transformationDopeQuery = transformation(iterator).toDopeQuery(manager)
        val conditionDopeQuery = condition?.let { it(iterator) }?.toDopeQuery(manager)
        return DopeQuery(
            queryString = "${transformationType.type} " +
                withAttributeKeysDopeQuery?.let { "${withAttributeKeysDopeQuery.queryString}:" }.orEmpty() +
                "${transformationDopeQuery.queryString} $FOR `$iteratorVariable` " +
                "${membershipType.type} ${rangeDopeQuery.queryString} " +
                conditionDopeQuery?.let { "$WHEN ${conditionDopeQuery.queryString} " }.orEmpty() +
                END,
            parameters = rangeDopeQuery.parameters.merge(
                withAttributeKeysDopeQuery?.parameters,
                transformationDopeQuery.parameters,
                conditionDopeQuery?.parameters,
            ),
        )
    }
}

class ForRangeConditionExpression<T : ValidType>(
    private val membershipType: MembershipType,
    private val range: TypeExpression<ArrayType<T>>,
    private val iteratorName: String? = null,
    private val condition: (Iterator<T>) -> TypeExpression<BooleanType>,
) : ArrayForRangeExpression<T, T>(
    membershipType = membershipType,
    range = range,
    iteratorName = iteratorName,
    transformation = { it },
    condition = condition,
) {
    fun <U : ValidType> map(
        transformation: (Iterator<T>) -> TypeExpression<U>,
    ) = ArrayForRangeExpression(
        membershipType = membershipType,
        range = range,
        iteratorName = iteratorName,
        transformation = transformation,
        condition = condition,
    )
}

fun <T : ValidType> TypeExpression<ArrayType<T>>.filter(
    iteratorName: String? = null,
    condition: (Iterator<T>) -> TypeExpression<BooleanType>,
) = ForRangeConditionExpression(IN, range = this, iteratorName, condition)

fun TypeExpression<ArrayType<ValidType>>.filterUnnested(
    iteratorName: String? = null,
    condition: (Iterator<out ValidType>) -> TypeExpression<BooleanType>,
) = ForRangeConditionExpression(WITHIN, range = this, iteratorName, condition)

abstract class ForRangeIndexedExpression<T : ValidType, U : ValidType>(
    private val transformationType: TransformationType,
    private val membershipType: MembershipType,
    private val range: TypeExpression<ArrayType<T>>,
    private val iteratorName: String? = null,
    private val indexName: String? = null,
    private val withAttributeKeys: ((Iterator<T>, Iterator<NumberType>) -> TypeExpression<StringType>)? = null,
    private val transformation: (Iterator<T>, Iterator<NumberType>) -> TypeExpression<U>,
    private val condition: ((Iterator<T>, Iterator<NumberType>) -> TypeExpression<BooleanType>)? = null,
) : Resolvable {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val rangeDopeQuery = range.toDopeQuery(manager)
        val iteratorVariable = iteratorName ?: manager.iteratorManager.getIteratorName()
        val indexVariable = indexName ?: manager.iteratorManager.getIteratorName()
        val iterator = Iterator<T>(iteratorVariable)
        val index = Iterator<NumberType>(indexVariable)
        val withAttributeKeysDopeQuery = withAttributeKeys?.let { it(iterator, index) }?.toDopeQuery(manager)
        val transformationDopeQuery = transformation(iterator, index).toDopeQuery(manager)
        val conditionDopeQuery = condition?.let { it(iterator, index) }?.toDopeQuery(manager)
        return DopeQuery(
            queryString = "${transformationType.type} " +
                withAttributeKeysDopeQuery?.let { "${withAttributeKeysDopeQuery.queryString}:" }.orEmpty() +
                "${transformationDopeQuery.queryString} $FOR `$indexVariable`:`$iteratorVariable` " +
                "${membershipType.type} ${rangeDopeQuery.queryString} " +
                conditionDopeQuery?.let { "$WHEN ${conditionDopeQuery.queryString} " }.orEmpty() +
                END,
            parameters = rangeDopeQuery.parameters.merge(
                withAttributeKeysDopeQuery?.parameters,
                transformationDopeQuery.parameters,
                conditionDopeQuery?.parameters,
            ),
        )
    }
}

class ForRangeIndexedConditionExpression<T : ValidType>(
    private val membershipType: MembershipType,
    private val range: TypeExpression<ArrayType<T>>,
    private val iteratorName: String? = null,
    private val indexName: String? = null,
    private val condition: (Iterator<T>, Iterator<NumberType>) -> TypeExpression<BooleanType>,
) : ArrayForRangeIndexedExpression<T, T>(
    membershipType = membershipType,
    range = range,
    iteratorName = iteratorName,
    indexName = indexName,
    transformation = { it, _ -> it },
    condition = condition,
) {
    fun <U : ValidType> map(
        transformation: (Iterator<T>, Iterator<NumberType>) -> TypeExpression<U>,
    ) = ArrayForRangeIndexedExpression(
        membershipType = membershipType,
        range = range,
        iteratorName = iteratorName,
        indexName = indexName,
        transformation = transformation,
        condition = condition,
    )
}

fun <T : ValidType> TypeExpression<ArrayType<T>>.filterIndexed(
    iteratorName: String? = null,
    indexName: String? = null,
    condition: (Iterator<T>, Iterator<NumberType>) -> TypeExpression<BooleanType>,
) = ForRangeIndexedConditionExpression(IN, range = this, iteratorName, indexName, condition)

fun TypeExpression<ArrayType<ValidType>>.filterIndexedUnnested(
    iteratorName: String? = null,
    indexName: String? = null,
    condition: (Iterator<out ValidType>, Iterator<NumberType>) -> TypeExpression<BooleanType>,
) = ForRangeIndexedConditionExpression(WITHIN, range = this, iteratorName, indexName, condition)
