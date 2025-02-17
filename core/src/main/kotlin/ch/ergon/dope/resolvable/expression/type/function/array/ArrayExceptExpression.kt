package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

class ArrayExceptExpression<T : ValidType>(array: TypeExpression<ArrayType<T>>, except: TypeExpression<ArrayType<T>>) :
    ArrayFunctionExpression<T>("ARRAY_EXCEPT", array, except)

fun <T : ValidType> arrayExcept(array: TypeExpression<ArrayType<T>>, except: TypeExpression<ArrayType<T>>) =
    ArrayExceptExpression(array, except)

fun <T : ValidType> arrayExcept(selectClause: ISelectOffsetClause<T>, except: TypeExpression<ArrayType<T>>) =
    arrayExcept(selectClause.asExpression(), except)

fun <T : ValidType> arrayExcept(array: TypeExpression<ArrayType<T>>, except: ISelectOffsetClause<T>) =
    arrayExcept(array, except.asExpression())

fun <T : ValidType> arrayExcept(selectClause: ISelectOffsetClause<T>, except: ISelectOffsetClause<T>) =
    arrayExcept(selectClause.asExpression(), except.asExpression())
