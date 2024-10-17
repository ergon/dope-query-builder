package ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

class ArrayExceptExpression<T : ValidType>(array: TypeExpression<ArrayType<T>>, except: TypeExpression<ArrayType<T>>) :
    ArrayFunctionExpression<T>("ARRAY_EXCEPT", array, except)

fun <T : ValidType> arrayExcept(array: TypeExpression<ArrayType<T>>, except: TypeExpression<ArrayType<T>>) =
    ArrayExceptExpression(array, except)

fun <T : ValidType> arrayExcept(array: ISelectOffsetClause<T>, except: TypeExpression<ArrayType<T>>) =
    arrayExcept(array.asExpression(), except)

fun <T : ValidType> arrayExcept(array: TypeExpression<ArrayType<T>>, except: ISelectOffsetClause<T>) =
    arrayExcept(array, except.asExpression())

fun <T : ValidType> arrayExcept(array: ISelectOffsetClause<T>, except: ISelectOffsetClause<T>) =
    arrayExcept(array.asExpression(), except.asExpression())
