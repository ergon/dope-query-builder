package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.asArrayField
import ch.ergon.dope.asSchemaArray
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.DEFAULT_ITERATOR_VARIABLE
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.Iterator
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.IteratorManager
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.SatisfiesType
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.SatisfiesType.ANY
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.SatisfiesType.EVERY
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.any
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.every
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
    private val variable: String,
    private val list: DopeSchemaArray<S>,
    private val predicate: (SchemaIterator<S>) -> TypeExpression<BooleanType>,
) : TypeExpression<BooleanType> {
    override fun toDopeQuery(): DopeQuery {
        val iteratorVariable = if (variable == DEFAULT_ITERATOR_VARIABLE) variable + IteratorManager.count else variable
        val predicateDopeQuery = predicate(SchemaIterator(iteratorVariable, list.schema)).toDopeQuery()

        return DopeQuery(
            queryString = "$satisfiesType `$iteratorVariable` IN ${list.name} SATISFIES ${predicateDopeQuery.queryString} END",
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
    variable: String,
    list: DopeSchemaArray<S>,
    predicate: (SchemaIterator<S>) -> TypeExpression<BooleanType>,
) : SatisfiesSchemaExpression<S>(ANY, variable, list, predicate)

class EverySatisfiesSchemaExpression<S : Schema>(
    variable: String,
    list: DopeSchemaArray<S>,
    predicate: (SchemaIterator<S>) -> TypeExpression<BooleanType>,
) : SatisfiesSchemaExpression<S>(EVERY, variable, list, predicate)

fun <S : Schema> DopeSchemaArray<S>.any(
    variable: String = DEFAULT_ITERATOR_VARIABLE,
    predicate: (SchemaIterator<S>) -> TypeExpression<BooleanType>,
) = AnySatisfiesSchemaExpression(variable, this, predicate)

fun <S : Schema> CMObjectList<S>.any(
    variable: String = DEFAULT_ITERATOR_VARIABLE,
    predicate: (SchemaIterator<S>) -> TypeExpression<BooleanType>,
) = AnySatisfiesSchemaExpression(variable, asSchemaArray(), predicate)

@JvmName("anyNumber")
fun CMList<Number>.any(
    variable: String = DEFAULT_ITERATOR_VARIABLE,
    predicate: (Iterator<NumberType>) -> TypeExpression<BooleanType>,
) = asArrayField().any(variable, predicate)

@JvmName("anyString")
fun CMList<String>.any(
    variable: String = DEFAULT_ITERATOR_VARIABLE,
    predicate: (Iterator<StringType>) -> TypeExpression<BooleanType>,
) = asArrayField().any(variable, predicate)

@JvmName("anyBoolean")
fun CMList<Boolean>.any(
    variable: String = DEFAULT_ITERATOR_VARIABLE,
    predicate: (Iterator<BooleanType>) -> TypeExpression<BooleanType>,
) = asArrayField().any(variable, predicate)

fun <S : Schema> DopeSchemaArray<S>.every(
    variable: String = DEFAULT_ITERATOR_VARIABLE,
    predicate: (SchemaIterator<S>) -> TypeExpression<BooleanType>,
) = EverySatisfiesSchemaExpression(variable, this, predicate)

fun <S : Schema> CMObjectList<S>.every(
    variable: String = DEFAULT_ITERATOR_VARIABLE,
    predicate: (SchemaIterator<S>) -> TypeExpression<BooleanType>,
) = EverySatisfiesSchemaExpression(variable, asSchemaArray(), predicate)

@JvmName("everyNumber")
fun CMList<Number>.every(
    variable: String = DEFAULT_ITERATOR_VARIABLE,
    predicate: (Iterator<NumberType>) -> TypeExpression<BooleanType>,
) = asArrayField().every(variable, predicate)

@JvmName("everyString")
fun CMList<String>.every(
    variable: String = DEFAULT_ITERATOR_VARIABLE,
    predicate: (Iterator<StringType>) -> TypeExpression<BooleanType>,
) = asArrayField().every(variable, predicate)

@JvmName("everyBoolean")
fun CMList<Boolean>.every(
    variable: String = DEFAULT_ITERATOR_VARIABLE,
    predicate: (Iterator<BooleanType>) -> TypeExpression<BooleanType>,
) = asArrayField().every(variable, predicate)
