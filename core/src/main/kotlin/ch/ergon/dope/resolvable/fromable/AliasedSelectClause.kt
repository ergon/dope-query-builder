package ch.ergon.dope.resolvable.fromable

import ch.ergon.dope.resolvable.clause.ISelectClause

class AliasedSelectClause(private val alias: String, private val selectClause: ISelectClause) : Fromable {
    override fun toQueryString(): String = "(${selectClause.toQueryString()}) AS $alias"
}
