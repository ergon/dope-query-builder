package ch.ergon.dope.helper

import ch.ergon.dope.resolvable.Asterisk
import ch.ergon.dope.resolvable.Selectable
import ch.ergon.dope.resolvable.clause.model.DeleteClause
import ch.ergon.dope.resolvable.clause.model.FromClause
import ch.ergon.dope.resolvable.clause.model.SelectClause
import ch.ergon.dope.resolvable.clause.model.SelectRawClause
import ch.ergon.dope.resolvable.clause.model.UpdateClause
import ch.ergon.dope.resolvable.clause.model.WithClause
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.type.DopeVariable
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.assignTo
import ch.ergon.dope.resolvable.keyspace.Keyspace
import ch.ergon.dope.resolvable.keyspace.UnaliasedKeyspace
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

fun someWithClause(withExpression: DopeVariable<out ValidType> = "alias".assignTo(someNumber())) = WithClause(withExpression)

fun someSelectClause(selectable: Selectable = Asterisk()) = SelectClause(selectable)

fun someSelectRawClause() = someStringSelectRawClause()

fun someStringSelectRawClause(expression: TypeExpression<StringType> = someStringField()) =
    SelectRawClause(expression)

fun someNumberSelectRawClause(expression: TypeExpression<NumberType> = someNumberField()) =
    SelectRawClause(expression)

fun someBooleanSelectRawClause(expression: TypeExpression<BooleanType> = someBooleanField()) =
    SelectRawClause(expression)

fun someObjectSelectRawClause(expression: TypeExpression<ObjectType> = someObjectField()) =
    SelectRawClause(expression)

fun someAnyTypeSelectRawClause(expression: Expression<ValidType> = someAnyTypeField()) = SelectRawClause(expression)

fun someDeleteClause(keyspace: Keyspace = someKeyspace()) = DeleteClause(keyspace)

fun someUpdateClause(keyspace: Keyspace = someKeyspace()) = UpdateClause(keyspace)

fun someFromClause(keyspace: UnaliasedKeyspace = someKeyspace(), parent: SelectClause = someSelectClause()) =
    FromClause(keyspace, parent)
