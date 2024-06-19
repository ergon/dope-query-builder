package ch.ergon.dope.helper

import ch.ergon.dope.resolvable.clause.ISelectUnnestClause
import ch.ergon.dope.resolvable.clause.model.DeleteClause
import ch.ergon.dope.resolvable.clause.model.FromClause
import ch.ergon.dope.resolvable.clause.model.SelectClause
import ch.ergon.dope.resolvable.expression.AsteriskExpression
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.fromable.Bucket
import ch.ergon.dope.resolvable.fromable.UnaliasedBucket

fun someSelectClause(expression: Expression = AsteriskExpression()) = SelectClause(expression)

fun someDeleteClause(bucket: Bucket = someBucket()) = DeleteClause(bucket)

fun someFromClause(bucket: UnaliasedBucket = someBucket(), parent: SelectClause = someSelectClause()): ISelectUnnestClause =
    FromClause(bucket, parent)
