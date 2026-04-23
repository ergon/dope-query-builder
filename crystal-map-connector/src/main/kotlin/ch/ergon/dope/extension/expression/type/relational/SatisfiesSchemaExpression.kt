package ch.ergon.dope.extension.expression.type.relational

import ch.ergon.dope.extension.expression.type.ObjectField
import ch.ergon.dope.extension.expression.type.ObjectList
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.collection.Iterator
import ch.ergon.dope.resolvable.expression.type.collection.any
import ch.ergon.dope.resolvable.expression.type.collection.every
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonList
import com.schwarz.crystalapi.schema.CMObjectList
import com.schwarz.crystalapi.schema.Schema

fun <S : Schema> ObjectList<S>.any(
    iteratorName: String? = null,
    predicate: (ObjectField<S>) -> TypeExpression<BooleanType>,
) = any(iteratorName) { iterator -> predicate(ObjectField(this.schema, iterator.variable)) }

fun <S : Schema> CMObjectList<S>.any(
    iteratorName: String? = null,
    predicate: (ObjectField<S>) -> TypeExpression<BooleanType>,
) = toDopeType().any(iteratorName) { iterator -> predicate(ObjectField(element, iterator.variable)) }

@JvmName("anyNumber")
fun CMJsonList<Number>.any(
    iteratorName: String? = null,
    predicate: (Iterator<NumberType>) -> TypeExpression<BooleanType>,
) = toDopeType().any(iteratorName, predicate)

@JvmName("anyString")
fun CMJsonList<String>.any(
    iteratorName: String? = null,
    predicate: (Iterator<StringType>) -> TypeExpression<BooleanType>,
) = toDopeType().any(iteratorName, predicate)

@JvmName("anyBoolean")
fun CMJsonList<Boolean>.any(
    iteratorName: String? = null,
    predicate: (Iterator<BooleanType>) -> TypeExpression<BooleanType>,
) = toDopeType().any(iteratorName, predicate)

fun <S : Schema> ObjectList<S>.every(
    iteratorName: String? = null,
    predicate: (ObjectField<S>) -> TypeExpression<BooleanType>,
) = every(iteratorName) { iterator -> predicate(ObjectField(this.schema, iterator.variable)) }

fun <S : Schema> CMObjectList<S>.every(
    iteratorName: String? = null,
    predicate: (ObjectField<S>) -> TypeExpression<BooleanType>,
) = toDopeType().every(iteratorName) { iterator -> predicate(ObjectField(element, iterator.variable)) }

@JvmName("everyNumber")
fun CMJsonList<Number>.every(
    iteratorName: String? = null,
    predicate: (Iterator<NumberType>) -> TypeExpression<BooleanType>,
) = toDopeType().every(iteratorName, predicate)

@JvmName("everyString")
fun CMJsonList<String>.every(
    iteratorName: String? = null,
    predicate: (Iterator<StringType>) -> TypeExpression<BooleanType>,
) = toDopeType().every(iteratorName, predicate)

@JvmName("everyBoolean")
fun CMJsonList<Boolean>.every(
    iteratorName: String? = null,
    predicate: (Iterator<BooleanType>) -> TypeExpression<BooleanType>,
) = toDopeType().every(iteratorName, predicate)
