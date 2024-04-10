package ch.ergon.dope.extension.clause

import ch.ergon.dope.asField
import ch.ergon.dope.resolvable.clause.ClauseBuilder
import ch.ergon.dope.resolvable.clause.select.factory.Limit
import ch.ergon.dope.resolvable.clause.select.factory.OrderBy
import com.schwarz.crystalapi.schema.CMField

fun OrderBy.orderBy(stringField: CMField<String>): Limit = ClauseBuilder(clauses).orderBy(stringField.asField())
