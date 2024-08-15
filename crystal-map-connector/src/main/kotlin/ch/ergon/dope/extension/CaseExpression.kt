package ch.ergon.dope.extension

import ch.ergon.dope.resolvable.expression.CaseClass
import ch.ergon.dope.resolvable.expression.SearchedCaseExpression
import ch.ergon.dope.resolvable.expression.SimpleCaseExpression
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.resolvable.expression.`else`
import ch.ergon.dope.resolvable.expression.`when`
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
fun CaseClass<NumberType>.`when`(field: CMField<out Number>, expression: UnaliasedExpression<out ValidType>) =
    `when`(field.toDopeType(), expression)

@JvmName("whenStringFieldUnaliased")
fun CaseClass<StringType>.`when`(field: CMField<String>, expression: UnaliasedExpression<out ValidType>) =
    `when`(field.toDopeType(), expression)

@JvmName("whenBooleanFieldUnaliased")
fun CaseClass<BooleanType>.`when`(field: CMField<Boolean>, expression: UnaliasedExpression<out ValidType>) =
    `when`(field.toDopeType(), expression)

@JvmName("whenNumberListUnaliased")
fun CaseClass<ArrayType<NumberType>>.`when`(field: CMList<out Number>, expression: UnaliasedExpression<out ValidType>) =
    `when`(field.toDopeType(), expression)

@JvmName("whenStringListUnaliased")
fun CaseClass<ArrayType<StringType>>.`when`(field: CMList<String>, expression: UnaliasedExpression<out ValidType>) =
    `when`(field.toDopeType(), expression)

@JvmName("whenBooleanListUnaliased")
fun CaseClass<ArrayType<BooleanType>>.`when`(field: CMList<Boolean>, expression: UnaliasedExpression<out ValidType>) =
    `when`(field.toDopeType(), expression)

@JvmName("whenNumberFieldCMType")
fun CaseClass<NumberType>.`when`(field: CMField<out Number>, expression: CMType) =
    `when`(field.toDopeType(), expression.toDopeType())

@JvmName("whenStringFieldCMType")
fun CaseClass<StringType>.`when`(field: CMField<String>, expression: CMType) =
    `when`(field.toDopeType(), expression.toDopeType())

@JvmName("whenBooleanFieldCMType")
fun CaseClass<BooleanType>.`when`(field: CMField<Boolean>, expression: CMType) =
    `when`(field.toDopeType(), expression.toDopeType())

@JvmName("whenNumberListCMType")
fun CaseClass<ArrayType<NumberType>>.`when`(field: CMList<out Number>, expression: CMType) =
    `when`(field.toDopeType(), expression.toDopeType())

@JvmName("whenStringListCMType")
fun CaseClass<ArrayType<StringType>>.`when`(field: CMList<String>, expression: CMType) =
    `when`(field.toDopeType(), expression.toDopeType())

@JvmName("whenBooleanListCMType")
fun CaseClass<ArrayType<BooleanType>>.`when`(field: CMList<Boolean>, expression: CMType) =
    `when`(field.toDopeType(), expression.toDopeType())

@JvmName("whenSimpleNumberFieldUnaliased")
fun SimpleCaseExpression<NumberType>.`when`(whenExpression: CMField<out Number>, type: UnaliasedExpression<out ValidType>) =
    `when`(whenExpression.toDopeType(), type)

@JvmName("whenSimpleStringFieldUnaliased")
fun SimpleCaseExpression<StringType>.`when`(whenExpression: CMField<String>, type: UnaliasedExpression<out ValidType>) =
    `when`(whenExpression.toDopeType(), type)

@JvmName("whenSimpleBooleanFieldUnaliased")
fun SimpleCaseExpression<BooleanType>.`when`(whenExpression: CMField<Boolean>, type: UnaliasedExpression<out ValidType>) =
    `when`(whenExpression.toDopeType(), type)

@JvmName("whenSimpleNumberListUnaliased")
fun SimpleCaseExpression<ArrayType<NumberType>>.`when`(whenExpression: CMList<out Number>, type: UnaliasedExpression<out ValidType>) =
    `when`(whenExpression.toDopeType(), type)

