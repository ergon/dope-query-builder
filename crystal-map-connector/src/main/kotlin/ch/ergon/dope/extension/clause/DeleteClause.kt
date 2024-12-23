package ch.ergon.dope.extension.clause

import ch.ergon.dope.resolvable.clause.IDeleteClause
import ch.ergon.dope.resolvable.clause.IDeleteLimitClause
import ch.ergon.dope.resolvable.clause.IDeleteOffsetClause
import ch.ergon.dope.resolvable.clause.IDeleteWhereClause
import ch.ergon.dope.resolvable.clause.model.DeleteReturningClause
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMType

fun IDeleteOffsetClause.returning(field: CMType) =
    returning(field.toDopeType())

fun DeleteReturningClause.thenReturning(field: CMType) =
    thenReturning(field.toDopeType())

fun IDeleteOffsetClause.returningRaw(field: CMType) =
    returningRaw(field.toDopeType())

fun IDeleteOffsetClause.returningValue(field: CMType) =
    returningValue(field.toDopeType())

fun IDeleteOffsetClause.returningElement(field: CMType) =
    returningElement(field.toDopeType())

fun IDeleteLimitClause.offset(numberExpression: CMJsonField<Number>) = offset(numberExpression.toDopeType())

fun IDeleteWhereClause.limit(numberExpression: CMJsonField<Number>) = limit(numberExpression.toDopeType())

fun IDeleteClause.where(booleanExpression: CMJsonField<Boolean>) = where(booleanExpression.toDopeType())
