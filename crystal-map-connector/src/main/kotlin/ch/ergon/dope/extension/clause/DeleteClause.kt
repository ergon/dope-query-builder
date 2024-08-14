package ch.ergon.dope.extension.clause

import ch.ergon.dope.resolvable.clause.IDeleteClause
import ch.ergon.dope.resolvable.clause.IDeleteLimitClause
import ch.ergon.dope.resolvable.clause.IDeleteOffsetClause
import ch.ergon.dope.resolvable.clause.IDeleteWhereClause
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMType

fun IDeleteOffsetClause.returning(field: CMType, vararg fields: CMType) =
    returning(field.toDopeType(), *fields.map { it.toDopeType() }.toTypedArray())

fun IDeleteLimitClause.offset(numberExpression: CMField<Number>) = offset(numberExpression.toDopeType())

fun IDeleteWhereClause.limit(numberExpression: CMField<Number>) = limit(numberExpression.toDopeType())

fun IDeleteClause.where(booleanExpression: CMField<Boolean>) = where(booleanExpression.toDopeType())
