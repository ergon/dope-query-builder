package ch.ergon.dope.extension.clause

import ch.ergon.dope.resolvable.clause.IUpdateClause
import ch.ergon.dope.resolvable.clause.IUpdateLimitClause
import ch.ergon.dope.resolvable.clause.IUpdateSetClause
import ch.ergon.dope.resolvable.clause.IUpdateUnsetClause
import ch.ergon.dope.resolvable.clause.IUpdateUseKeysClause
import ch.ergon.dope.resolvable.clause.IUpdateWhereClause
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.ValidType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList
import com.schwarz.crystalapi.schema.CMType

fun IUpdateLimitClause.returning(field: CMType, vararg fields: CMType) =
    returning(field.toDopeType(), *fields.map { it.toDopeType() }.toTypedArray())

fun IUpdateWhereClause.limit(numberField: CMField<Number>) = limit(numberField.toDopeType())

fun IUpdateUnsetClause.where(whereExpression: CMField<Boolean>) = where(whereExpression.toDopeType())

fun IUpdateSetClause.unset(field: CMType, vararg fields: CMType) =
    unset(field.toDopeType(), *fields.map { it.toDopeType() }.toTypedArray())

@JvmName("setCMTypeToCMType")
fun IUpdateUseKeysClause.set(
    fieldValuePair: Pair<CMType, CMType>,
    vararg fieldValuePairs: Pair<CMType, CMType>,
) = set(
    fieldValuePair.first.toDopeType() to fieldValuePair.second.toDopeType(),
    *fieldValuePairs.map { it.first.toDopeType() to it.second.toDopeType() }.toTypedArray(),
)

@JvmName("setCMTypeToTypeExpression")
fun IUpdateUseKeysClause.set(
    fieldValuePair: Pair<CMType, TypeExpression<out ValidType>>,
    vararg fieldValuePairs: Pair<CMType, TypeExpression<out ValidType>>,
) = set(
    fieldValuePair.first.toDopeType() to fieldValuePair.second,
    *fieldValuePairs.map { it.first.toDopeType() to it.second }.toTypedArray(),
)

fun IUpdateClause.useKeys(useKeys: CMField<String>) = useKeys(useKeys.toDopeType())
fun IUpdateClause.useKeys(useKeys: CMList<String>) = useKeys(useKeys.toDopeType())
