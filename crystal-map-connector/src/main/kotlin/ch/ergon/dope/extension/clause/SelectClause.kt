package ch.ergon.dope.extension.clause

import ch.ergon.dope.resolvable.clause.ISelectFromClause
import ch.ergon.dope.resolvable.clause.ISelectGroupByClause
import ch.ergon.dope.resolvable.clause.ISelectJoinClause
import ch.ergon.dope.resolvable.clause.ISelectLimitClause
import ch.ergon.dope.resolvable.clause.ISelectOrderByClause
import ch.ergon.dope.resolvable.clause.ISelectUnnestClause
import ch.ergon.dope.resolvable.clause.ISelectWhereClause
import ch.ergon.dope.resolvable.clause.model.OrderByType
import ch.ergon.dope.resolvable.clause.model.assignTo
import ch.ergon.dope.resolvable.clause.model.joinHint.HashOrNestedLoopHint
import ch.ergon.dope.resolvable.clause.model.joinHint.KeysOrIndexHint
import ch.ergon.dope.resolvable.fromable.Bucket
import ch.ergon.dope.resolvable.fromable.Joinable
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.ValidType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList
import com.schwarz.crystalapi.schema.CMType

fun <T : ValidType> ISelectLimitClause<T>.offset(numberField: CMJsonField<Number>) = offset(numberField.toDopeType())

fun <T : ValidType> ISelectOrderByClause<T>.limit(numberField: CMJsonField<Number>) = limit(numberField.toDopeType())

fun <T : ValidType> ISelectGroupByClause<T>.orderBy(stringField: CMJsonField<String>) = orderBy(stringField.toDopeType())

fun <T : ValidType> ISelectGroupByClause<T>.orderBy(stringField: CMJsonField<String>, orderByType: OrderByType) =
    orderBy(stringField.toDopeType(), orderByType)

fun <T : ValidType> ISelectWhereClause<T>.groupBy(field: CMType, vararg fields: CMType) =
    groupBy(field.toDopeType(), *fields.map { it.toDopeType() }.toTypedArray())

fun <T : ValidType> ISelectFromClause<T>.where(whereExpression: CMJsonField<Boolean>) = where(whereExpression.toDopeType())

fun <T : ValidType> ISelectJoinClause<T>.join(
    joinable: Joinable,
    onKeys: CMJsonField<out Any>,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
) = join(joinable, onKeys.toDopeType(), hashOrNestedLoopHint, keysOrIndexHint)
fun <T : ValidType> ISelectJoinClause<T>.join(
    joinable: Joinable,
    onKey: CMJsonField<out Any>,
    forBucket: Bucket,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
) = join(joinable, onKey.toDopeType(), forBucket, hashOrNestedLoopHint, keysOrIndexHint)

fun <T : ValidType> ISelectJoinClause<T>.innerJoin(
    joinable: Joinable,
    onKeys: CMJsonField<out Any>,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
) = innerJoin(joinable, onKeys.toDopeType(), hashOrNestedLoopHint, keysOrIndexHint)
fun <T : ValidType> ISelectJoinClause<T>.innerJoin(
    joinable: Joinable,
    onKey: CMJsonField<out Any>,
    forBucket: Bucket,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
) = innerJoin(joinable, onKey.toDopeType(), forBucket, hashOrNestedLoopHint, keysOrIndexHint)

fun <T : ValidType> ISelectJoinClause<T>.leftJoin(
    joinable: Joinable,
    onKeys: CMJsonField<out Any>,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
) = leftJoin(joinable, onKeys.toDopeType(), hashOrNestedLoopHint, keysOrIndexHint)
fun <T : ValidType> ISelectJoinClause<T>.leftJoin(
    joinable: Joinable,
    onKey: CMJsonField<out Any>,
    forBucket: Bucket,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
) = leftJoin(joinable, onKey.toDopeType(), forBucket, hashOrNestedLoopHint, keysOrIndexHint)

@JvmName("unnestString")
fun <T : ValidType> ISelectUnnestClause<T>.unnest(arrayField: CMJsonList<String>) = unnest(arrayField.toDopeType())

@JvmName("unnestNumber")
fun <T : ValidType> ISelectUnnestClause<T>.unnest(arrayField: CMJsonList<Number>) = unnest(arrayField.toDopeType())

@JvmName("unnestBoolean")
fun <T : ValidType> ISelectUnnestClause<T>.unnest(arrayField: CMJsonList<Boolean>) = unnest(arrayField.toDopeType())

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
