package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.clause.IUpdateClause
import ch.ergon.dope.resolvable.clause.IUpdateSetClause
import ch.ergon.dope.resolvable.expression.type.IField
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

data class SetClause(
    val setAssignment: SetAssignment<out ValidType>,
    val setAssignments: List<SetAssignment<out ValidType>> = emptyList(),
    val parentClause: IUpdateClause,
) : IUpdateSetClause

data class SetAssignment<T : ValidType>(
    val field: IField<T>,
    val value: TypeExpression<out T>,
) : Resolvable

fun <T : ValidType> IField<T>.toNewValue(value: TypeExpression<out T>) = SetAssignment(this, value)

fun IField<NumberType>.toNewValue(value: Number) = toNewValue(value.toDopeType())

fun IField<StringType>.toNewValue(value: String) = toNewValue(value.toDopeType())

fun IField<BooleanType>.toNewValue(value: Boolean) = toNewValue(value.toDopeType())
