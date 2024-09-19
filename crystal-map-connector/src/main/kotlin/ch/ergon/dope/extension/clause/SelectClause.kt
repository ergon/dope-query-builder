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
import ch.ergon.dope.resolvable.fromable.Joinable
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.ValidType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList
import com.schwarz.crystalapi.schema.CMType

fun <R : ValidType> ISelectLimitClause<R>.offset(numberField: CMJsonField<Number>) = offset(numberField.toDopeType())

fun <R : ValidType> ISelectOrderByClause<R>.limit(numberField: CMJsonField<Number>) = limit(numberField.toDopeType())

fun <R : ValidType> ISelectGroupByClause<R>.orderBy(stringField: CMJsonField<String>) = orderBy(stringField.toDopeType())

fun <R : ValidType> ISelectGroupByClause<R>.orderBy(stringField: CMJsonField<String>, orderByType: OrderByType) =
    orderBy(stringField.toDopeType(), orderByType)

fun <R : ValidType> ISelectWhereClause<R>.groupBy(field: CMType, vararg fields: CMType) =
    groupBy(field.toDopeType(), *fields.map { it.toDopeType() }.toTypedArray())

fun <R : ValidType> ISelectFromClause<R>.where(whereExpression: CMJsonField<Boolean>) = where(whereExpression.toDopeType())

fun <R : ValidType> ISelectJoinClause<R>.join(joinable: Joinable, onKeys: CMJsonField<out Any>) = join(joinable, onKeys.toDopeType())
fun <R : ValidType> ISelectJoinClause<R>.join(joinable: Joinable, onKey: CMJsonField<out Any>, forBucket: Bucket) =
    join(joinable, onKey.toDopeType(), forBucket)

fun <R : ValidType> ISelectJoinClause<R>.innerJoin(joinable: Joinable, onKeys: CMJsonField<out Any>) = innerJoin(joinable, onKeys.toDopeType())
fun <R : ValidType> ISelectJoinClause<R>.innerJoin(joinable: Joinable, onKey: CMJsonField<out Any>, forBucket: Bucket) =
    innerJoin(joinable, onKey.toDopeType(), forBucket)

fun <R : ValidType> ISelectJoinClause<R>.leftJoin(joinable: Joinable, onKeys: CMJsonField<out Any>) = leftJoin(joinable, onKeys.toDopeType())
fun <R : ValidType> ISelectJoinClause<R>.leftJoin(joinable: Joinable, onKey: CMJsonField<out Any>, forBucket: Bucket) =
    leftJoin(joinable, onKey.toDopeType(), forBucket)

@JvmName("unnestString")
fun <R : ValidType> ISelectUnnestClause<R>.unnest(arrayField: CMJsonList<String>) = unnest(arrayField.toDopeType())

@JvmName("unnestNumber")
fun <R : ValidType> ISelectUnnestClause<R>.unnest(arrayField: CMJsonList<Number>) = unnest(arrayField.toDopeType())

@JvmName("unnestBoolean")
fun <R : ValidType> ISelectUnnestClause<R>.unnest(arrayField: CMJsonList<Boolean>) = unnest(arrayField.toDopeType())