@JvmName("whenSimpleStringListUnaliased")
fun SimpleCaseExpression<ArrayType<StringType>>.`when`(whenExpression: CMList<String>, type: UnaliasedExpression<out ValidType>) =
    `when`(whenExpression.toDopeType(), type)

@JvmName("whenSimpleBooleanListUnaliased")
fun SimpleCaseExpression<ArrayType<BooleanType>>.`when`(whenExpression: CMList<Boolean>, type: UnaliasedExpression<out ValidType>) =
    `when`(whenExpression.toDopeType(), type)

@JvmName("whenSimpleNumberFieldCMType")
fun SimpleCaseExpression<NumberType>.`when`(whenExpression: CMField<out Number>, type: CMType) =
    `when`(whenExpression.toDopeType(), type.toDopeType())

@JvmName("whenSimpleStringFieldCMType")
fun SimpleCaseExpression<StringType>.`when`(whenExpression: CMField<String>, type: CMType) =
    `when`(whenExpression.toDopeType(), type.toDopeType())

@JvmName("whenSimpleBooleanFieldCMType")
fun SimpleCaseExpression<BooleanType>.`when`(whenExpression: CMField<Boolean>, type: CMType) =
    `when`(whenExpression.toDopeType(), type.toDopeType())

@JvmName("whenSimpleNumberListCMType")
fun SimpleCaseExpression<ArrayType<NumberType>>.`when`(whenExpression: CMList<out Number>, type: CMType) =
    `when`(whenExpression.toDopeType(), type.toDopeType())

@JvmName("whenSimpleStringListCMType")
fun SimpleCaseExpression<ArrayType<StringType>>.`when`(whenExpression: CMList<String>, type: CMType) =
    `when`(whenExpression.toDopeType(), type.toDopeType())

@JvmName("whenSimpleBooleanListCMType")
fun SimpleCaseExpression<ArrayType<BooleanType>>.`when`(whenExpression: CMList<Boolean>, type: CMType) =
    `when`(whenExpression.toDopeType(), type.toDopeType())

@JvmName("whenTypeExpressionCMType")
fun <T : ValidType> SimpleCaseExpression<T>.`when`(whenExpression: TypeExpression<T>, type: CMType) =
    `when`(whenExpression, type.toDopeType())

@JvmName("elseSimpleCMType")
fun <T : ValidType> SimpleCaseExpression<T>.`else`(elseCase: CMType) =
    `else`(elseCase.toDopeType())

@JvmName("whenCMFieldBooleanUnaliased")
fun `when`(whenCondition: CMField<Boolean>, thenExpression: UnaliasedExpression<out ValidType>) =
    `when`(whenCondition.toDopeType(), thenExpression)

@JvmName("whenTypeExpressionBooleanCMType")
fun `when`(whenCondition: TypeExpression<BooleanType>, thenExpression: CMType) =
    `when`(whenCondition, thenExpression.toDopeType())

@JvmName("whenCMFieldBooleanCMType")
fun `when`(whenCondition: CMField<Boolean>, thenExpression: CMType) =
    `when`(whenCondition.toDopeType(), thenExpression.toDopeType())

@JvmName("whenSearchedCMFieldBooleanUnaliased")
fun SearchedCaseExpression.`when`(whenCondition: CMField<Boolean>, thenExpression: UnaliasedExpression<out ValidType>) =
    `when`(whenCondition.toDopeType(), thenExpression)

@JvmName("whenSearchedTypeExpressionBooleanCMType")
fun SearchedCaseExpression.`when`(whenCondition: TypeExpression<BooleanType>, thenExpression: CMType) =
    `when`(whenCondition, thenExpression.toDopeType())

@JvmName("whenSearchedCMFieldBooleanCMType")
fun SearchedCaseExpression.`when`(whenCondition: CMField<Boolean>, thenExpression: CMType) =
    `when`(whenCondition.toDopeType(), thenExpression.toDopeType())

@JvmName("elseSearchedCMType")
fun SearchedCaseExpression.`else`(elseCase: CMType) =
    `else`(elseCase.toDopeType())
