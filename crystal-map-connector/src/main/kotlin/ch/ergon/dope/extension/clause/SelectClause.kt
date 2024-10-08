package ch.ergon.dope.extension.clause

import ch.ergon.dope.resolvable.clause.ISelectFromClause
import ch.ergon.dope.resolvable.clause.ISelectGroupByClause
import ch.ergon.dope.resolvable.clause.ISelectJoinClause
import ch.ergon.dope.resolvable.clause.ISelectLimitClause
import ch.ergon.dope.resolvable.clause.ISelectOrderByClause
import ch.ergon.dope.resolvable.clause.ISelectUnnestClause
import ch.ergon.dope.resolvable.clause.ISelectWhereClause
import ch.ergon.dope.resolvable.clause.model.OrderByType
import ch.ergon.dope.resolvable.clause.model.joinHint.HashOrNestedLoopHint
import ch.ergon.dope.resolvable.clause.model.joinHint.KeysOrIndexHint
import ch.ergon.dope.resolvable.fromable.Bucket
import ch.ergon.dope.resolvable.fromable.Joinable
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList
import com.schwarz.crystalapi.schema.CMType

fun ISelectLimitClause.offset(numberField: CMJsonField<Number>) = offset(numberField.toDopeType())

fun ISelectOrderByClause.limit(numberField: CMJsonField<Number>) = limit(numberField.toDopeType())

fun ISelectGroupByClause.orderBy(stringField: CMJsonField<String>) = orderBy(stringField.toDopeType())

fun ISelectGroupByClause.orderBy(stringField: CMJsonField<String>, orderByType: OrderByType) =
    orderBy(stringField.toDopeType(), orderByType)

fun ISelectWhereClause.groupBy(field: CMType, vararg fields: CMType) =
    groupBy(field.toDopeType(), *fields.map { it.toDopeType() }.toTypedArray())

fun ISelectFromClause.where(whereExpression: CMJsonField<Boolean>) = where(whereExpression.toDopeType())

fun ISelectJoinClause.join(
    joinable: Joinable,
    onKeys: CMJsonField<out Any>,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
) = join(joinable, onKeys.toDopeType(), hashOrNestedLoopHint, keysOrIndexHint)
fun ISelectJoinClause.join(
    joinable: Joinable,
    onKey: CMJsonField<out Any>,
    forBucket: Bucket,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
) = join(joinable, onKey.toDopeType(), forBucket, hashOrNestedLoopHint, keysOrIndexHint)

fun ISelectJoinClause.innerJoin(
    joinable: Joinable,
    onKeys: CMJsonField<out Any>,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
) = innerJoin(joinable, onKeys.toDopeType(), hashOrNestedLoopHint, keysOrIndexHint)
fun ISelectJoinClause.innerJoin(
    joinable: Joinable,
    onKey: CMJsonField<out Any>,
    forBucket: Bucket,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
) = innerJoin(joinable, onKey.toDopeType(), forBucket, hashOrNestedLoopHint, keysOrIndexHint)

fun ISelectJoinClause.leftJoin(
    joinable: Joinable,
    onKeys: CMJsonField<out Any>,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
) = leftJoin(joinable, onKeys.toDopeType(), hashOrNestedLoopHint, keysOrIndexHint)
fun ISelectJoinClause.leftJoin(
    joinable: Joinable,
    onKey: CMJsonField<out Any>,
    forBucket: Bucket,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
) = leftJoin(joinable, onKey.toDopeType(), forBucket, hashOrNestedLoopHint, keysOrIndexHint)

@JvmName("unnestString")
fun ISelectUnnestClause.unnest(arrayField: CMJsonList<String>) = unnest(arrayField.toDopeType())

@JvmName("unnestNumber")
fun ISelectUnnestClause.unnest(arrayField: CMJsonList<Number>) = unnest(arrayField.toDopeType())

@JvmName("unnestBoolean")
fun ISelectUnnestClause.unnest(arrayField: CMJsonList<Boolean>) = unnest(arrayField.toDopeType())
