package ch.ergon.dope.extension.expression.type.range

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.expression.type.ObjectField
import ch.ergon.dope.extension.expression.type.ObjectList
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.range.END
import ch.ergon.dope.resolvable.expression.type.range.FOR
import ch.ergon.dope.resolvable.expression.type.range.MembershipType.IN
import ch.ergon.dope.resolvable.expression.type.range.TransformationType
import ch.ergon.dope.resolvable.expression.type.range.TransformationType.ARRAY
import ch.ergon.dope.resolvable.expression.type.range.TransformationType.FIRST
import ch.ergon.dope.resolvable.expression.type.range.TransformationType.OBJECT
import ch.ergon.dope.resolvable.expression.type.range.WHEN
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType
import com.schwarz.crystalapi.schema.CMObjectList
import com.schwarz.crystalapi.schema.Schema

abstract class RangeSchemaExpression<S : Schema, T : ValidType>(
    private val transformationType: TransformationType,
    private val range: ObjectList<S>,
    private val iteratorName: String?,
    private val withAttributeKeys: ((ObjectField<S>) -> TypeExpression<StringType>)?,
    private val transformation: (ObjectField<S>) -> TypeExpression<T>,
    private val condition: ((ObjectField<S>) -> TypeExpression<BooleanType>)?,
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
            queryString = "${transformationType.queryString} " +
                withAttributeKeys?.let { "${withAttributeKeysDopeQuery?.queryString}:" }.orEmpty() +
                "${transformationDopeQuery.queryString} $FOR `$iteratorVariable` " +
                "${IN.queryString} ${rangeDopeQuery.queryString} " +
                conditionDopeQuery?.let { "$WHEN ${conditionDopeQuery.queryString} " }.orEmpty() +
                END,
        )
    }
}

class FilterRangeSchemaExpression<S : Schema>(
    private val range: ObjectList<S>,
    private val iteratorName: String? = null,
    private val condition: (ObjectField<S>) -> TypeExpression<BooleanType>,
) {
    fun <T : ValidType> map(
        transformation: (ObjectField<S>) -> TypeExpression<T>,
    ) = ArrayRangeSchemaExpression(
        range,
        iteratorName,
        transformation,
        condition,
    )
}

fun <S : Schema> CMObjectList<S>.filter(
    iteratorName: String? = null,
    condition: (ObjectField<S>) -> TypeExpression<BooleanType>,
) = FilterRangeSchemaExpression(
    range = toDopeType(),
    iteratorName = iteratorName,
    condition = condition,
)

class ArrayRangeSchemaExpression<S : Schema, T : ValidType>(
    private val range: ObjectList<S>,
    private val iteratorName: String? = null,
    private val transformation: (ObjectField<S>) -> TypeExpression<T>,
    private val condition: ((ObjectField<S>) -> TypeExpression<BooleanType>)? = null,
) : TypeExpression<ArrayType<T>>, RangeSchemaExpression<S, T>(
    transformationType = ARRAY,
    range = range,
    iteratorName = iteratorName,
    withAttributeKeys = null,
    transformation = transformation,
    condition = condition,
) {
    fun first() = FirstRangeSchemaExpression(
        range = range,
        iteratorName = iteratorName,
        transformation = transformation,
        condition = condition,
    )

    fun toObject(withAttributeKeys: (ObjectField<S>) -> TypeExpression<StringType>) =
        ObjectRangeSchemaExpression(
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
) = ArrayRangeSchemaExpression(
    range = toDopeType(),
    iteratorName = iteratorName,
    transformation = transformation,
)

class FirstRangeSchemaExpression<S : Schema, T : ValidType>(
    range: ObjectList<S>,
    iteratorName: String? = null,
    transformation: (ObjectField<S>) -> TypeExpression<T>,
    condition: ((ObjectField<S>) -> TypeExpression<BooleanType>)? = null,
) : TypeExpression<T>, RangeSchemaExpression<S, T>(
    transformationType = FIRST,
    range = range,
    iteratorName = iteratorName,
    withAttributeKeys = null,
    transformation = transformation,
    condition = condition,
)

class ObjectRangeSchemaExpression<S : Schema, T : ValidType>(
    range: ObjectList<S>,
    iteratorName: String? = null,
    withAttributeKeys: ((ObjectField<S>) -> TypeExpression<StringType>),
    transformation: (ObjectField<S>) -> TypeExpression<T>,
    condition: ((ObjectField<S>) -> TypeExpression<BooleanType>)? = null,
) : TypeExpression<ObjectType>, RangeSchemaExpression<S, T>(
    transformationType = OBJECT,
    range = range,
    iteratorName = iteratorName,
    withAttributeKeys = withAttributeKeys,
    transformation = transformation,
    condition = condition,
)
