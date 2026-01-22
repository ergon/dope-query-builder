package ch.ergon.dope.helper

import ch.ergon.dope.resolvable.clause.model.OrderExpression
import ch.ergon.dope.resolvable.clause.model.OrderType
import ch.ergon.dope.resolvable.clause.model.OrderType.ASC
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.NullsOrder
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OrderingTerm
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.UnboundedPreceding
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowFrameClause
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowFrameExclusion
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowFrameExtent
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowFrameType
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowFrameType.RANGE
import ch.ergon.dope.resolvable.expression.type.CaseClass
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.resolvable.expression.type.TRUE
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.conditional.SearchResult
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.resolvable.keyspace.Keyspace
import ch.ergon.dope.resolvable.keyspace.UnaliasedKeyspace
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

fun someKeyspace(bucket: String = "someBucket", scope: String? = null, collection: String? = null) = UnaliasedKeyspace(bucket, scope, collection)

fun someNumberField(name: String = "numberField", keyspace: Keyspace? = null) = Field<NumberType>(name, keyspace)

fun someStringField(name: String = "stringField", keyspace: Keyspace? = null) = Field<StringType>(name, keyspace)

fun someBooleanField(name: String = "booleanField", keyspace: Keyspace? = null) = Field<BooleanType>(name, keyspace)

fun someObjectField(name: String = "objectField", keyspace: Keyspace? = null) = Field<ObjectType>(name, keyspace)

fun someAnyTypeField(name: String = "anyTypeField", keyspace: Keyspace? = null) = Field<ValidType>(name, keyspace)

fun someBooleanExpression() = TRUE

fun someNumberArrayField(name: String = "numberArrayField", keyspace: Keyspace? = null) =
    Field<ArrayType<NumberType>>(name, keyspace)

fun someStringArrayField(name: String = "stringArrayField", keyspace: Keyspace? = null) =
    Field<ArrayType<StringType>>(name, keyspace)

fun someBooleanArrayField(name: String = "booleanArrayField", keyspace: Keyspace? = null) =
    Field<ArrayType<BooleanType>>(name, keyspace)

fun someObjectArrayField(name: String = "objectArrayField", keyspace: Keyspace? = null) =
    Field<ArrayType<ObjectType>>(name, keyspace)

fun someAnyTypeArrayField(name: String = "anyTypeArrayField", keyspace: Keyspace? = null) =
    Field<ArrayType<ValidType>>(name, keyspace)

fun someNumber(value: Number = 5) = value

fun someInt(value: Int = 5) = value

fun someString(value: String = "someString") = value

fun someBoolean(value: Boolean = true) = value

fun someObject() = mapOf("key1" to someNumber(), "key2" to someString())

fun <T : ValidType> someCaseClass(expression: TypeExpression<T>) = CaseClass(
    expression,
)

fun someStringSearchNumberResult(
    searchExpression: TypeExpression<StringType> = someString().toDopeType(),
    resultExpression: TypeExpression<NumberType> = someNumber().toDopeType(),
) = SearchResult(searchExpression, resultExpression)

fun someOrderExpression(typeExpression: TypeExpression<StringType> = someStringField(), orderByType: OrderType = ASC) = OrderExpression(
    typeExpression,
    orderByType,
)

fun someOrderingTerm(
    expression: TypeExpression<out ValidType> = someStringField(),
    orderType: OrderType? = null,
    nullsOrder: NullsOrder? = null,
) = OrderingTerm(expression, orderType, nullsOrder)

fun someWindowDefinition(
    windowReferenceExpression: TypeExpression<StringType>? = null,
    windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
    windowOrderClause: List<OrderingTerm>? = null,
    windowFrameClause: WindowFrameClause? = null,
) = WindowDefinition(
    windowReferenceExpression = windowReferenceExpression,
    windowPartitionClause = windowPartitionClause,
    windowOrderClause = windowOrderClause,
    windowFrameClause = windowFrameClause,
)

fun someWindowFrameClause(
    windowFrameType: WindowFrameType = RANGE,
    windowFrameExtent: WindowFrameExtent = someWindowFrameExtent(),
    windowFrameExclusion: WindowFrameExclusion? = null,
) = WindowFrameClause(windowFrameType, windowFrameExtent, windowFrameExclusion)

fun someWindowFrameExtent() = UnboundedPreceding()

fun someWindowFrameExclusion() = WindowFrameExclusion.EXCLUDE_GROUP
