package ch.ergon.dope.extension.type.conditional

import ch.ergon.dope.resolvable.expression.unaliased.type.conditional.CaseClass
import ch.ergon.dope.resolvable.expression.unaliased.type.conditional.CaseExpression
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

@JvmName("elseSimpleCMNumberWithGeneric")
fun <T : ValidType> CaseExpression<T, NumberType>.otherwise(elseCase: CMJsonField<out Number>) =
    otherwise(elseCase.toDopeType())

@JvmName("elseSimpleCMNumberWithoutGeneric")
fun <T : ValidType> CaseExpression<T, out ValidType>.otherwise(elseCase: CMJsonField<out Number>) =
    otherwise(elseCase.toDopeType())

@JvmName("elseSimpleCMStringWithGeneric")
fun <T : ValidType> CaseExpression<T, StringType>.otherwise(elseCase: CMJsonField<String>) =
    otherwise(elseCase.toDopeType())

@JvmName("elseSimpleCMStringWithoutGeneric")
fun <T : ValidType> CaseExpression<T, out ValidType>.otherwise(elseCase: CMJsonField<String>) =
    otherwise(elseCase.toDopeType())

@JvmName("elseSimpleCMBooleanWithGeneric")
fun <T : ValidType> CaseExpression<T, BooleanType>.otherwise(elseCase: CMJsonField<Boolean>) =
    otherwise(elseCase.toDopeType())

@JvmName("elseSimpleCMBooleanWithoutGeneric")
fun <T : ValidType> CaseExpression<T, out ValidType>.otherwise(elseCase: CMJsonField<Boolean>) =
    otherwise(elseCase.toDopeType())

@JvmName("elseSearchedCMNumberWithGeneric")
fun <T : ValidType> CaseExpression<BooleanType, T>.otherwise(elseCase: CMJsonField<out Number>) =
    otherwise(elseCase.toDopeType())

@JvmName("elseSearchedCMNumberWithoutGeneric")
fun CaseExpression<BooleanType, NumberType>.otherwise(elseCase: CMJsonField<out Number>) =
    otherwise(elseCase.toDopeType())

@JvmName("elseSearchedCMStringWithGeneric")
fun <T : ValidType> CaseExpression<BooleanType, T>.otherwise(elseCase: CMJsonField<String>) =
    otherwise(elseCase.toDopeType())

@JvmName("elseSearchedCMStringWithoutGeneric")
fun CaseExpression<BooleanType, StringType>.otherwise(elseCase: CMJsonField<String>) =
    otherwise(elseCase.toDopeType())

@JvmName("elseSearchedCMBooleanWithGeneric")
fun <T : ValidType> CaseExpression<BooleanType, T>.otherwise(elseCase: CMJsonField<Boolean>) =
    otherwise(elseCase.toDopeType())

@JvmName("elseSearchedCMBooleanWithoutGeneric")
fun CaseExpression<BooleanType, BooleanType>.otherwise(elseCase: CMJsonField<Boolean>) =
    otherwise(elseCase.toDopeType())
