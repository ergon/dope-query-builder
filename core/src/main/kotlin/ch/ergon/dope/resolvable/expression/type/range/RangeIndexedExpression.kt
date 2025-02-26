package ch.ergon.dope.resolvable.expression.type.range

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.collection.Iterator
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

abstract class RangeIndexedExpression<T : ValidType, U : ValidType> : Resolvable {
    abstract val transformationType: TransformationType
    abstract val membershipType: MembershipType
    abstract val range: TypeExpression<ArrayType<T>>
    abstract val indexName: String?
    abstract val iteratorName: String?
    abstract val withAttributeKeys: ((Iterator<NumberType>, Iterator<T>) -> TypeExpression<StringType>)?
    abstract val transformation: (Iterator<NumberType>, Iterator<T>) -> TypeExpression<U>
    abstract val condition: ((Iterator<NumberType>, Iterator<T>) -> TypeExpression<BooleanType>)?
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val rangeDopeQuery = range.toDopeQuery(manager)
        val indexVariable = indexName ?: manager.iteratorManager.getIteratorName()
        val iteratorVariable = iteratorName ?: manager.iteratorManager.getIteratorName()
        val index = Iterator<NumberType>(indexVariable)
        val iterator = Iterator<T>(iteratorVariable)
        val withAttributeKeysDopeQuery = withAttributeKeys?.let { it(index, iterator) }?.toDopeQuery(manager)
        val transformationDopeQuery = transformation(index, iterator).toDopeQuery(manager)
        val conditionDopeQuery = condition?.let { it(index, iterator) }?.toDopeQuery(manager)
        return DopeQuery(
            queryString = "${transformationType.queryString} " +
                withAttributeKeysDopeQuery?.let { "${withAttributeKeysDopeQuery.queryString}:" }.orEmpty() +
                "${transformationDopeQuery.queryString} $FOR `$indexVariable`:`$iteratorVariable` " +
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
