package ch.ergon.dope.extension.type.conditional

import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.conditional.SearchResult
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType
import com.schwarz.crystalapi.schema.CMField

@JvmName("caseNumberField")
fun <U : ValidType> CMField<out Number>.resultsIn(resultExpression: UnaliasedExpression<U>) =
    SearchResult(toDopeType(), resultExpression)

@JvmName("caseStringField")
fun <U : ValidType> CMField<String>.resultsIn(resultExpression: UnaliasedExpression<U>) =
    SearchResult(toDopeType(), resultExpression)

@JvmName("caseBooleanField")
fun <U : ValidType> CMField<Boolean>.resultsIn(resultExpression: UnaliasedExpression<U>) =
    SearchResult(toDopeType(), resultExpression)

@JvmName("caseNumberField")
fun <T : ValidType> UnaliasedExpression<T>.resultsIn(resultField: CMField<out Number>): SearchResult<T, NumberType> =
    SearchResult(this, resultField.toDopeType())

@JvmName("caseStringField")
fun <T : ValidType> UnaliasedExpression<T>.resultsIn(resultField: CMField<String>): SearchResult<T, StringType> =
    SearchResult(this, resultField.toDopeType())

@JvmName("caseBooleanField")
fun <T : ValidType> UnaliasedExpression<T>.resultsIn(resultField: CMField<Boolean>): SearchResult<T, BooleanType> =
    SearchResult(this, resultField.toDopeType())
