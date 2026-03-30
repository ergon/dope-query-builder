package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

data class ArrayExceptExpression<T : ValidType>(
    val array: TypeExpression<ArrayType<T>>,
    val except: TypeExpression<ArrayType<T>>,
) : FunctionExpression<ArrayType<T>>(listOf(array, except))

fun <T : ValidType> TypeExpression<ArrayType<T>>.except(except: TypeExpression<ArrayType<T>>) =
    ArrayExceptExpression(this, except)

fun <T : ValidType> ISelectOffsetClause<T>.except(except: TypeExpression<ArrayType<T>>) =
    asExpression().except(except)

fun <T : ValidType> TypeExpression<ArrayType<T>>.except(except: ISelectOffsetClause<T>) =
    except(except.asExpression())

fun <T : ValidType> ISelectOffsetClause<T>.except(except: ISelectOffsetClause<T>) =
    asExpression().except(except.asExpression())
