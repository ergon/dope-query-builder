package ch.ergon.dope.extension

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.resolvable.clause.model.SelectClause
import ch.ergon.dope.resolvable.clause.model.SelectDistinctClause
import ch.ergon.dope.resolvable.clause.model.SelectRawClause
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMType

fun QueryBuilder.select(expression: CMType, vararg expressions: CMType): SelectClause =
    select(expression.toDopeType(), *expressions.map { it.toDopeType() }.toTypedArray())

fun QueryBuilder.selectDistinct(expression: CMType, vararg expressions: CMType): SelectDistinctClause =
    selectDistinct(expression.toDopeType(), *expressions.map { it.toDopeType() }.toTypedArray())

fun QueryBuilder.selectRaw(expression: CMType): SelectRawClause = selectRaw(expression.toDopeType())
