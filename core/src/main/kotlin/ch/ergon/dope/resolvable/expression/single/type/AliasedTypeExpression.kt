package ch.ergon.dope.resolvable.expression.single.type

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.single.SingleExpression
import ch.ergon.dope.util.formatToQueryStringWithSymbol
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class AliasedTypeExpression<T : ValidType>(
    private val typeExpression: TypeExpression<T>,
    private val alias: String,
) : SingleExpression<T> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val typeExpressionDopeQuery = typeExpression.toDopeQuery(manager)
        return DopeQuery(
            queryString = formatToQueryStringWithSymbol(typeExpressionDopeQuery.queryString, symbol = "AS", "`$alias`"),
            parameters = typeExpressionDopeQuery.parameters,
        )
    }
}

fun <T : ValidType> TypeExpression<T>.alias(alias: String): AliasedTypeExpression<T> = AliasedTypeExpression(this, alias)

fun Number.alias(alias: String): AliasedTypeExpression<NumberType> = toDopeType().alias(alias)

fun String.alias(alias: String): AliasedTypeExpression<StringType> = toDopeType().alias(alias)

fun Boolean.alias(alias: String): AliasedTypeExpression<BooleanType> = toDopeType().alias(alias)

fun <V> Map<String, V>.alias(alias: String): AliasedTypeExpression<ObjectType> = toDopeType().alias(alias)
