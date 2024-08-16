package ch.ergon.dope.extension

import ch.ergon.dope.resolvable.expression.CaseClass
import ch.ergon.dope.resolvable.expression.SearchedCaseExpression
import ch.ergon.dope.resolvable.expression.SimpleCaseExpression
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.resolvable.expression.otherwise
import ch.ergon.dope.resolvable.expression.whenThen
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList
import com.schwarz.crystalapi.schema.CMType

@JvmName("caseNumberField")
fun case(expression: CMField<out Number>) = CaseClass(expression.toDopeType())

@JvmName("caseStringField")
fun case(expression: CMField<String>) = CaseClass(expression.toDopeType())

@JvmName("caseBooleanField")
fun case(expression: CMField<Boolean>) = CaseClass(expression.toDopeType())

@JvmName("caseNumberList")
fun case(expression: CMList<out Number>) = CaseClass(expression.toDopeType())

@JvmName("caseStringList")
fun case(expression: CMList<String>) = CaseClass(expression.toDopeType())

@JvmName("caseBooleanList")
fun case(expression: CMList<Boolean>) = CaseClass(expression.toDopeType())

@JvmName("whenNumberFieldUnaliased")
fun CaseClass<NumberType>.whenThen(field: CMField<out Number>, expression: UnaliasedExpression<out ValidType>) =
    whenThen(field.toDopeType(), expression)

@JvmName("whenStringFieldUnaliased")
fun CaseClass<StringType>.whenThen(field: CMField<String>, expression: UnaliasedExpression<out ValidType>) =
    whenThen(field.toDopeType(), expression)

@JvmName("whenBooleanFieldUnaliased")
fun CaseClass<BooleanType>.whenThen(field: CMField<Boolean>, expression: UnaliasedExpression<out ValidType>) =
    whenThen(field.toDopeType(), expression)

@JvmName("whenNumberListUnaliased")
fun CaseClass<ArrayType<NumberType>>.whenThen(field: CMList<out Number>, expression: UnaliasedExpression<out ValidType>) =
    whenThen(field.toDopeType(), expression)

@JvmName("whenStringListUnaliased")
fun CaseClass<ArrayType<StringType>>.whenThen(field: CMList<String>, expression: UnaliasedExpression<out ValidType>) =
    whenThen(field.toDopeType(), expression)

@JvmName("whenBooleanListUnaliased")
fun CaseClass<ArrayType<BooleanType>>.whenThen(field: CMList<Boolean>, expression: UnaliasedExpression<out ValidType>) =
    whenThen(field.toDopeType(), expression)

@JvmName("whenNumberFieldCMType")
fun CaseClass<NumberType>.whenThen(field: CMField<out Number>, expression: CMType) =
    whenThen(field.toDopeType(), expression.toDopeType())

@JvmName("whenStringFieldCMType")
fun CaseClass<StringType>.whenThen(field: CMField<String>, expression: CMType) =
    whenThen(field.toDopeType(), expression.toDopeType())

@JvmName("whenBooleanFieldCMType")
fun CaseClass<BooleanType>.whenThen(field: CMField<Boolean>, expression: CMType) =
    whenThen(field.toDopeType(), expression.toDopeType())

@JvmName("whenNumberListCMType")
fun CaseClass<ArrayType<NumberType>>.whenThen(field: CMList<out Number>, expression: CMType) =
    whenThen(field.toDopeType(), expression.toDopeType())

@JvmName("whenStringListCMType")
fun CaseClass<ArrayType<StringType>>.whenThen(field: CMList<String>, expression: CMType) =
    whenThen(field.toDopeType(), expression.toDopeType())

@JvmName("whenBooleanListCMType")
fun CaseClass<ArrayType<BooleanType>>.whenThen(field: CMList<Boolean>, expression: CMType) =
    whenThen(field.toDopeType(), expression.toDopeType())

@JvmName("whenSimpleNumberFieldUnaliased")
fun SimpleCaseExpression<NumberType>.whenThen(whenExpression: CMField<out Number>, type: UnaliasedExpression<out ValidType>) =
    whenThen(whenExpression.toDopeType(), type)

@JvmName("whenSimpleStringFieldUnaliased")
fun SimpleCaseExpression<StringType>.whenThen(whenExpression: CMField<String>, type: UnaliasedExpression<out ValidType>) =
    whenThen(whenExpression.toDopeType(), type)

@JvmName("whenSimpleBooleanFieldUnaliased")
fun SimpleCaseExpression<BooleanType>.whenThen(whenExpression: CMField<Boolean>, type: UnaliasedExpression<out ValidType>) =
    whenThen(whenExpression.toDopeType(), type)

