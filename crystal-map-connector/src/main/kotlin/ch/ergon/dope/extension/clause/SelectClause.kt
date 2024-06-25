package ch.ergon.dope.extension.clause

import ch.ergon.dope.resolvable.clause.ISelectFromClause
import ch.ergon.dope.resolvable.clause.ISelectGroupByClause
import ch.ergon.dope.resolvable.clause.ISelectJoinClause
import ch.ergon.dope.resolvable.clause.ISelectLimitClause
import ch.ergon.dope.resolvable.clause.ISelectOrderByClause
import ch.ergon.dope.resolvable.clause.ISelectUnnestClause
import ch.ergon.dope.resolvable.clause.ISelectWhereClause
import ch.ergon.dope.resolvable.clause.model.GroupByClause
import ch.ergon.dope.resolvable.clause.model.OrderByType
import ch.ergon.dope.resolvable.clause.model.SelectLimitClause
import ch.ergon.dope.resolvable.clause.model.SelectOffsetClause
import ch.ergon.dope.resolvable.clause.model.SelectOrderByClause
import ch.ergon.dope.resolvable.fromable.Bucket
import ch.ergon.dope.toDopeArrayField
import ch.ergon.dope.toDopeField
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList
import com.schwarz.crystalapi.schema.CMType

fun ISelectLimitClause.offset(numberField: CMField<Number>): SelectOffsetClause = offset(numberField.toDopeField())

fun ISelectOrderByClause.limit(numberField: CMField<Number>): SelectLimitClause = limit(numberField.toDopeField())

fun ISelectGroupByClause.orderBy(stringField: CMField<String>): SelectOrderByClause = orderBy(stringField.toDopeField())

fun ISelectGroupByClause.orderBy(stringField: CMField<String>, orderByType: OrderByType): SelectOrderByClause =
    orderBy(stringField.toDopeField(), orderByType)

fun ISelectWhereClause.groupBy(field: CMType, vararg fields: CMType): GroupByClause =
    groupBy(field.toDopeField(), *fields.map { it.toDopeField() }.toTypedArray())

fun ISelectFromClause.where(whereExpression: CMField<Boolean>) = where(whereExpression.toDopeField())

fun ISelectJoinClause.join(bucket: Bucket, onKeys: CMField<out Any>) = join(bucket, onKeys.toDopeField())
fun ISelectJoinClause.join(bucket: Bucket, onKey: CMField<out Any>, forBucket: Bucket) = join(bucket, onKey.toDopeField(), forBucket)

fun ISelectJoinClause.innerJoin(bucket: Bucket, onKeys: CMField<out Any>) = innerJoin(bucket, onKeys.toDopeField())
fun ISelectJoinClause.innerJoin(bucket: Bucket, onKey: CMField<out Any>, forBucket: Bucket) = innerJoin(bucket, onKey.toDopeField(), forBucket)

fun ISelectJoinClause.leftJoin(bucket: Bucket, onKeys: CMField<out Any>) = leftJoin(bucket, onKeys.toDopeField())
fun ISelectJoinClause.leftJoin(bucket: Bucket, onKey: CMField<out Any>, forBucket: Bucket) = leftJoin(bucket, onKey.toDopeField(), forBucket)

@JvmName("unnestString")
fun ISelectUnnestClause.unnest(arrayField: CMList<String>) = unnest(arrayField.toDopeArrayField())

@JvmName("unnestNumber")
fun ISelectUnnestClause.unnest(arrayField: CMList<Number>) = unnest(arrayField.toDopeArrayField())

@JvmName("unnestBoolean")
fun ISelectUnnestClause.unnest(arrayField: CMList<Boolean>) = unnest(arrayField.toDopeArrayField())
