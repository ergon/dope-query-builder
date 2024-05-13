package ch.ergon.dope.extension.clause

import ch.ergon.dope.asField
import ch.ergon.dope.resolvable.clause.ISelectGroupByClause
import ch.ergon.dope.resolvable.clause.ISelectLimitClause
import ch.ergon.dope.resolvable.clause.ISelectOrderByClause
import ch.ergon.dope.resolvable.clause.model.OrderByType
import ch.ergon.dope.resolvable.clause.model.SelectLimitClause
import ch.ergon.dope.resolvable.clause.model.SelectOffsetClause
import ch.ergon.dope.resolvable.clause.model.SelectOrderByClause
import com.schwarz.crystalapi.schema.CMField

fun ISelectLimitClause.offset(numberField: CMField<Number>): SelectOffsetClause = offset(numberField.asField())

fun ISelectOrderByClause.limit(numberField: CMField<Number>): SelectLimitClause = limit(numberField.asField())

fun ISelectGroupByClause.orderBy(stringField: CMField<String>, orderByType: OrderByType): SelectOrderByClause =
    orderBy(stringField.asField(), orderByType)

fun ISelectGroupByClause.orderBy(stringField: CMField<String>): SelectOrderByClause = orderBy(stringField.asField())
