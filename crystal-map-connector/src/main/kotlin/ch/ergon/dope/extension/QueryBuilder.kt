package ch.ergon.dope.extension

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.resolvable.clause.model.SelectClause
import ch.ergon.dope.resolvable.clause.model.SelectDistinctClause
import ch.ergon.dope.resolvable.clause.model.SelectRawClause
import ch.ergon.dope.toDopeField
import com.schwarz.crystalapi.schema.CMType

fun QueryBuilder.select(expression: CMType, vararg expressions: CMType): SelectClause =
    select(expression.toDopeField(), *expressions.map { it.toDopeField() }.toTypedArray())

fun QueryBuilder.selectDistinct(expression: CMType, vararg expressions: CMType): SelectDistinctClause =
    selectDistinct(expression.toDopeField(), *expressions.map { it.toDopeField() }.toTypedArray())

fun QueryBuilder.selectRaw(expression: CMType): SelectRawClause = selectRaw(expression.toDopeField())
