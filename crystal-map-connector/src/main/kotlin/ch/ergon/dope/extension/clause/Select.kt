package ch.ergon.dope.extension.clause

import ch.ergon.dope.asField
import ch.ergon.dope.resolvable.clause.IGroupByClause
import ch.ergon.dope.resolvable.clause.ILimitClause
import ch.ergon.dope.resolvable.clause.IOrderByClause
import ch.ergon.dope.resolvable.clause.LimitClause
import ch.ergon.dope.resolvable.clause.OffsetClause
import ch.ergon.dope.resolvable.clause.OrderByClause
import ch.ergon.dope.resolvable.clause.OrderByType
import com.schwarz.crystalapi.schema.CMField

fun ILimitClause.offset(numberField: CMField<Number>): OffsetClause = offset(numberField.asField())

fun IOrderByClause.limit(numberField: CMField<Number>): LimitClause = limit(numberField.asField())

fun IGroupByClause.orderBy(stringField: CMField<String>, orderByType: OrderByType): OrderByClause = orderBy(stringField.asField(), orderByType)

fun IGroupByClause.orderBy(stringField: CMField<String>): OrderByClause = orderBy(stringField.asField())
