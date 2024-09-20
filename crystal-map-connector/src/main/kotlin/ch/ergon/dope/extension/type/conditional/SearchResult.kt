package ch.ergon.dope.extension.type.conditional

import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional.SearchResult
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType
import com.schwarz.crystalapi.schema.CMJsonField

@JvmName("caseNumberField")
fun <U : ValidType> CMJsonField<out Number>.resultsIn(resultExpression: UnaliasedExpression<U>) =
    SearchResult(toDopeType(), resultExpression)

@JvmName("caseStringField")
fun <U : ValidType> CMJsonField<String>.resultsIn(resultExpression: UnaliasedExpression<U>) =
    SearchResult(toDopeType(), resultExpression)

@JvmName("caseBooleanField")
fun <U : ValidType> CMJsonField<Boolean>.resultsIn(resultExpression: UnaliasedExpression<U>) =
    SearchResult(toDopeType(), resultExpression)

@JvmName("caseNumberField")
fun <T : ValidType> UnaliasedExpression<T>.resultsIn(resultField: CMJsonField<out Number>): SearchResult<T, NumberType> =
    SearchResult(this, resultField.toDopeType())

@JvmName("caseStringField")
fun <T : ValidType> UnaliasedExpression<T>.resultsIn(resultField: CMJsonField<String>): SearchResult<T, StringType> =
    SearchResult(this, resultField.toDopeType())

@JvmName("caseBooleanField")
fun <T : ValidType> UnaliasedExpression<T>.resultsIn(resultField: CMJsonField<Boolean>): SearchResult<T, BooleanType> =
    SearchResult(this, resultField.toDopeType())
