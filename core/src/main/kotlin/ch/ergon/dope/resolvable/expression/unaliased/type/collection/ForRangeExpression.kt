package ch.ergon.dope.resolvable.expression.unaliased.type.collection

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

private const val FOR = "FOR"
private const val WHEN = "WHEN"
private const val END = "END"

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
    private val indexName: String? = null,
    private val withAttributeKeys: ((Iterator<T>, Iterator<NumberType>) -> TypeExpression<StringType>)? = null,
    private val transformation: (Iterator<T>, Iterator<NumberType>) -> TypeExpression<U>,
    private val condition: ((Iterator<T>, Iterator<NumberType>) -> TypeExpression<BooleanType>)? = null,
) : Resolvable {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val rangeDopeQuery = range.toDopeQuery(manager)
        val iteratorVariable = iteratorName ?: manager.iteratorManager.getIteratorName()
        val indexVariable = indexName ?: manager.iteratorManager.getIteratorName()
        val iter = Iterator<T>(iteratorVariable)
        val index = Iterator<NumberType>(indexVariable)
        val transformationDopeQuery = transformation(iter, index).toDopeQuery(manager)
        val conditionDopeQuery = condition?.let { it(iter, index) }?.toDopeQuery(manager)
        val uniqueKeyDopeQuery = withAttributeKeys?.let { it(iter, index) }?.toDopeQuery(manager)
        return DopeQuery(
            queryString = "${transformationType.type} " +
                uniqueKeyDopeQuery?.let { "${uniqueKeyDopeQuery.queryString}:" }.orEmpty() +
                "${transformationDopeQuery.queryString} $FOR `$indexVariable`:`$iteratorVariable` " +
                "${membershipType.type} ${rangeDopeQuery.queryString} " +
                conditionDopeQuery?.let { "$WHEN ${conditionDopeQuery.queryString} " }.orEmpty() +
                END,
            parameters = rangeDopeQuery.parameters.merge(
                uniqueKeyDopeQuery?.parameters,
                transformationDopeQuery.parameters,
                conditionDopeQuery?.parameters,
            ),
        )
    }
}

class ForRangeConditionExpression<T : ValidType>(
    private val range: TypeExpression<ArrayType<T>>,
    private val condition: ((Iterator<T>, Iterator<NumberType>) -> TypeExpression<BooleanType>),
) {
    fun <U : ValidType> mapIndexed(
        iteratorName: String? = null,
        indexName: String? = null,
        transformation: (Iterator<T>, Iterator<NumberType>) -> TypeExpression<U>,
    ) = ArrayForRangeExpression(
        range = range,
        iteratorName = iteratorName,
        indexName = indexName,
        transformation = transformation,
        condition = condition,
    )

    fun <U : ValidType> mapIndexedUnnested(
        iteratorName: String? = null,
        indexName: String? = null,
        transformation: (Iterator<T>, Iterator<NumberType>) -> TypeExpression<U>,
    ) = ArrayForUnnestedRangeExpression(
        range = range,
        iteratorName = iteratorName,
        indexName = indexName,
        transformation = transformation,
        condition = condition,
    )
}

fun <T : ValidType> TypeExpression<ArrayType<T>>.filter(
    condition: (Iterator<T>, Iterator<NumberType>) -> TypeExpression<BooleanType>,
) = ForRangeConditionExpression(this, condition)
