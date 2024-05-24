package ch.ergon.dope.extension.clause

import ch.ergon.dope.asField
import ch.ergon.dope.resolvable.clause.IDeleteClause
import ch.ergon.dope.resolvable.clause.IDeleteLimitClause
import ch.ergon.dope.resolvable.clause.IDeleteOffsetClause
import ch.ergon.dope.resolvable.clause.IDeleteWhereClause
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMType

fun IDeleteOffsetClause.returning(field: CMType, vararg fields: CMType) =
    returning(field.asField(), *fields.map { it.asField() }.toTypedArray())

fun IDeleteLimitClause.offset(numberExpression: CMField<Number>) = offset(numberExpression.asField())

fun IDeleteWhereClause.limit(numberExpression: CMField<Number>) = limit(numberExpression.asField())

fun IDeleteClause.where(booleanExpression: CMField<Boolean>) = where(booleanExpression.asField())
