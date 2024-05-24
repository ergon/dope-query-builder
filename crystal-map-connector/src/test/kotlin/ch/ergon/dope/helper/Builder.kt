package ch.ergon.dope.helper

import ch.ergon.dope.resolvable.clause.model.DeleteClause
import ch.ergon.dope.resolvable.clause.model.FromClause
import ch.ergon.dope.resolvable.clause.model.SelectClause
import ch.ergon.dope.resolvable.expression.AsteriskExpression
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.fromable.Bucket
import ch.ergon.dope.resolvable.fromable.Fromable
import ch.ergon.dope.resolvable.fromable.UnaliasedBucket
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

fun someBucket(name: String = "someBucket") = UnaliasedBucket(name)

fun someCMNumberField(name: String = "someNumberField", path: String = "") = CMField<Number>(name, path)
fun someCMStringField(name: String = "someStringField", path: String = "") = CMField<String>(name, path)
fun someCMBooleanField(name: String = "someBooleanField", path: String = "") = CMField<Boolean>(name, path)

fun someCMNumberList(name: String = "someNumberList", path: String = "") = CMList<Number>(name, path)
fun someCMStringList(name: String = "someStringList", path: String = "") = CMList<String>(name, path)
fun someCMBooleanList(name: String = "someBooleanList", path: String = "") = CMList<Boolean>(name, path)

fun someSelect(exception: Expression = AsteriskExpression()) = SelectClause(exception)
fun someFrom(fromable: Fromable = someBucket(), selectClause: SelectClause = someSelect()) = FromClause(fromable, selectClause)

fun someDelete(bucket: Bucket = someBucket()) = DeleteClause(bucket)
