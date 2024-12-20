package ch.ergon.dope.extension.type.collection

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.Iterator
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.filter
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.filterIndexed
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.map
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.mapIndexed
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType
import com.schwarz.crystalapi.schema.CMJsonList

@JvmName("mapCMJsonNumberList")
fun <T : ValidType> CMJsonList<out Number>.map(
    iteratorName: String? = null,
    transformation: (Iterator<NumberType>) -> TypeExpression<T>,
) = toDopeType().map(iteratorName, transformation)

@JvmName("mapCMJsonStringList")
fun <T : ValidType> CMJsonList<String>.map(
    iteratorName: String? = null,
    transformation: (Iterator<StringType>) -> TypeExpression<T>,
) = toDopeType().map(iteratorName, transformation)

@JvmName("mapCMJsonBooleanList")
fun <T : ValidType> CMJsonList<Boolean>.map(
    iteratorName: String? = null,
    transformation: (Iterator<BooleanType>) -> TypeExpression<T>,
) = toDopeType().map(iteratorName, transformation)

@JvmName("mapCMJsonNumberListIndexed")
fun <T : ValidType> CMJsonList<out Number>.mapIndexed(
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

@JvmName("filterCMJsonNumberList")
fun CMJsonList<out Number>.filter(
    iteratorName: String? = null,
    condition: (Iterator<NumberType>) -> TypeExpression<BooleanType>,
) = toDopeType().filter(iteratorName, condition)

@JvmName("filterCMJsonStringList")
fun CMJsonList<String>.filter(
    iteratorName: String? = null,
    condition: (Iterator<StringType>) -> TypeExpression<BooleanType>,
) = toDopeType().filter(iteratorName, condition)

@JvmName("filterCMJsonBooleanList")
fun CMJsonList<Boolean>.filter(
    iteratorName: String? = null,
    condition: (Iterator<BooleanType>) -> TypeExpression<BooleanType>,
) = toDopeType().filter(iteratorName, condition)

@JvmName("filterCMJsonNumberListIndexed")
fun CMJsonList<out Number>.filterIndexed(
    iteratorName: String? = null,
    indexName: String? = null,
    condition: (Iterator<NumberType>, Iterator<NumberType>) -> TypeExpression<BooleanType>,
) = toDopeType().filterIndexed(iteratorName, indexName, condition)

@JvmName("filterCMJsonStringListIndexed")
fun CMJsonList<String>.filterIndexed(
    iteratorName: String? = null,
    indexName: String? = null,
    condition: (Iterator<StringType>, Iterator<NumberType>) -> TypeExpression<BooleanType>,
) = toDopeType().filterIndexed(iteratorName, indexName, condition)

@JvmName("filterCMJsonBooleanListIndexed")
fun CMJsonList<Boolean>.filterIndexed(
    iteratorName: String? = null,
    indexName: String? = null,
    condition: (Iterator<BooleanType>, Iterator<NumberType>) -> TypeExpression<BooleanType>,
) = toDopeType().filterIndexed(iteratorName, indexName, condition)
