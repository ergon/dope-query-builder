package ch.ergon.dope.extension.type.conditional

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional.SearchResult
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMObjectField
import com.schwarz.crystalapi.schema.Schema

@JvmName("caseNumberField")
fun <U : ValidType> CMJsonField<out Number>.resultsIn(resultExpression: TypeExpression<U>) =
    SearchResult(toDopeType(), resultExpression)

@JvmName("caseStringField")
fun <U : ValidType> CMJsonField<String>.resultsIn(resultExpression: TypeExpression<U>) =
    SearchResult(toDopeType(), resultExpression)

@JvmName("caseBooleanField")
fun <U : ValidType> CMJsonField<Boolean>.resultsIn(resultExpression: TypeExpression<U>) =
    SearchResult(toDopeType(), resultExpression)

@JvmName("caseObjectField")
fun <U : ValidType> CMObjectField<Schema>.resultsIn(resultExpression: TypeExpression<U>) =
    SearchResult(toDopeType(), resultExpression)

@JvmName("caseNumberField")
fun <T : ValidType> TypeExpression<T>.resultsIn(resultField: CMJsonField<out Number>): SearchResult<T, NumberType> =
    SearchResult(this, resultField.toDopeType())

@JvmName("caseStringField")
fun <T : ValidType> TypeExpression<T>.resultsIn(resultField: CMJsonField<String>): SearchResult<T, StringType> =
    SearchResult(this, resultField.toDopeType())

@JvmName("caseBooleanField")
fun <T : ValidType> TypeExpression<T>.resultsIn(resultField: CMJsonField<Boolean>): SearchResult<T, BooleanType> =
    SearchResult(this, resultField.toDopeType())

@JvmName("caseObjectField")
fun <T : ValidType> TypeExpression<T>.resultsIn(resultField: CMObjectField<Schema>): SearchResult<T, ObjectType> =
    SearchResult(this, resultField.toDopeType())
