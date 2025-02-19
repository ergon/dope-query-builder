package ch.ergon.dope.extension.expression.type.collection

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.collection.Iterator
import ch.ergon.dope.resolvable.expression.type.collection.filter
import ch.ergon.dope.resolvable.expression.type.collection.filterIndexed
import ch.ergon.dope.resolvable.expression.type.collection.map
import ch.ergon.dope.resolvable.expression.type.collection.mapIndexed
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
    indexName: String? = null,
    iteratorName: String? = null,
    transformation: (Iterator<NumberType>, Iterator<NumberType>) -> TypeExpression<T>,
) = toDopeType().mapIndexed(indexName, iteratorName, transformation)

@JvmName("mapCMJsonStringListIndexed")
fun <T : ValidType> CMJsonList<String>.mapIndexed(
    indexName: String? = null,
    iteratorName: String? = null,
    transformation: (Iterator<NumberType>, Iterator<StringType>) -> TypeExpression<T>,
) = toDopeType().mapIndexed(indexName, iteratorName, transformation)

@JvmName("mapCMJsonBooleanListIndexed")
fun <T : ValidType> CMJsonList<Boolean>.mapIndexed(
    indexName: String? = null,
    iteratorName: String? = null,
    transformation: (Iterator<NumberType>, Iterator<BooleanType>) -> TypeExpression<T>,
) = toDopeType().mapIndexed(indexName, iteratorName, transformation)

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
    indexName: String? = null,
    iteratorName: String? = null,
    condition: (Iterator<NumberType>, Iterator<NumberType>) -> TypeExpression<BooleanType>,
) = toDopeType().filterIndexed(indexName, iteratorName, condition)

@JvmName("filterCMJsonStringListIndexed")
fun CMJsonList<String>.filterIndexed(
    indexName: String? = null,
    iteratorName: String? = null,
    condition: (Iterator<NumberType>, Iterator<StringType>) -> TypeExpression<BooleanType>,
) = toDopeType().filterIndexed(indexName, iteratorName, condition)

@JvmName("filterCMJsonBooleanListIndexed")
fun CMJsonList<Boolean>.filterIndexed(
    indexName: String? = null,
    iteratorName: String? = null,
    condition: (Iterator<NumberType>, Iterator<BooleanType>) -> TypeExpression<BooleanType>,
) = toDopeType().filterIndexed(indexName, iteratorName, condition)
