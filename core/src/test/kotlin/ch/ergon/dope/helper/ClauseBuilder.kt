package ch.ergon.dope.helper

import ch.ergon.dope.resolvable.Asterisk
import ch.ergon.dope.resolvable.Selectable
import ch.ergon.dope.resolvable.bucket.Bucket
import ch.ergon.dope.resolvable.bucket.UnaliasedBucket
import ch.ergon.dope.resolvable.clause.model.DeleteClause
import ch.ergon.dope.resolvable.clause.model.FromClause
import ch.ergon.dope.resolvable.clause.model.SelectClause
import ch.ergon.dope.resolvable.clause.model.SelectRawClause
import ch.ergon.dope.resolvable.clause.model.UpdateClause
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

fun someSelectClause(selectable: Selectable = Asterisk()) = SelectClause(selectable)

fun someSelectRawClause() = someStringSelectRawClause()

fun someStringSelectRawClause(expression: TypeExpression<StringType> = someStringField()) =
    SelectRawClause(expression)

fun someNumberSelectRawClause(expression: TypeExpression<NumberType> = someNumberField()) =
    SelectRawClause(expression)

fun someBooleanSelectRawClause(expression: TypeExpression<BooleanType> = someBooleanField()) =
    SelectRawClause(expression)

fun someDeleteClause(bucket: Bucket = someBucket()) = DeleteClause(bucket)

fun someUpdateClause(bucket: Bucket = someBucket()) = UpdateClause(bucket)

fun someFromClause(bucket: UnaliasedBucket = someBucket(), parent: SelectClause = someSelectClause()) = FromClause(bucket, parent)
