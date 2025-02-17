package ch.ergon.dope.extension.expression.type.function.conditional

import ch.ergon.dope.resolvable.expression.type.CaseExpression
import ch.ergon.dope.resolvable.expression.type.case
import ch.ergon.dope.resolvable.expression.type.otherwise
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList
import com.schwarz.crystalapi.schema.CMObjectField
import com.schwarz.crystalapi.schema.CMObjectList
import com.schwarz.crystalapi.schema.Schema

@JvmName("caseNumberField")
fun case(expression: CMJsonField<out Number>) = case(expression.toDopeType())

@JvmName("caseStringField")
fun case(expression: CMJsonField<String>) = case(expression.toDopeType())

@JvmName("caseBooleanField")
fun case(expression: CMJsonField<Boolean>) = case(expression.toDopeType())

@JvmName("caseObjectField")
fun case(expression: CMObjectField<Schema>) = case(expression.toDopeType())

@JvmName("caseNumberList")
fun case(expression: CMJsonList<out Number>) = case(expression.toDopeType())

@JvmName("caseStringList")
fun case(expression: CMJsonList<String>) = case(expression.toDopeType())

@JvmName("caseBooleanList")
fun case(expression: CMJsonList<Boolean>) = case(expression.toDopeType())

@JvmName("caseObjectList")
fun case(expression: CMObjectList<Schema>) = case(expression.toDopeType())

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

@JvmName("elseSimpleCMObjectWithGeneric")
fun <T : ValidType> CaseExpression<T, ObjectType>.otherwise(elseCase: CMObjectField<Schema>) =
    otherwise(elseCase.toDopeType())

@JvmName("elseSimpleCMObjectWithoutGeneric")
fun <T : ValidType> CaseExpression<T, out ValidType>.otherwise(elseCase: CMObjectField<Schema>) =
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

@JvmName("elseSearchedCMObjectWithGeneric")
fun <T : ValidType> CaseExpression<BooleanType, T>.otherwise(elseCase: CMObjectField<Schema>) =
    otherwise(elseCase.toDopeType())

@JvmName("elseSearchedCMObjectWithoutGeneric")
fun CaseExpression<BooleanType, ObjectType>.otherwise(elseCase: CMObjectField<Schema>) =
    otherwise(elseCase.toDopeType())
