package ch.ergon.dope.extension.clause

import ch.ergon.dope.resolvable.clause.IDeleteClause
import ch.ergon.dope.resolvable.clause.IDeleteLimitClause
import ch.ergon.dope.resolvable.clause.IDeleteOffsetClause
import ch.ergon.dope.resolvable.clause.IDeleteWhereClause
import ch.ergon.dope.toDopeField
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMType

fun IDeleteOffsetClause.returning(field: CMType, vararg fields: CMType) =
    returning(field.toDopeField(), *fields.map { it.toDopeField() }.toTypedArray())

fun IDeleteLimitClause.offset(numberExpression: CMField<Number>) = offset(numberExpression.toDopeField())

fun IDeleteWhereClause.limit(numberExpression: CMField<Number>) = limit(numberExpression.toDopeField())

fun IDeleteClause.where(booleanExpression: CMField<Boolean>) = where(booleanExpression.toDopeField())
