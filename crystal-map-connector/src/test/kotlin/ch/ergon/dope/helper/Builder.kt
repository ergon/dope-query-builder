package ch.ergon.dope.helper

import ch.ergon.dope.resolvable.clause.model.DeleteClause
import ch.ergon.dope.resolvable.clause.model.FromClause
import ch.ergon.dope.resolvable.clause.model.SelectClause
import ch.ergon.dope.resolvable.expression.AsteriskExpression
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.fromable.AliasedBucket
import ch.ergon.dope.resolvable.fromable.Bucket
import ch.ergon.dope.resolvable.fromable.Fromable
import ch.ergon.dope.resolvable.fromable.UnaliasedBucket
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

fun someBucket(name: String = "someBucket") = UnaliasedBucket(name)

fun someCMNumberField(name: String = "CMNumberField", path: String = "") = CMField<Number>(name, path)
fun someCMStringField(name: String = "CMStringField", path: String = "") = CMField<String>(name, path)
fun someCMBooleanField(name: String = "CMBooleanField", path: String = "") = CMField<Boolean>(name, path)

fun someCMNumberList(name: String = "CMNumberList", path: String = "") = CMList<Number>(name, path)
fun someCMStringList(name: String = "CMStringList", path: String = "") = CMList<String>(name, path)
fun someCMBooleanList(name: String = "CMBooleanList", path: String = "") = CMList<Boolean>(name, path)

fun someSelect(exception: Expression = AsteriskExpression()) = SelectClause(exception)
fun someFrom(fromable: Fromable = someBucket(), selectClause: SelectClause = someSelect()) = FromClause(fromable, selectClause)

fun someDelete(bucket: Bucket = someBucket()) = DeleteClause(bucket)

fun someNumber(value: Number = 5) = value

fun someString(value: String = "someString") = value

fun someBoolean(value: Boolean = true) = value

fun someNumberField(name: String = "numberField", bucket: Bucket = someBucket("")) = Field<NumberType>(name, getBucketName(bucket))
fun someStringField(name: String = "stringField", bucket: Bucket = someBucket("")) = Field<StringType>(name, getBucketName(bucket))
fun someBooleanField(name: String = "booleanField", bucket: Bucket = someBucket("")) = Field<BooleanType>(name, getBucketName(bucket))

private fun getBucketName(bucket: Bucket): String = when (bucket) {
    is AliasedBucket -> bucket.alias
    is UnaliasedBucket -> bucket.name
}
