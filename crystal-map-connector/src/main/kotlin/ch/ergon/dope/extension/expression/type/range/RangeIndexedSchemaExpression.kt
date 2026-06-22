package ch.ergon.dope.extension.expression.type.range

import ch.ergon.dope.extension.expression.type.ObjectField
import ch.ergon.dope.extension.expression.type.ObjectList
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.collection.Iterator
import ch.ergon.dope.resolvable.expression.type.range.MembershipType
import ch.ergon.dope.resolvable.expression.type.range.RangeIndexedLike
import ch.ergon.dope.resolvable.expression.type.range.TransformationType
import ch.ergon.dope.resolvable.expression.type.range.TransformationType.ARRAY
import ch.ergon.dope.resolvable.expression.type.range.TransformationType.FIRST
import ch.ergon.dope.resolvable.expression.type.range.TransformationType.OBJECT
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType
import com.schwarz.crystalapi.schema.CMObjectList
import com.schwarz.crystalapi.schema.Schema

abstract class RangeIndexedSchemaExpression<S : Schema, T : ValidType, V : ValidType>(
    override val transformationType: TransformationType,
    val cmRange: ObjectList<S>,
    override val indexName: String?,
    override val iteratorName: String?,
    open val cmWithAttributeKeys: ((Iterator<NumberType>, ObjectField<S>) -> TypeExpression<StringType>)?,
    open val cmTransformation: (Iterator<NumberType>, ObjectField<S>) -> TypeExpression<T>,
    open val cmCondition: ((Iterator<NumberType>, ObjectField<S>) -> TypeExpression<BooleanType>)?,
) : RangeIndexedLike<ObjectType, T, V> {
    override val membershipType: MembershipType = MembershipType.IN
    override val range: TypeExpression<ArrayType<ObjectType>> get() = this.cmRange
    override val withAttributeKeys: ((Iterator<NumberType>, Iterator<ObjectType>) -> TypeExpression<StringType>)? =
        this.cmWithAttributeKeys?.let { fn ->
            { index, iterator -> fn(index, ObjectField(cmRange.schema, iterator.variable)) }
        }
    override val transformation: (Iterator<NumberType>, Iterator<ObjectType>) -> TypeExpression<T> =
        { index, iterator -> this.cmTransformation(index, ObjectField(cmRange.schema, iterator.variable)) }
    override val condition: ((Iterator<NumberType>, Iterator<ObjectType>) -> TypeExpression<BooleanType>)? =
        this.cmCondition?.let { fn ->
            { index, iterator -> fn(index, ObjectField(cmRange.schema, iterator.variable)) }
        }
}

data class ForRangeIndexedConditionSchemaExpression<S : Schema>(
    val range: ObjectList<S>,
    val indexName: String? = null,
    val iteratorName: String? = null,
    val condition: (Iterator<NumberType>, ObjectField<S>) -> TypeExpression<BooleanType>,
) {
    fun <T : ValidType> map(
        transformation: (Iterator<NumberType>, ObjectField<S>) -> TypeExpression<T>,
    ) = ArrayRangeIndexedSchemaExpression(
        range = range,
        indexName = indexName,
        iteratorName = iteratorName,
        cmTransformation = transformation,
        cmCondition = condition,
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

data class ArrayRangeIndexedSchemaExpression<S : Schema, T : ValidType>(
    override val range: ObjectList<S>,
    override val indexName: String? = null,
    override val iteratorName: String? = null,
    override val cmTransformation: (Iterator<NumberType>, ObjectField<S>) -> TypeExpression<T>,
    override val cmCondition: ((Iterator<NumberType>, ObjectField<S>) -> TypeExpression<BooleanType>)? = null,
) : RangeIndexedSchemaExpression<S, T, ArrayType<T>>(
    transformationType = ARRAY,
    cmRange = range,
    indexName = indexName,
    iteratorName = iteratorName,
    cmWithAttributeKeys = null,
    cmTransformation = cmTransformation,
    cmCondition = cmCondition,
) {
    fun first() = FirstRangeIndexedSchemaExpression(
        range = cmRange,
        indexName = indexName,
        iteratorName = iteratorName,
        cmTransformation = cmTransformation,
        cmCondition = cmCondition,
    )

    fun toObject(withAttributeKeys: (Iterator<NumberType>, ObjectField<S>) -> TypeExpression<StringType>) =
        ObjectRangeIndexedSchemaExpression(
            range = cmRange,
            indexName = indexName,
            iteratorName = iteratorName,
            cmTransformation = cmTransformation,
            cmCondition = cmCondition,
            cmWithAttributeKeys = withAttributeKeys,
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
    cmTransformation = transformation,
)

data class FirstRangeIndexedSchemaExpression<S : Schema, T : ValidType>(
    override val range: ObjectList<S>,
    override val indexName: String? = null,
    override val iteratorName: String? = null,
    override val cmTransformation: (Iterator<NumberType>, ObjectField<S>) -> TypeExpression<T>,
    override val cmCondition: ((Iterator<NumberType>, ObjectField<S>) -> TypeExpression<BooleanType>)? = null,
) : RangeIndexedSchemaExpression<S, T, T>(
    transformationType = FIRST,
    cmRange = range,
    indexName = indexName,
    iteratorName = iteratorName,
    cmWithAttributeKeys = null,
    cmTransformation = cmTransformation,
    cmCondition = cmCondition,
)

data class ObjectRangeIndexedSchemaExpression<S : Schema, T : ValidType>(
    override val range: ObjectList<S>,
    override val indexName: String? = null,
    override val iteratorName: String? = null,
    override val cmWithAttributeKeys: ((Iterator<NumberType>, ObjectField<S>) -> TypeExpression<StringType>),
    override val cmTransformation: (Iterator<NumberType>, ObjectField<S>) -> TypeExpression<T>,
    override val cmCondition: ((Iterator<NumberType>, ObjectField<S>) -> TypeExpression<BooleanType>)? = null,
) : RangeIndexedSchemaExpression<S, T, ObjectType>(
    transformationType = OBJECT,
    cmRange = range,
    indexName = indexName,
    iteratorName = iteratorName,
    cmWithAttributeKeys = cmWithAttributeKeys,
    cmTransformation = cmTransformation,
    cmCondition = cmCondition,
)
