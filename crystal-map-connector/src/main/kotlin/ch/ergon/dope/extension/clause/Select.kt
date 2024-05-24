package ch.ergon.dope.extension.clause

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.asArrayField
import ch.ergon.dope.asField
import ch.ergon.dope.resolvable.clause.ISelectFromClause
import ch.ergon.dope.resolvable.clause.ISelectGroupByClause
import ch.ergon.dope.resolvable.clause.ISelectJoinClause
import ch.ergon.dope.resolvable.clause.ISelectLimitClause
import ch.ergon.dope.resolvable.clause.ISelectOrderByClause
import ch.ergon.dope.resolvable.clause.ISelectUnnestClause
import ch.ergon.dope.resolvable.clause.ISelectWhereClause
import ch.ergon.dope.resolvable.clause.model.GroupByClause
import ch.ergon.dope.resolvable.clause.model.OrderByType
import ch.ergon.dope.resolvable.clause.model.SelectClause
import ch.ergon.dope.resolvable.clause.model.SelectDistinctClause
import ch.ergon.dope.resolvable.clause.model.SelectLimitClause
import ch.ergon.dope.resolvable.clause.model.SelectOffsetClause
import ch.ergon.dope.resolvable.clause.model.SelectOrderByClause
import ch.ergon.dope.resolvable.clause.model.SelectRawClause
import ch.ergon.dope.resolvable.fromable.Bucket
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList
import com.schwarz.crystalapi.schema.CMType

fun ISelectLimitClause.offset(numberField: CMField<Number>): SelectOffsetClause = offset(numberField.asField())

fun ISelectOrderByClause.limit(numberField: CMField<Number>): SelectLimitClause = limit(numberField.asField())

fun ISelectGroupByClause.orderBy(stringField: CMField<String>): SelectOrderByClause = orderBy(stringField.asField())

fun ISelectGroupByClause.orderBy(stringField: CMField<String>, orderByType: OrderByType): SelectOrderByClause =
    orderBy(stringField.asField(), orderByType)

fun ISelectWhereClause.groupBy(field: CMType, vararg fields: CMType): GroupByClause =
    groupBy(field.asField(), *fields.map { it.asField() }.toTypedArray())

fun ISelectFromClause.where(whereExpression: CMField<Boolean>) = where(whereExpression.asField())

fun ISelectJoinClause.join(bucket: Bucket, onKeys: CMField<out Any>) = join(bucket, onKeys.asField())

fun ISelectJoinClause.innerJoin(bucket: Bucket, onKeys: CMField<out Any>) = innerJoin(bucket, onKeys.asField())

fun ISelectJoinClause.leftJoin(bucket: Bucket, onKeys: CMField<out Any>) = leftJoin(bucket, onKeys.asField())

fun ISelectJoinClause.rightJoin(bucket: Bucket, onKeys: CMField<out Any>) = rightJoin(bucket, onKeys.asField())

@JvmName("unnestString")
fun ISelectUnnestClause.unnest(arrayField: CMList<String>) = unnest(arrayField.asArrayField())

@JvmName("unnestNumber")
fun ISelectUnnestClause.unnest(arrayField: CMList<Number>) = unnest(arrayField.asArrayField())

@JvmName("unnestBoolean")
fun ISelectUnnestClause.unnest(arrayField: CMList<Boolean>) = unnest(arrayField.asArrayField())

fun QueryBuilder.select(expression: CMType, vararg expressions: CMType): SelectClause =
    select(expression.asField(), *expressions.map { it.asField() }.toTypedArray())

fun QueryBuilder.selectDistinct(expression: CMType, vararg expressions: CMType): SelectDistinctClause =
    selectDistinct(expression.asField(), *expressions.map { it.asField() }.toTypedArray())

fun QueryBuilder.selectRaw(expression: CMType): SelectRawClause = selectRaw(expression.asField())
