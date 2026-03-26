package ch.ergon.dope.couchbase.resolver

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolver.clause.ClauseResolver
import ch.ergon.dope.couchbase.resolver.expression.ExpressionResolver
import ch.ergon.dope.resolvable.Asterisk
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.bucket.AliasedBucketDefinition
import ch.ergon.dope.resolvable.bucket.IndexReference
import ch.ergon.dope.resolvable.bucket.UseIndex
import ch.ergon.dope.resolvable.bucket.UseKeysClass
import ch.ergon.dope.resolvable.clause.Clause
import ch.ergon.dope.resolvable.clause.joinHint.HashOrNestedLoopHint
import ch.ergon.dope.resolvable.clause.joinHint.KeysOrIndexHint
import ch.ergon.dope.resolvable.clause.model.OrderExpression
import ch.ergon.dope.resolvable.clause.model.SetAssignment
import ch.ergon.dope.resolvable.clause.model.WindowDeclaration
import ch.ergon.dope.resolvable.clause.model.WithClause
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.Between
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.CurrentRow
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.Following
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OrderingTerm
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.Preceding
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.UnboundedFollowing
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.UnboundedPreceding
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowFrameClause
import ch.ergon.dope.resolvable.expression.type.CaseClass
import ch.ergon.dope.resolvable.expression.type.ObjectEntryPrimitive
import ch.ergon.dope.resolvable.expression.type.function.token.ContainsTokenOptions
import ch.ergon.dope.resolvable.expression.type.function.token.CustomTokenOptions
import ch.ergon.dope.resolver.QueryResolver

interface AbstractCouchbaseResolver : QueryResolver<CouchbaseDopeQuery> {
    abstract override val manager: DopeQueryManager
}

class CouchbaseResolver(
    override val manager: DopeQueryManager = DopeQueryManager(),
) : ClauseResolver, ExpressionResolver, KeySpaceResolver, WindowResolver {
    override fun resolve(resolvable: Resolvable): CouchbaseDopeQuery =
        when (resolvable) {
            is Clause -> resolve(resolvable)

            is WithClause -> resolve(resolvable)

            is OrderExpression -> resolve(resolvable)

            is SetAssignment<*> -> resolve(resolvable)

            is Expression<*> -> resolve(resolvable)

            is AliasedBucketDefinition -> resolve(resolvable)

            is UseKeysClass -> resolve(resolvable)

            is IndexReference -> resolve(resolvable)

            is UseIndex -> resolve(resolvable)

            is ObjectEntryPrimitive<*> -> resolve(resolvable)

            is OverDefinition -> resolve(resolvable)

            is WindowDefinition -> resolve(resolvable)

            is WindowFrameClause -> resolve(resolvable)

            is Between -> resolve(resolvable)

            is UnboundedFollowing -> CouchbaseDopeQuery("UNBOUNDED FOLLOWING")

            is UnboundedPreceding -> CouchbaseDopeQuery("UNBOUNDED PRECEDING")

            is CurrentRow -> CouchbaseDopeQuery("CURRENT ROW")

            is Following -> resolve(resolvable)

            is Preceding -> resolve(resolvable)

            is OrderingTerm -> resolve(resolvable)

            is WindowDeclaration -> resolve(resolvable)

            is CaseClass<*> -> resolve(resolvable)

            is Asterisk -> resolve(resolvable)

            is ContainsTokenOptions -> resolve(resolvable)

            is CustomTokenOptions -> resolve(resolvable)

            is HashOrNestedLoopHint -> resolve(resolvable)

            is KeysOrIndexHint -> resolve(resolvable)

            else -> throw UnsupportedOperationException("Not supported: $resolvable")
        }
}
