package ch.ergon.dope.extension.type.collection

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.type.ObjectField
import ch.ergon.dope.extension.type.ObjectList
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.END
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.FOR
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.Iterator
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.MembershipType
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.TransformationType
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.TransformationType.ARRAY
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.TransformationType.FIRST
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.TransformationType.OBJECT
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.WHEN
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType
import com.schwarz.crystalapi.schema.CMObjectList
import com.schwarz.crystalapi.schema.Schema

abstract class ForRangeIndexedSchemaExpression<S : Schema, T : ValidType>(
    private val transformationType: TransformationType,
    private val range: ObjectList<S>,
    private val iteratorName: String? = null,
    private val indexName: String? = null,
    private val withAttributeKeys: ((ObjectField<S>, Iterator<NumberType>) -> TypeExpression<StringType>)? = null,
    private val transformation: (ObjectField<S>, Iterator<NumberType>) -> TypeExpression<T>,
    private val condition: ((ObjectField<S>, Iterator<NumberType>) -> TypeExpression<BooleanType>)? = null,
) : Resolvable {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val rangeDopeQuery = range.toDopeQuery(manager)
        val iteratorVariable = iteratorName ?: manager.iteratorManager.getIteratorName()
        val indexVariable = indexName ?: manager.iteratorManager.getIteratorName()
        val index = Iterator<NumberType>(indexVariable)
        val withAttributeKeysDopeQuery = withAttributeKeys?.let {
            it(ObjectField(range.schema, iteratorVariable, ""), index)
        }?.toDopeQuery(manager)
        val transformationDopeQuery = transformation(
            ObjectField(range.schema, iteratorVariable, ""),
            index,
        ).toDopeQuery(manager)
        val conditionDopeQuery = condition?.let {
            it(ObjectField(range.schema, iteratorVariable, ""), index)
        }?.toDopeQuery(manager)
        return DopeQuery(
            queryString = "${transformationType.type} " +
                withAttributeKeys?.let { "${withAttributeKeysDopeQuery?.queryString}:" }.orEmpty() +
                "${transformationDopeQuery.queryString} $FOR `$indexVariable`:`$iteratorVariable` " +
                "${MembershipType.IN.type} ${rangeDopeQuery.queryString} " +
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
    private val iteratorName: String? = null,
    private val indexName: String? = null,
    private val condition: (ObjectField<S>, Iterator<NumberType>) -> TypeExpression<BooleanType>,
) {
    fun <T : ValidType> map(
        transformation: (ObjectField<S>, Iterator<NumberType>) -> TypeExpression<T>,
    ) = ArrayForRangeIndexedSchemaExpression(
        range,
        iteratorName,
        indexName,
        transformation,
        condition,
    )
}

fun <S : Schema> CMObjectList<S>.filterIndexed(
    iteratorName: String? = null,
    indexName: String? = null,
    condition: (ObjectField<S>, Iterator<NumberType>) -> TypeExpression<BooleanType>,
) = ForRangeIndexedConditionSchemaExpression(
    range = toDopeType(),
    iteratorName = iteratorName,
    indexName = indexName,
    condition = condition,
)

class ArrayForRangeIndexedSchemaExpression<S : Schema, T : ValidType>(
    private val range: ObjectList<S>,
    private val iteratorName: String? = null,
    private val indexName: String? = null,
    private val transformation: (ObjectField<S>, Iterator<NumberType>) -> TypeExpression<T>,
    private val condition: ((ObjectField<S>, Iterator<NumberType>) -> TypeExpression<BooleanType>)? = null,
) : TypeExpression<ArrayType<T>>, ForRangeIndexedSchemaExpression<S, T>(
    transformationType = ARRAY,
    range = range,
    iteratorName = iteratorName,
    indexName = indexName,
    transformation = transformation,
    condition = condition,
) {
    fun first() = FirstForRangeIndexedSchemaExpression(
        range = range,
        iteratorName = iteratorName,
        indexName = indexName,
        transformation = transformation,
        condition = condition,
    )

    fun toObject(withAttributeKeys: (ObjectField<S>, Iterator<NumberType>) -> TypeExpression<StringType>) =
        ObjectForRangeIndexedSchemaExpression(
            range = range,
            iteratorName = iteratorName,
            indexName = indexName,
            transformation = transformation,
            condition = condition,
            withAttributeKeys = withAttributeKeys,
        )
}

fun <S : Schema, T : ValidType> CMObjectList<S>.mapIndexed(
    iteratorName: String? = null,
    indexName: String? = null,
    transformation: (ObjectField<S>, Iterator<NumberType>) -> TypeExpression<T>,
) = ArrayForRangeIndexedSchemaExpression(
    range = toDopeType(),
    iteratorName = iteratorName,
    indexName = indexName,
    transformation = transformation,
)

class FirstForRangeIndexedSchemaExpression<S : Schema, T : ValidType>(
    range: ObjectList<S>,
    iteratorName: String? = null,
    indexName: String? = null,
    transformation: (ObjectField<S>, Iterator<NumberType>) -> TypeExpression<T>,
    condition: ((ObjectField<S>, Iterator<NumberType>) -> TypeExpression<BooleanType>)? = null,
) : TypeExpression<T>, ForRangeIndexedSchemaExpression<S, T>(
    transformationType = FIRST,
    range = range,
    iteratorName = iteratorName,
    indexName = indexName,
    transformation = transformation,
    condition = condition,
)

class ObjectForRangeIndexedSchemaExpression<S : Schema, T : ValidType>(
    range: ObjectList<S>,
    iteratorName: String? = null,
    indexName: String? = null,
    withAttributeKeys: ((ObjectField<S>, Iterator<NumberType>) -> TypeExpression<StringType>),
    transformation: (ObjectField<S>, Iterator<NumberType>) -> TypeExpression<T>,
    condition: ((ObjectField<S>, Iterator<NumberType>) -> TypeExpression<BooleanType>)? = null,
) : TypeExpression<ObjectType>, ForRangeIndexedSchemaExpression<S, T>(
    transformationType = OBJECT,
    range = range,
    iteratorName = iteratorName,
    indexName = indexName,
    withAttributeKeys = withAttributeKeys,
    transformation = transformation,
    condition = condition,
)
