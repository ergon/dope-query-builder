package ch.ergon.dope.extension.expression.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.expression.type.ObjectField
import ch.ergon.dope.extension.expression.type.ObjectList
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.collection.Iterator
import ch.ergon.dope.resolvable.expression.type.collection.SatisfiesType
import ch.ergon.dope.resolvable.expression.type.collection.SatisfiesType.ANY
import ch.ergon.dope.resolvable.expression.type.collection.SatisfiesType.EVERY
import ch.ergon.dope.resolvable.expression.type.collection.any
import ch.ergon.dope.resolvable.expression.type.collection.every
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonList
import com.schwarz.crystalapi.schema.CMObjectList
import com.schwarz.crystalapi.schema.Schema

sealed class SatisfiesSchemaExpression<S : Schema>(
    private val satisfiesType: SatisfiesType,
    private val iteratorName: String? = null,
    private val arrayExpression: ObjectList<S>,
    private val predicate: (ObjectField<S>) -> TypeExpression<BooleanType>,
) : TypeExpression<BooleanType> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val iteratorVariable = iteratorName ?: manager.iteratorManager.getIteratorName()
        val arrayExpressionDopeQuery = arrayExpression.toDopeQuery(manager)
        val predicateDopeQuery = predicate(ObjectField(arrayExpression.schema, iteratorVariable, "")).toDopeQuery(manager)
        return DopeQuery(
            queryString = "$satisfiesType `$iteratorVariable` IN ${arrayExpressionDopeQuery.queryString} " +
                "SATISFIES ${predicateDopeQuery.queryString} END",
            parameters = arrayExpressionDopeQuery.parameters.merge(predicateDopeQuery.parameters),
        )
    }
}

class AnySatisfiesSchemaExpression<S : Schema>(
    iteratorName: String? = null,
    arrayExpression: ObjectList<S>,
    predicate: (ObjectField<S>) -> TypeExpression<BooleanType>,
) : SatisfiesSchemaExpression<S>(ANY, iteratorName, arrayExpression, predicate)

class EverySatisfiesSchemaExpression<S : Schema>(
    iteratorName: String? = null,
    arrayExpression: ObjectList<S>,
    predicate: (ObjectField<S>) -> TypeExpression<BooleanType>,
) : SatisfiesSchemaExpression<S>(EVERY, iteratorName, arrayExpression, predicate)

fun <S : Schema> ObjectList<S>.any(
    iteratorName: String? = null,
    predicate: (ObjectField<S>) -> TypeExpression<BooleanType>,
) = AnySatisfiesSchemaExpression(iteratorName, this, predicate)

fun <S : Schema> CMObjectList<S>.any(
    iteratorName: String? = null,
    predicate: (ObjectField<S>) -> TypeExpression<BooleanType>,
) = AnySatisfiesSchemaExpression(iteratorName, toDopeType(), predicate)

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
) = EverySatisfiesSchemaExpression(iteratorName, this, predicate)

fun <S : Schema> CMObjectList<S>.every(
    iteratorName: String? = null,
    predicate: (ObjectField<S>) -> TypeExpression<BooleanType>,
) = EverySatisfiesSchemaExpression(iteratorName, toDopeType(), predicate)

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
