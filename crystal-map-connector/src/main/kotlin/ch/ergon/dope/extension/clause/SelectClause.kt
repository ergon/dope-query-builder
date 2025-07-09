package ch.ergon.dope.extension.clause

import ch.ergon.dope.resolvable.Joinable
import ch.ergon.dope.resolvable.Nestable
import ch.ergon.dope.resolvable.bucket.Bucket
import ch.ergon.dope.resolvable.clause.ISelectFromClause
import ch.ergon.dope.resolvable.clause.ISelectGroupByClause
import ch.ergon.dope.resolvable.clause.ISelectLimitClause
import ch.ergon.dope.resolvable.clause.ISelectOrderByClause
import ch.ergon.dope.resolvable.clause.ISelectWhereClause
import ch.ergon.dope.resolvable.clause.joinHint.HashOrNestedLoopHint
import ch.ergon.dope.resolvable.clause.joinHint.KeysOrIndexHint
import ch.ergon.dope.resolvable.clause.model.OrderType
import ch.ergon.dope.resolvable.clause.model.SelectOrderByClause
import ch.ergon.dope.resolvable.expression.type.assignTo
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.ValidType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList
import com.schwarz.crystalapi.schema.CMType

fun <T : ValidType> ISelectLimitClause<T>.offset(numberField: CMJsonField<Number>) = offset(numberField.toDopeType())

fun <T : ValidType> ISelectOrderByClause<T>.limit(numberField: CMJsonField<Number>) = limit(numberField.toDopeType())

@JvmName("orderByNumber")
fun <T : ValidType> ISelectGroupByClause<T>.orderBy(
    numberField: CMJsonField<Number>,
    orderByType: OrderType? = null,
) = orderBy(numberField.toDopeType(), orderByType)

@JvmName("orderByString")
fun <T : ValidType> ISelectGroupByClause<T>.orderBy(
    stringField: CMJsonField<String>,
    orderByType: OrderType? = null,
) = orderBy(stringField.toDopeType(), orderByType)

@JvmName("orderByBoolean")
fun <T : ValidType> ISelectGroupByClause<T>.orderBy(
    booleanField: CMJsonField<Boolean>,
    orderByType: OrderType? = null,
) = orderBy(booleanField.toDopeType(), orderByType)

@JvmName("orderByListNumber")
fun <T : ValidType> ISelectGroupByClause<T>.orderBy(
    numberField: CMJsonList<Number>,
    orderByType: OrderType? = null,
) = orderBy(numberField.toDopeType(), orderByType)

@JvmName("orderByListString")
fun <T : ValidType> ISelectGroupByClause<T>.orderBy(
    stringField: CMJsonList<String>,
    orderByType: OrderType? = null,
) = orderBy(stringField.toDopeType(), orderByType)

@JvmName("orderByListBoolean")
fun <T : ValidType> ISelectGroupByClause<T>.orderBy(
    booleanField: CMJsonList<Boolean>,
    orderByType: OrderType? = null,
) = orderBy(booleanField.toDopeType(), orderByType)

@JvmName("thenOrderByNumber")
fun <T : ValidType> SelectOrderByClause<T>.thenOrderBy(
    numberField: CMJsonField<Number>,
    orderByType: OrderType? = null,
) = thenOrderBy(numberField.toDopeType(), orderByType)

@JvmName("thenOrderByString")
fun <T : ValidType> SelectOrderByClause<T>.thenOrderBy(
    stringField: CMJsonField<String>,
    orderByType: OrderType? = null,
) = thenOrderBy(stringField.toDopeType(), orderByType)

@JvmName("thenOrderByBoolean")
fun <T : ValidType> SelectOrderByClause<T>.thenOrderBy(
    booleanField: CMJsonField<Boolean>,
    orderByType: OrderType? = null,
) = thenOrderBy(booleanField.toDopeType(), orderByType)

@JvmName("thenOrderByListNumber")
fun <T : ValidType> SelectOrderByClause<T>.thenOrderBy(
    numberField: CMJsonList<Number>,
    orderByType: OrderType? = null,
) = thenOrderBy(numberField.toDopeType(), orderByType)

@JvmName("thenOrderByListString")
fun <T : ValidType> SelectOrderByClause<T>.thenOrderBy(
    stringField: CMJsonList<String>,
    orderByType: OrderType? = null,
) = thenOrderBy(stringField.toDopeType(), orderByType)

@JvmName("thenOrderByListBoolean")
fun <T : ValidType> SelectOrderByClause<T>.thenOrderBy(
    booleanField: CMJsonList<Boolean>,
    orderByType: OrderType? = null,
) = thenOrderBy(booleanField.toDopeType(), orderByType)

