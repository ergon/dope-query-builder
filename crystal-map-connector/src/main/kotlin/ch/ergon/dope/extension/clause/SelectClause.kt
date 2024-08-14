package ch.ergon.dope.extension.clause

import ch.ergon.dope.resolvable.clause.ISelectFromClause
import ch.ergon.dope.resolvable.clause.ISelectGroupByClause
import ch.ergon.dope.resolvable.clause.ISelectJoinClause
import ch.ergon.dope.resolvable.clause.ISelectLimitClause
import ch.ergon.dope.resolvable.clause.ISelectOrderByClause
import ch.ergon.dope.resolvable.clause.ISelectUnnestClause
import ch.ergon.dope.resolvable.clause.ISelectUseKeysClause
import ch.ergon.dope.resolvable.clause.ISelectWhereClause
import ch.ergon.dope.resolvable.clause.model.OrderByType
import ch.ergon.dope.resolvable.fromable.Bucket
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

fun ISelectUseKeysClause.where(whereExpression: CMJsonField<Boolean>) = where(whereExpression.toDopeType())

fun ISelectFromClause.useKeys(useKeys: CMField<String>) = useKeys(useKeys.toDopeType())
fun ISelectFromClause.useKeys(useKeys: CMList<String>) = useKeys(useKeys.toDopeType())

fun ISelectJoinClause.join(bucket: Bucket, onKeys: CMJsonField<out Any>) = join(bucket, onKeys.toDopeType())
fun ISelectJoinClause.join(bucket: Bucket, onKey: CMJsonField<out Any>, forBucket: Bucket) = join(bucket, onKey.toDopeType(), forBucket)

fun ISelectJoinClause.innerJoin(bucket: Bucket, onKeys: CMJsonField<out Any>) = innerJoin(bucket, onKeys.toDopeType())
fun ISelectJoinClause.innerJoin(bucket: Bucket, onKey: CMJsonField<out Any>, forBucket: Bucket) =
    innerJoin(bucket, onKey.toDopeType(), forBucket)

fun ISelectJoinClause.leftJoin(bucket: Bucket, onKeys: CMJsonField<out Any>) = leftJoin(bucket, onKeys.toDopeType())
fun ISelectJoinClause.leftJoin(bucket: Bucket, onKey: CMJsonField<out Any>, forBucket: Bucket) =
    leftJoin(bucket, onKey.toDopeType(), forBucket)

@JvmName("unnestString")
fun ISelectUnnestClause.unnest(arrayField: CMJsonList<String>) = unnest(arrayField.toDopeType())

@JvmName("unnestNumber")
fun ISelectUnnestClause.unnest(arrayField: CMJsonList<Number>) = unnest(arrayField.toDopeType())

@JvmName("unnestBoolean")
fun ISelectUnnestClause.unnest(arrayField: CMJsonList<Boolean>) = unnest(arrayField.toDopeType())
