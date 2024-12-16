package ch.ergon.dope.extension.type.collection

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.Iterator
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.filter
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.mapIndexed
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.mapIndexedUnnested
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType
import com.schwarz.crystalapi.schema.CMJsonList

@JvmName("mapCMJsonNumberListIndexed")
fun <T : ValidType> CMJsonList<Number>.mapIndexed(
    iteratorName: String? = null,
    indexName: String? = null,
    transformation: (Iterator<NumberType>, Iterator<NumberType>) -> TypeExpression<T>,
) = toDopeType().mapIndexed(iteratorName, indexName, transformation)

@JvmName("mapCMJsonStringListIndexed")
fun <T : ValidType> CMJsonList<String>.mapIndexed(
    iteratorName: String? = null,
    indexName: String? = null,
    transformation: (Iterator<StringType>, Iterator<NumberType>) -> TypeExpression<T>,
) = toDopeType().mapIndexed(iteratorName, indexName, transformation)

@JvmName("mapCMJsonBooleanListIndexed")
fun <T : ValidType> CMJsonList<Boolean>.mapIndexed(
    iteratorName: String? = null,
    indexName: String? = null,
    transformation: (Iterator<BooleanType>, Iterator<NumberType>) -> TypeExpression<T>,
) = toDopeType().mapIndexed(iteratorName, indexName, transformation)

@JvmName("mapCMJsonNumberListIndexedUnnested")
fun <T : ValidType> CMJsonList<Number>.mapIndexedUnnested(
    iteratorName: String? = null,
    indexName: String? = null,
    transformation: (Iterator<NumberType>, Iterator<NumberType>) -> TypeExpression<T>,
) = toDopeType().mapIndexedUnnested(iteratorName, indexName, transformation)

@JvmName("mapCMJsonStringListIndexedUnnested")
fun <T : ValidType> CMJsonList<String>.mapIndexedUnnested(
    iteratorName: String? = null,
    indexName: String? = null,
    transformation: (Iterator<StringType>, Iterator<NumberType>) -> TypeExpression<T>,
) = toDopeType().mapIndexedUnnested(iteratorName, indexName, transformation)

@JvmName("mapCMJsonBooleanListIndexedUnnested")
fun <T : ValidType> CMJsonList<Boolean>.mapIndexedUnnested(
    iteratorName: String? = null,
    indexName: String? = null,
    transformation: (Iterator<BooleanType>, Iterator<NumberType>) -> TypeExpression<T>,
) = toDopeType().mapIndexedUnnested(iteratorName, indexName, transformation)

@JvmName("filterCMJsonNumberList")
fun CMJsonList<out Number>.filter(
    condition: (Iterator<NumberType>, Iterator<NumberType>) -> TypeExpression<BooleanType>,
) = toDopeType().filter(condition)

@JvmName("filterCMJsonStringList")
fun CMJsonList<String>.filter(
    condition: (Iterator<StringType>, Iterator<NumberType>) -> TypeExpression<BooleanType>,
) = toDopeType().filter(condition)

@JvmName("filterCMJsonBooleanList")
fun CMJsonList<Boolean>.filter(
    condition: (Iterator<BooleanType>, Iterator<NumberType>) -> TypeExpression<BooleanType>,
) = toDopeType().filter(condition)
