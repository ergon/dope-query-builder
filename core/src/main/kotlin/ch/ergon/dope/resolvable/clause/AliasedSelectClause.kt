package ch.ergon.dope.resolvable.clause

class AliasedSelectClause(private val alias: String, private val selectClause: ISelectClause) : Fromable {
    override fun toQueryString(): String = "(${selectClause.toQueryString()}) AS $alias"
}
