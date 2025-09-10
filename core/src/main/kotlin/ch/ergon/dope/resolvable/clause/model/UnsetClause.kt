package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.resolvable.clause.IUpdateSetClause
import ch.ergon.dope.resolvable.clause.IUpdateUnsetClause
import ch.ergon.dope.resolvable.expression.type.IField
import ch.ergon.dope.validtype.ValidType

data class UnsetClause(
    val field: IField<out ValidType>,
    val fields: List<IField<out ValidType>> = emptyList(),
    val parentClause: IUpdateSetClause,
) : IUpdateUnsetClause
