package ch.ergon.dope.extension.clause

import ch.ergon.dope.resolvable.clause.ISelectFromClause
import ch.ergon.dope.resolvable.clause.ISelectGroupByClause
import ch.ergon.dope.resolvable.clause.ISelectJoinClause
import ch.ergon.dope.resolvable.clause.ISelectLimitClause
import ch.ergon.dope.resolvable.clause.ISelectOrderByClause
import ch.ergon.dope.resolvable.clause.ISelectUnnestClause
import ch.ergon.dope.resolvable.clause.ISelectWhereClause
import ch.ergon.dope.resolvable.clause.model.OrderByType
import ch.ergon.dope.resolvable.fromable.Bucket
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList
import com.schwarz.crystalapi.schema.CMType

fun ISelectLimitClause.offset(numberField: CMField<Number>) = offset(numberField.toDopeType())

fun ISelectOrderByClause.limit(numberField: CMField<Number>) = limit(numberField.toDopeType())

fun ISelectGroupByClause.orderBy(stringField: CMField<String>) = orderBy(stringField.toDopeType())

fun ISelectGroupByClause.orderBy(stringField: CMField<String>, orderByType: OrderByType) =
    orderBy(stringField.toDopeType(), orderByType)

fun ISelectWhereClause.groupBy(field: CMType, vararg fields: CMType) =
    groupBy(field.toDopeType(), *fields.map { it.toDopeType() }.toTypedArray())

fun ISelectFromClause.where(whereExpression: CMField<Boolean>) = where(whereExpression.toDopeType())

fun ISelectJoinClause.join(bucket: Bucket, onKeys: CMField<out Any>) = join(bucket, onKeys.toDopeType())
fun ISelectJoinClause.join(bucket: Bucket, onKey: CMField<out Any>, forBucket: Bucket) = join(bucket, onKey.toDopeType(), forBucket)

fun ISelectJoinClause.innerJoin(bucket: Bucket, onKeys: CMField<out Any>) = innerJoin(bucket, onKeys.toDopeType())
fun ISelectJoinClause.innerJoin(bucket: Bucket, onKey: CMField<out Any>, forBucket: Bucket) = innerJoin(bucket, onKey.toDopeType(), forBucket)

fun ISelectJoinClause.leftJoin(bucket: Bucket, onKeys: CMField<out Any>) = leftJoin(bucket, onKeys.toDopeType())
fun ISelectJoinClause.leftJoin(bucket: Bucket, onKey: CMField<out Any>, forBucket: Bucket) = leftJoin(bucket, onKey.toDopeType(), forBucket)

@JvmName("unnestString")
fun ISelectUnnestClause.unnest(arrayField: CMList<String>) = unnest(arrayField.toDopeType())

@JvmName("unnestNumber")
fun ISelectUnnestClause.unnest(arrayField: CMList<Number>) = unnest(arrayField.toDopeType())

@JvmName("unnestBoolean")
fun ISelectUnnestClause.unnest(arrayField: CMList<Boolean>) = unnest(arrayField.toDopeType())
