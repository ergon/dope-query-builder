package ch.ergon.dope.extension.expression.type.range

import ch.ergon.dope.extension.expression.type.ObjectField
import ch.ergon.dope.extension.expression.type.ObjectList
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.collection.Iterator
import ch.ergon.dope.resolvable.expression.type.range.MembershipType.IN
import ch.ergon.dope.resolvable.expression.type.range.RangeLike
import ch.ergon.dope.resolvable.expression.type.range.TransformationType
import ch.ergon.dope.resolvable.expression.type.range.TransformationType.ARRAY
import ch.ergon.dope.resolvable.expression.type.range.TransformationType.FIRST
import ch.ergon.dope.resolvable.expression.type.range.TransformationType.OBJECT
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType
import com.schwarz.crystalapi.schema.CMObjectList
import com.schwarz.crystalapi.schema.Schema

abstract class RangeSchemaExpression<S : Schema, T : ValidType, V : ValidType>(
    override val transformationType: TransformationType,
    val cmRange: ObjectList<S>,
    override val iteratorName: String?,
    open val cmWithAttributeKeys: ((ObjectField<S>) -> TypeExpression<StringType>)?,
    open val cmTransformation: (ObjectField<S>) -> TypeExpression<T>,
    open val cmCondition: ((ObjectField<S>) -> TypeExpression<BooleanType>)?,
) : RangeLike<ObjectType, T, V> {
    override val membershipType = IN
    override val range: TypeExpression<ArrayType<ObjectType>> get() = this.cmRange
    override val withAttributeKeys: ((Iterator<ObjectType>) -> TypeExpression<StringType>)?
        get() = this.cmWithAttributeKeys?.let { fn -> { iterator -> fn(ObjectField(cmRange.schema, iterator.variable)) } }
    override val transformation: (Iterator<ObjectType>) -> TypeExpression<T>
        get() = { iterator -> this.cmTransformation(ObjectField(cmRange.schema, iterator.variable)) }
    override val condition: ((Iterator<ObjectType>) -> TypeExpression<BooleanType>)?
        get() = this.cmCondition?.let { fn -> { iterator -> fn(ObjectField(cmRange.schema, iterator.variable)) } }
}

data class FilterRangeSchemaExpression<S : Schema>(
    val range: ObjectList<S>,
    val iteratorName: String? = null,
    val condition: (ObjectField<S>) -> TypeExpression<BooleanType>,
) : TypeExpression<ArrayType<ObjectType>> {
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

data class ArrayRangeSchemaExpression<S : Schema, T : ValidType>(
    override val range: ObjectList<S>,
    override val iteratorName: String? = null,
    val objectTransformation: (ObjectField<S>) -> TypeExpression<T>,
    val objectCondition: ((ObjectField<S>) -> TypeExpression<BooleanType>)? = null,
) : RangeSchemaExpression<S, T, ArrayType<T>>(
    transformationType = ARRAY,
    cmRange = range,
    iteratorName = iteratorName,
    cmWithAttributeKeys = null,
    cmTransformation = objectTransformation,
    cmCondition = objectCondition,
) {
    fun first() = FirstRangeSchemaExpression(
        range = range,
        iteratorName = iteratorName,
        cmTransformation = objectTransformation,
        cmCondition = objectCondition,
    )

    fun toObject(withAttributeKeys: (ObjectField<S>) -> TypeExpression<StringType>) =
        ObjectRangeSchemaExpression(
            range = range,
            iteratorName = iteratorName,
            cmTransformation = objectTransformation,
            cmCondition = objectCondition,
            cmWithAttributeKeys = withAttributeKeys,
        )
}

fun <S : Schema, T : ValidType> CMObjectList<S>.map(
    iteratorName: String? = null,
    transformation: (ObjectField<S>) -> TypeExpression<T>,
) = ArrayRangeSchemaExpression(
    range = toDopeType(),
    iteratorName = iteratorName,
    objectTransformation = transformation,
)

data class FirstRangeSchemaExpression<S : Schema, T : ValidType>(
    override val range: ObjectList<S>,
    override val iteratorName: String? = null,
    override val cmTransformation: (ObjectField<S>) -> TypeExpression<T>,
    override val cmCondition: ((ObjectField<S>) -> TypeExpression<BooleanType>)? = null,
) : RangeSchemaExpression<S, T, T>(
    transformationType = FIRST,
    cmRange = range,
    iteratorName = iteratorName,
    cmWithAttributeKeys = null,
    cmTransformation = cmTransformation,
    cmCondition = cmCondition,
)

data class ObjectRangeSchemaExpression<S : Schema, T : ValidType>(
    override val range: ObjectList<S>,
    override val iteratorName: String? = null,
    override val cmWithAttributeKeys: ((ObjectField<S>) -> TypeExpression<StringType>),
    override val cmTransformation: (ObjectField<S>) -> TypeExpression<T>,
    override val cmCondition: ((ObjectField<S>) -> TypeExpression<BooleanType>)? = null,
) : RangeSchemaExpression<S, T, ObjectType>(
    transformationType = OBJECT,
    cmRange = range,
    iteratorName = iteratorName,
    cmWithAttributeKeys = cmWithAttributeKeys,
    cmTransformation = cmTransformation,
    cmCondition = cmCondition,
)
