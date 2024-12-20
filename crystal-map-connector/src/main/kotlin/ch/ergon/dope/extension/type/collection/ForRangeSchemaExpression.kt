package ch.ergon.dope.extension.type.collection

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.type.ObjectField
import ch.ergon.dope.extension.type.ObjectList
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.END
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.FOR
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.MembershipType.IN
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.TransformationType
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.TransformationType.ARRAY
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.TransformationType.FIRST
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.TransformationType.OBJECT
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.WHEN
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType
import com.schwarz.crystalapi.schema.CMObjectList
import com.schwarz.crystalapi.schema.Schema

abstract class ForRangeSchemaExpression<S : Schema, T : ValidType>(
    private val transformationType: TransformationType,
    private val range: ObjectList<S>,
    private val iteratorName: String? = null,
    private val withAttributeKeys: ((ObjectField<S>) -> TypeExpression<StringType>)? = null,
    private val transformation: (ObjectField<S>) -> TypeExpression<T>,
    private val condition: ((ObjectField<S>) -> TypeExpression<BooleanType>)? = null,
) : Resolvable {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val rangeDopeQuery = range.toDopeQuery(manager)
        val iteratorVariable = iteratorName ?: manager.iteratorManager.getIteratorName()
        val withAttributeKeysDopeQuery = withAttributeKeys?.let {
            it(ObjectField(range.schema, iteratorVariable, ""))
        }?.toDopeQuery(manager)
        val transformationDopeQuery = transformation(ObjectField(range.schema, iteratorVariable, "")).toDopeQuery(manager)
        val conditionDopeQuery = condition?.let {
            it(ObjectField(range.schema, iteratorVariable, ""))
        }?.toDopeQuery(manager)
        return DopeQuery(
            queryString = "${transformationType.type} " +
                withAttributeKeys?.let { "${withAttributeKeysDopeQuery?.queryString}:" }.orEmpty() +
                "${transformationDopeQuery.queryString} $FOR `$iteratorVariable` " +
                "${IN.type} ${rangeDopeQuery.queryString} " +
                conditionDopeQuery?.let { "$WHEN ${conditionDopeQuery.queryString} " }.orEmpty() +
                END,
        )
    }
}

class ForRangeConditionSchemaExpression<S : Schema>(
    private val range: ObjectList<S>,
    private val iteratorName: String? = null,
    private val condition: (ObjectField<S>) -> TypeExpression<BooleanType>,
) {
    fun <T : ValidType> map(
        transformation: (ObjectField<S>) -> TypeExpression<T>,
    ) = ArrayForRangeSchemaExpression(
        range,
        iteratorName,
        transformation,
        condition,
    )
}

fun <S : Schema> CMObjectList<S>.filter(
    iteratorName: String? = null,
    condition: (ObjectField<S>) -> TypeExpression<BooleanType>,
) = ForRangeConditionSchemaExpression(
    range = toDopeType(),
    iteratorName = iteratorName,
    condition = condition,
)

class ArrayForRangeSchemaExpression<S : Schema, T : ValidType>(
    private val range: ObjectList<S>,
    private val iteratorName: String? = null,
    private val transformation: (ObjectField<S>) -> TypeExpression<T>,
    private val condition: ((ObjectField<S>) -> TypeExpression<BooleanType>)? = null,
) : TypeExpression<ArrayType<T>>, ForRangeSchemaExpression<S, T>(
    transformationType = ARRAY,
    range = range,
    iteratorName = iteratorName,
    transformation = transformation,
    condition = condition,
) {
    fun first() = FirstForRangeSchemaExpression(
        range = range,
        iteratorName = iteratorName,
        transformation = transformation,
        condition = condition,
    )

    fun toObject(withAttributeKeys: (ObjectField<S>) -> TypeExpression<StringType>) =
        ObjectForRangeSchemaExpression(
            range = range,
            iteratorName = iteratorName,
            transformation = transformation,
            condition = condition,
            withAttributeKeys = withAttributeKeys,
        )
}

fun <S : Schema, T : ValidType> CMObjectList<S>.map(
    iteratorName: String? = null,
    transformation: (ObjectField<S>) -> TypeExpression<T>,
) = ArrayForRangeSchemaExpression(
    range = toDopeType(),
    iteratorName = iteratorName,
    transformation = transformation,
)

class FirstForRangeSchemaExpression<S : Schema, T : ValidType>(
    range: ObjectList<S>,
    iteratorName: String? = null,
    transformation: (ObjectField<S>) -> TypeExpression<T>,
    condition: ((ObjectField<S>) -> TypeExpression<BooleanType>)? = null,
) : TypeExpression<T>, ForRangeSchemaExpression<S, T>(
    transformationType = FIRST,
    range = range,
    iteratorName = iteratorName,
    transformation = transformation,
    condition = condition,
)

class ObjectForRangeSchemaExpression<S : Schema, T : ValidType>(
    range: ObjectList<S>,
    iteratorName: String? = null,
    withAttributeKeys: ((ObjectField<S>) -> TypeExpression<StringType>),
    transformation: (ObjectField<S>) -> TypeExpression<T>,
    condition: ((ObjectField<S>) -> TypeExpression<BooleanType>)? = null,
) : TypeExpression<ObjectType>, ForRangeSchemaExpression<S, T>(
    transformationType = OBJECT,
    range = range,
    iteratorName = iteratorName,
    withAttributeKeys = withAttributeKeys,
    transformation = transformation,
    condition = condition,
)
