package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.resolvable.clause.ISelectGroupByClause
import ch.ergon.dope.resolvable.clause.ISelectWhereClause
import ch.ergon.dope.resolvable.expression.type.IField
import ch.ergon.dope.validtype.ValidType

data class GroupByClause<T : ValidType>(
    val field: IField<out ValidType>,
    val fields: List<IField<out ValidType>> = emptyList(),
    val parentClause: ISelectWhereClause<T>,
) : ISelectGroupByClause<T>
