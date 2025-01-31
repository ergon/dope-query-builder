package ch.ergon.dope.resolvable.expression

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.formatToQueryStringWithSymbol
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
            queryString = formatToQueryStringWithSymbol(typeExpressionDopeQuery.queryString, "AS", "`$alias`"),
            parameters = typeExpressionDopeQuery.parameters,
        )
    }
}

fun <T : ValidType> TypeExpression<T>.alias(alias: String): AliasedTypeExpression<T> = AliasedTypeExpression(this, alias)

fun Number.alias(string: String): AliasedTypeExpression<NumberType> = toDopeType().alias(string)

fun String.alias(string: String): AliasedTypeExpression<StringType> = toDopeType().alias(string)

fun Boolean.alias(string: String): AliasedTypeExpression<BooleanType> = toDopeType().alias(string)

fun <V> Map<String, V>.alias(string: String): AliasedTypeExpression<ObjectType> = toDopeType().alias(string)
