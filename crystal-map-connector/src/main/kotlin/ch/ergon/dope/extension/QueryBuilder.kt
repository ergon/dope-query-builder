package ch.ergon.dope.extension

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.asField
import ch.ergon.dope.resolvable.clause.model.SelectClause
import ch.ergon.dope.resolvable.clause.model.SelectDistinctClause
import ch.ergon.dope.resolvable.clause.model.SelectRawClause
import com.schwarz.crystalapi.schema.CMType

fun QueryBuilder.select(expression: CMType, vararg expressions: CMType): SelectClause =
    select(expression.asField(), *expressions.map { it.asField() }.toTypedArray())

fun QueryBuilder.selectDistinct(expression: CMType, vararg expressions: CMType): SelectDistinctClause =
    selectDistinct(expression.asField(), *expressions.map { it.asField() }.toTypedArray())

fun QueryBuilder.selectRaw(expression: CMType): SelectRawClause = selectRaw(expression.asField())
