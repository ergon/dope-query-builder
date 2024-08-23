package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.Iterator
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.SatisfiesType
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.SatisfiesType.ANY
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.SatisfiesType.EVERY
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.any
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.every
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.DopeSchemaArray
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList
import com.schwarz.crystalapi.schema.CMObject
import com.schwarz.crystalapi.schema.CMObjectList
import com.schwarz.crystalapi.schema.CMType
import com.schwarz.crystalapi.schema.Schema

sealed class SatisfiesSchemaExpression<S : Schema>(
    private val satisfiesType: SatisfiesType,
    private val iteratorName: String? = null,
    private val arrayExpression: DopeSchemaArray<S>,
    private val predicate: (SchemaIterator<S>) -> TypeExpression<BooleanType>,
) : TypeExpression<BooleanType> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val iteratorVariable = iteratorName ?: manager.iteratorManager.getIteratorName()
        val predicateDopeQuery = predicate(SchemaIterator(iteratorVariable, arrayExpression.schema)).toDopeQuery(manager)
        return DopeQuery(
            queryString = "$satisfiesType `$iteratorVariable` IN ${arrayExpression.name} SATISFIES ${predicateDopeQuery.queryString} END",
            parameters = predicateDopeQuery.parameters,
        )
    }
}

class SchemaIterator<S : Schema>(val variable: String, val schema: S) {
    inline fun <reified A : CMType> field(getCMType: S.() -> A): A {
        val schemaAttribute = schema.getCMType()
        val newPath = if (schemaAttribute.path.isBlank()) variable else "${schemaAttribute.path}`.`$variable"
        return when (schemaAttribute) {
            is CMField<*> -> CMField<Any>(schemaAttribute.name, newPath) as A
            is CMList<*> -> CMList<Any>(schemaAttribute.name, newPath) as A
            is CMObject<*> -> CMObject(schemaAttribute.element, newPath) as A
            is CMObjectList<*> -> CMObjectList(schemaAttribute.element, schemaAttribute.name, newPath) as A
            else -> error("Unsupported CMType: $schemaAttribute")
        }
    }
}

class AnySatisfiesSchemaExpression<S : Schema>(
    iteratorName: String? = null,
    arrayExpression: DopeSchemaArray<S>,
    predicate: (SchemaIterator<S>) -> TypeExpression<BooleanType>,
) : SatisfiesSchemaExpression<S>(ANY, iteratorName, arrayExpression, predicate)

class EverySatisfiesSchemaExpression<S : Schema>(
    iteratorName: String? = null,
    arrayExpression: DopeSchemaArray<S>,
    predicate: (SchemaIterator<S>) -> TypeExpression<BooleanType>,
) : SatisfiesSchemaExpression<S>(EVERY, iteratorName, arrayExpression, predicate)

fun <S : Schema> DopeSchemaArray<S>.any(
    iteratorName: String? = null,
    predicate: (SchemaIterator<S>) -> TypeExpression<BooleanType>,
) = AnySatisfiesSchemaExpression(iteratorName, this, predicate)

fun <S : Schema> CMObjectList<S>.any(
    iteratorName: String? = null,
    predicate: (SchemaIterator<S>) -> TypeExpression<BooleanType>,
) = AnySatisfiesSchemaExpression(iteratorName, toDopeType(), predicate)

@JvmName("anyNumber")
fun CMList<Number>.any(
    iteratorName: String? = null,
    predicate: (Iterator<NumberType>) -> TypeExpression<BooleanType>,
) = toDopeType().any(iteratorName, predicate)

@JvmName("anyString")
fun CMList<String>.any(
    iteratorName: String? = null,
    predicate: (Iterator<StringType>) -> TypeExpression<BooleanType>,
) = toDopeType().any(iteratorName, predicate)

@JvmName("anyBoolean")
fun CMList<Boolean>.any(
    iteratorName: String? = null,
    predicate: (Iterator<BooleanType>) -> TypeExpression<BooleanType>,
) = toDopeType().any(iteratorName, predicate)

fun <S : Schema> DopeSchemaArray<S>.every(
    iteratorName: String? = null,
    predicate: (SchemaIterator<S>) -> TypeExpression<BooleanType>,
) = EverySatisfiesSchemaExpression(iteratorName, this, predicate)

fun <S : Schema> CMObjectList<S>.every(
    iteratorName: String? = null,
    predicate: (SchemaIterator<S>) -> TypeExpression<BooleanType>,
) = EverySatisfiesSchemaExpression(iteratorName, toDopeType(), predicate)

@JvmName("everyNumber")
fun CMList<Number>.every(
    iteratorName: String? = null,
    predicate: (Iterator<NumberType>) -> TypeExpression<BooleanType>,
) = toDopeType().every(iteratorName, predicate)

@JvmName("everyString")
fun CMList<String>.every(
    iteratorName: String? = null,
    predicate: (Iterator<StringType>) -> TypeExpression<BooleanType>,
) = toDopeType().every(iteratorName, predicate)

@JvmName("everyBoolean")
fun CMList<Boolean>.every(
    iteratorName: String? = null,
    predicate: (Iterator<BooleanType>) -> TypeExpression<BooleanType>,
) = toDopeType().every(iteratorName, predicate)
