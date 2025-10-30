package ch.ergon.dope.mongo.resolver

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.mongo.MongoDopeQuery
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.clause.Clause
import ch.ergon.dope.resolvable.clause.model.OrderExpression
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolver.QueryResolver

interface AbstractMongoResolver : QueryResolver<MongoDopeQuery> {
    abstract override val manager: DopeQueryManager
}

class MongoResolver(
    override val manager: DopeQueryManager = DopeQueryManager(),
) : ClauseResolver, ExpressionResolver {
    override fun resolve(resolvable: Resolvable): MongoDopeQuery =
        when (resolvable) {
            is Clause -> resolve(resolvable)

            is OrderExpression -> resolve(resolvable)

            is Expression<*> -> resolve(resolvable)

            else -> TODO("not yet implemented: $resolvable")
        }
}
