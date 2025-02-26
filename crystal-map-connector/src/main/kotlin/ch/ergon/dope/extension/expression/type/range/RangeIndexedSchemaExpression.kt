package ch.ergon.dope.extension.expression.type.range

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.expression.type.ObjectField
import ch.ergon.dope.extension.expression.type.ObjectList
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.collection.Iterator
import ch.ergon.dope.resolvable.expression.type.range.END
import ch.ergon.dope.resolvable.expression.type.range.FOR
import ch.ergon.dope.resolvable.expression.type.range.MembershipType
import ch.ergon.dope.resolvable.expression.type.range.TransformationType
import ch.ergon.dope.resolvable.expression.type.range.TransformationType.ARRAY
import ch.ergon.dope.resolvable.expression.type.range.TransformationType.FIRST
import ch.ergon.dope.resolvable.expression.type.range.TransformationType.OBJECT
import ch.ergon.dope.resolvable.expression.type.range.WHEN
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType
import com.schwarz.crystalapi.schema.CMObjectList
import com.schwarz.crystalapi.schema.Schema

abstract class RangeIndexedSchemaExpression<S : Schema, T : ValidType>(
    private val transformationType: TransformationType,
    private val range: ObjectList<S>,
    private val indexName: String?,
    private val iteratorName: String?,
    private val withAttributeKeys: ((Iterator<NumberType>, ObjectField<S>) -> TypeExpression<StringType>)?,
    private val transformation: (Iterator<NumberType>, ObjectField<S>) -> TypeExpression<T>,
    private val condition: ((Iterator<NumberType>, ObjectField<S>) -> TypeExpression<BooleanType>)?,
) : Resolvable {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val rangeDopeQuery = range.toDopeQuery(manager)
        val indexVariable = indexName ?: manager.iteratorManager.getIteratorName()
        val iteratorVariable = iteratorName ?: manager.iteratorManager.getIteratorName()
        val index = Iterator<NumberType>(indexVariable)
        val withAttributeKeysDopeQuery = withAttributeKeys?.let {
            it(index, ObjectField(range.schema, iteratorVariable, ""))
        }?.toDopeQuery(manager)
        val transformationDopeQuery = transformation(
            index,
            ObjectField(range.schema, iteratorVariable, ""),
        ).toDopeQuery(manager)
        val conditionDopeQuery = condition?.let {
            it(index, ObjectField(range.schema, iteratorVariable, ""))
        }?.toDopeQuery(manager)
        return DopeQuery(
            queryString = "${transformationType.queryString} " +
                withAttributeKeys?.let { "${withAttributeKeysDopeQuery?.queryString}:" }.orEmpty() +
                "${transformationDopeQuery.queryString} $FOR `$indexVariable`:`$iteratorVariable` " +
                "${MembershipType.IN.queryString} ${rangeDopeQuery.queryString} " +
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

class ForRangeIndexedConditionSchemaExpression<S : Schema>(
    private val range: ObjectList<S>,
    private val indexName: String? = null,
    private val iteratorName: String? = null,
    private val condition: (Iterator<NumberType>, ObjectField<S>) -> TypeExpression<BooleanType>,
) {
    fun <T : ValidType> map(
        transformation: (Iterator<NumberType>, ObjectField<S>) -> TypeExpression<T>,
    ) = ArrayRangeIndexedSchemaExpression(
        range = range,
        indexName = indexName,
        iteratorName = iteratorName,
        transformation = transformation,
        condition = condition,
    )
}

fun <S : Schema> CMObjectList<S>.filterIndexed(
    indexName: String? = null,
    iteratorName: String? = null,
    condition: (Iterator<NumberType>, ObjectField<S>) -> TypeExpression<BooleanType>,
) = ForRangeIndexedConditionSchemaExpression(
    range = toDopeType(),
    indexName = indexName,
    iteratorName = iteratorName,
    condition = condition,
)

class ArrayRangeIndexedSchemaExpression<S : Schema, T : ValidType>(
    private val range: ObjectList<S>,
    private val indexName: String? = null,
    private val iteratorName: String? = null,
    private val transformation: (Iterator<NumberType>, ObjectField<S>) -> TypeExpression<T>,
    private val condition: ((Iterator<NumberType>, ObjectField<S>) -> TypeExpression<BooleanType>)? = null,
) : TypeExpression<ArrayType<T>>, RangeIndexedSchemaExpression<S, T>(
    transformationType = ARRAY,
    range = range,
    indexName = indexName,
    iteratorName = iteratorName,
    withAttributeKeys = null,
    transformation = transformation,
    condition = condition,
) {
    fun first() = FirstRangeIndexedSchemaExpression(
        range = range,
        indexName = indexName,
        iteratorName = iteratorName,
        transformation = transformation,
        condition = condition,
    )

    fun toObject(withAttributeKeys: (Iterator<NumberType>, ObjectField<S>) -> TypeExpression<StringType>) =
        ObjectRangeIndexedSchemaExpression(
            range = range,
            indexName = indexName,
            iteratorName = iteratorName,
            transformation = transformation,
            condition = condition,
            withAttributeKeys = withAttributeKeys,
        )
}

fun <S : Schema, T : ValidType> CMObjectList<S>.mapIndexed(
    indexName: String? = null,
    iteratorName: String? = null,
    transformation: (Iterator<NumberType>, ObjectField<S>) -> TypeExpression<T>,
) = ArrayRangeIndexedSchemaExpression(
    range = toDopeType(),
    indexName = indexName,
    iteratorName = iteratorName,
    transformation = transformation,
)

class FirstRangeIndexedSchemaExpression<S : Schema, T : ValidType>(
    range: ObjectList<S>,
    indexName: String? = null,
    iteratorName: String? = null,
    transformation: (Iterator<NumberType>, ObjectField<S>) -> TypeExpression<T>,
    condition: ((Iterator<NumberType>, ObjectField<S>) -> TypeExpression<BooleanType>)? = null,
) : TypeExpression<T>, RangeIndexedSchemaExpression<S, T>(
    transformationType = FIRST,
    range = range,
    indexName = indexName,
    iteratorName = iteratorName,
    withAttributeKeys = null,
    transformation = transformation,
    condition = condition,
)

class ObjectRangeIndexedSchemaExpression<S : Schema, T : ValidType>(
    range: ObjectList<S>,
    indexName: String? = null,
    iteratorName: String? = null,
    withAttributeKeys: ((Iterator<NumberType>, ObjectField<S>) -> TypeExpression<StringType>),
    transformation: (Iterator<NumberType>, ObjectField<S>) -> TypeExpression<T>,
    condition: ((Iterator<NumberType>, ObjectField<S>) -> TypeExpression<BooleanType>)? = null,
) : TypeExpression<ObjectType>, RangeIndexedSchemaExpression<S, T>(
    transformationType = OBJECT,
    range = range,
    indexName = indexName,
    iteratorName = iteratorName,
    withAttributeKeys = withAttributeKeys,
    transformation = transformation,
    condition = condition,
)
