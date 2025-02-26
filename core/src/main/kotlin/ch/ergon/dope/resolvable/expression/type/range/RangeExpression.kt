package ch.ergon.dope.resolvable.expression.type.range

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.collection.Iterator
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

const val FOR = "FOR"
const val WHEN = "WHEN"
const val END = "END"

enum class TransformationType(val queryString: String) {
    ARRAY("ARRAY"),
    FIRST("FIRST"),
    OBJECT("OBJECT"),
}

enum class MembershipType(val queryString: String) {
    IN("IN"),
    WITHIN("WITHIN"),
}

sealed class RangeExpression<T : ValidType, U : ValidType> : Resolvable {
    abstract val transformationType: TransformationType
    abstract val membershipType: MembershipType
    abstract val range: TypeExpression<ArrayType<T>>
    abstract val iteratorName: String?
    abstract val withAttributeKeys: ((Iterator<T>) -> TypeExpression<StringType>)?
    abstract val transformation: (Iterator<T>) -> TypeExpression<U>
    abstract val condition: ((Iterator<T>) -> TypeExpression<BooleanType>)?

    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val rangeDopeQuery = range.toDopeQuery(manager)
        val iteratorVariable = iteratorName ?: manager.iteratorManager.getIteratorName()
        val iterator = Iterator<T>(iteratorVariable)
        val withAttributeKeysDopeQuery = withAttributeKeys?.let { it(iterator) }?.toDopeQuery(manager)
        val transformationDopeQuery = transformation(iterator).toDopeQuery(manager)
        val conditionDopeQuery = condition?.let { it(iterator) }?.toDopeQuery(manager)
        return DopeQuery(
            queryString = "${transformationType.queryString} " +
                withAttributeKeysDopeQuery?.let { "${withAttributeKeysDopeQuery.queryString}:" }.orEmpty() +
                "${transformationDopeQuery.queryString} $FOR `$iteratorVariable` " +
                "${membershipType.queryString} ${rangeDopeQuery.queryString} " +
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