fun <T : ValidType> ISelectWhereClause<T>.groupBy(field: CMType, vararg fields: CMType) =
    groupBy(field.toDopeType(), *fields.map { it.toDopeType() }.toTypedArray())

fun <T : ValidType> ISelectFromClause<T>.where(whereExpression: CMJsonField<Boolean>) = where(whereExpression.toDopeType())

fun <T : ValidType> ISelectFromClause<T>.join(
    joinable: Joinable,
    keys: CMJsonList<String>,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
) = join(joinable, keys.toDopeType(), hashOrNestedLoopHint, keysOrIndexHint)

fun <T : ValidType> ISelectFromClause<T>.join(
    joinable: Joinable,
    key: CMJsonField<String>,
    bucket: Bucket? = null,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
) = join(joinable, key.toDopeType(), bucket, hashOrNestedLoopHint, keysOrIndexHint)

fun <T : ValidType> ISelectFromClause<T>.innerJoin(
    joinable: Joinable,
    keys: CMJsonList<String>,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
) = innerJoin(joinable, keys.toDopeType(), hashOrNestedLoopHint, keysOrIndexHint)

fun <T : ValidType> ISelectFromClause<T>.innerJoin(
    joinable: Joinable,
    key: CMJsonField<String>,
    bucket: Bucket? = null,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
) = innerJoin(joinable, key.toDopeType(), bucket, hashOrNestedLoopHint, keysOrIndexHint)

fun <T : ValidType> ISelectFromClause<T>.leftJoin(
    joinable: Joinable,
    keys: CMJsonList<String>,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
) = leftJoin(joinable, keys.toDopeType(), hashOrNestedLoopHint, keysOrIndexHint)

fun <T : ValidType> ISelectFromClause<T>.leftJoin(
    joinable: Joinable,
    key: CMJsonField<String>,
    bucket: Bucket? = null,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
) = leftJoin(joinable, key.toDopeType(), bucket, hashOrNestedLoopHint, keysOrIndexHint)

fun <T : ValidType> ISelectFromClause<T>.rightJoin(
    joinable: Joinable,
    key: CMJsonField<Boolean>,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
) = rightJoin(joinable, key.toDopeType(), hashOrNestedLoopHint, keysOrIndexHint)

@JvmName("unnestString")
fun <T : ValidType> ISelectFromClause<T>.unnest(arrayField: CMJsonList<String>) = unnest(arrayField.toDopeType())

@JvmName("unnestNumber")
fun <T : ValidType> ISelectFromClause<T>.unnest(arrayField: CMJsonList<Number>) = unnest(arrayField.toDopeType())

@JvmName("unnestBoolean")
fun <T : ValidType> ISelectFromClause<T>.unnest(arrayField: CMJsonList<Boolean>) = unnest(arrayField.toDopeType())

fun <T : ValidType> ISelectFromClause<T>.nest(
    nestable: Nestable,
    keys: CMJsonList<String>,
) = nest(nestable, keys.toDopeType())

fun <T : ValidType> ISelectFromClause<T>.nest(
    nestable: Nestable,
    key: CMJsonField<String>,
    bucket: Bucket? = null,
) = nest(nestable, key.toDopeType(), bucket)

fun <T : ValidType> ISelectFromClause<T>.innerNest(
    nestable: Nestable,
    keys: CMJsonList<String>,
) = innerNest(nestable, keys.toDopeType())

fun <T : ValidType> ISelectFromClause<T>.innerNest(
    nestable: Nestable,
    key: CMJsonField<String>,
    bucket: Bucket? = null,
) = innerNest(nestable, key.toDopeType(), bucket)

fun <T : ValidType> ISelectFromClause<T>.leftNest(
    nestable: Nestable,
    keys: CMJsonList<String>,
) = leftNest(nestable, keys.toDopeType())

fun <T : ValidType> ISelectFromClause<T>.leftNest(
    nestable: Nestable,
    key: CMJsonField<String>,
    bucket: Bucket? = null,
) = leftNest(nestable, key.toDopeType(), bucket)

@JvmName("assignToCMNumberField")
fun String.assignTo(value: CMJsonField<Number>) = assignTo(value.toDopeType())

@JvmName("assignToCMStringField")
fun String.assignTo(value: CMJsonField<String>) = assignTo(value.toDopeType())

@JvmName("assignToCMBooleanField")
fun String.assignTo(value: CMJsonField<Boolean>) = assignTo(value.toDopeType())

@JvmName("assignToCMNumberList")
fun String.assignTo(value: CMJsonList<Number>) = assignTo(value.toDopeType())

@JvmName("assignToCMStringList")
fun String.assignTo(value: CMJsonList<String>) = assignTo(value.toDopeType())

@JvmName("assignToCMBooleanList")
fun String.assignTo(value: CMJsonList<Boolean>) = assignTo(value.toDopeType())
