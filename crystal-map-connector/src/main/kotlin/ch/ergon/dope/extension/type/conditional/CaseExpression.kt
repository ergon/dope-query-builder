package ch.ergon.dope.extension.type.conditional

import ch.ergon.dope.resolvable.expression.unaliased.type.conditional.CaseClass
import ch.ergon.dope.resolvable.expression.unaliased.type.conditional.SearchedCaseExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.conditional.SimpleCaseExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.conditional.otherwise
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList

@JvmName("caseNumberField")
fun case(expression: CMJsonField<out Number>) = CaseClass(expression.toDopeType())

@JvmName("caseStringField")
fun case(expression: CMJsonField<String>) = CaseClass(expression.toDopeType())

@JvmName("caseBooleanField")
fun case(expression: CMJsonField<Boolean>) = CaseClass(expression.toDopeType())

@JvmName("caseNumberList")
fun case(expression: CMJsonList<out Number>) = CaseClass(expression.toDopeType())

@JvmName("caseStringList")
fun case(expression: CMJsonList<String>) = CaseClass(expression.toDopeType())

@JvmName("caseBooleanList")
fun case(expression: CMJsonList<Boolean>) = CaseClass(expression.toDopeType())

@JvmName("elseSearchedCMNumberWithGeneric")
fun <T : ValidType> SimpleCaseExpression<T, NumberType>.otherwise(elseCase: CMJsonField<out Number>) =
    otherwise(elseCase.toDopeType())

@JvmName("elseSearchedCMNumberWithoutGeneric")
fun <T : ValidType> SimpleCaseExpression<T, out ValidType>.otherwise(elseCase: CMJsonField<out Number>) =
    otherwise(elseCase.toDopeType())

@JvmName("elseSearchedCMStringWithGeneric")
fun <T : ValidType> SimpleCaseExpression<T, StringType>.otherwise(elseCase: CMJsonField<String>) =
    otherwise(elseCase.toDopeType())

@JvmName("elseSearchedCMStringWithoutGeneric")
fun <T : ValidType> SimpleCaseExpression<T, out ValidType>.otherwise(elseCase: CMJsonField<String>) =
    otherwise(elseCase.toDopeType())

@JvmName("elseSearchedCMBooleanWithGeneric")
fun <T : ValidType> SimpleCaseExpression<T, BooleanType>.otherwise(elseCase: CMJsonField<Boolean>) =
    otherwise(elseCase.toDopeType())

@JvmName("elseSearchedCMBooleanWithoutGeneric")
fun <T : ValidType> SimpleCaseExpression<T, out ValidType>.otherwise(elseCase: CMJsonField<Boolean>) =
    otherwise(elseCase.toDopeType())

@JvmName("elseSearchedCMNumberWithGeneric")
fun <T : ValidType> SearchedCaseExpression<T>.otherwise(elseCase: CMJsonField<out Number>) =
    otherwise(elseCase.toDopeType())

@JvmName("elseSearchedCMNumberWithoutGeneric")
fun SearchedCaseExpression<NumberType>.otherwise(elseCase: CMJsonField<out Number>) =
    otherwise(elseCase.toDopeType())

@JvmName("elseSearchedCMStringWithGeneric")
fun <T : ValidType> SearchedCaseExpression<T>.otherwise(elseCase: CMJsonField<String>) =
    otherwise(elseCase.toDopeType())

@JvmName("elseSearchedCMStringWithoutGeneric")
fun SearchedCaseExpression<StringType>.otherwise(elseCase: CMJsonField<String>) =
    otherwise(elseCase.toDopeType())

@JvmName("elseSearchedCMBooleanWithGeneric")
fun <T : ValidType> SearchedCaseExpression<T>.otherwise(elseCase: CMJsonField<Boolean>) =
    otherwise(elseCase.toDopeType())

@JvmName("elseSearchedCMBooleanWithoutGeneric")
fun SearchedCaseExpression<BooleanType>.otherwise(elseCase: CMJsonField<Boolean>) =
    otherwise(elseCase.toDopeType())