@JvmName("whenSimpleNumberListUnaliased")
fun SimpleCaseExpression<ArrayType<NumberType>>.whenThen(whenExpression: CMList<out Number>, type: UnaliasedExpression<out ValidType>) =
    whenThen(whenExpression.toDopeType(), type)

@JvmName("whenSimpleStringListUnaliased")
fun SimpleCaseExpression<ArrayType<StringType>>.whenThen(whenExpression: CMList<String>, type: UnaliasedExpression<out ValidType>) =
    whenThen(whenExpression.toDopeType(), type)

@JvmName("whenSimpleBooleanListUnaliased")
fun SimpleCaseExpression<ArrayType<BooleanType>>.whenThen(whenExpression: CMList<Boolean>, type: UnaliasedExpression<out ValidType>) =
    whenThen(whenExpression.toDopeType(), type)

@JvmName("whenSimpleNumberFieldCMType")
fun SimpleCaseExpression<NumberType>.whenThen(whenExpression: CMField<out Number>, type: CMType) =
    whenThen(whenExpression.toDopeType(), type.toDopeType())

@JvmName("whenSimpleStringFieldCMType")
fun SimpleCaseExpression<StringType>.whenThen(whenExpression: CMField<String>, type: CMType) =
    whenThen(whenExpression.toDopeType(), type.toDopeType())

@JvmName("whenSimpleBooleanFieldCMType")
fun SimpleCaseExpression<BooleanType>.whenThen(whenExpression: CMField<Boolean>, type: CMType) =
    whenThen(whenExpression.toDopeType(), type.toDopeType())

@JvmName("whenSimpleNumberListCMType")
fun SimpleCaseExpression<ArrayType<NumberType>>.whenThen(whenExpression: CMList<out Number>, type: CMType) =
    whenThen(whenExpression.toDopeType(), type.toDopeType())

@JvmName("whenSimpleStringListCMType")
fun SimpleCaseExpression<ArrayType<StringType>>.whenThen(whenExpression: CMList<String>, type: CMType) =
    whenThen(whenExpression.toDopeType(), type.toDopeType())

@JvmName("whenSimpleBooleanListCMType")
fun SimpleCaseExpression<ArrayType<BooleanType>>.whenThen(whenExpression: CMList<Boolean>, type: CMType) =
    whenThen(whenExpression.toDopeType(), type.toDopeType())

@JvmName("whenTypeExpressionCMType")
fun <T : ValidType> SimpleCaseExpression<T>.whenThen(whenExpression: TypeExpression<T>, type: CMType) =
    whenThen(whenExpression, type.toDopeType())

@JvmName("elseSimpleCMType")
fun <T : ValidType> SimpleCaseExpression<T>.otherwise(elseCase: CMType) =
    otherwise(elseCase.toDopeType())

@JvmName("whenCMFieldBooleanUnaliased")
fun whenThen(whenCondition: CMField<Boolean>, thenExpression: UnaliasedExpression<out ValidType>) =
    whenThen(whenCondition.toDopeType(), thenExpression)

@JvmName("whenTypeExpressionBooleanCMType")
fun whenThen(whenCondition: TypeExpression<BooleanType>, thenExpression: CMType) =
    whenThen(whenCondition, thenExpression.toDopeType())

@JvmName("whenCMFieldBooleanCMType")
fun whenThen(whenCondition: CMField<Boolean>, thenExpression: CMType) =
    whenThen(whenCondition.toDopeType(), thenExpression.toDopeType())

@JvmName("whenSearchedCMFieldBooleanUnaliased")
fun SearchedCaseExpression.whenThen(whenCondition: CMField<Boolean>, thenExpression: UnaliasedExpression<out ValidType>) =
    whenThen(whenCondition.toDopeType(), thenExpression)

@JvmName("whenSearchedTypeExpressionBooleanCMType")
fun SearchedCaseExpression.whenThen(whenCondition: TypeExpression<BooleanType>, thenExpression: CMType) =
    whenThen(whenCondition, thenExpression.toDopeType())

@JvmName("whenSearchedCMFieldBooleanCMType")
fun SearchedCaseExpression.whenThen(whenCondition: CMField<Boolean>, thenExpression: CMType) =
    whenThen(whenCondition.toDopeType(), thenExpression.toDopeType())

@JvmName("elseSearchedCMType")
fun SearchedCaseExpression.otherwise(elseCase: CMType) =
    otherwise(elseCase.toDopeType())
