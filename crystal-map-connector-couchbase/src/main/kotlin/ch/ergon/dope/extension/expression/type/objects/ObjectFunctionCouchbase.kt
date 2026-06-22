package ch.ergon.dope.extension.expression.type.objects

import ch.ergon.dope.couchbase.resolvable.expression.type.function.objects.getInnerPairs
import ch.ergon.dope.couchbase.resolvable.expression.type.function.objects.getNestedPairs
import ch.ergon.dope.couchbase.resolvable.expression.type.function.objects.getPaths
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.ObjectType
import com.schwarz.crystalapi.schema.CMObjectField
import com.schwarz.crystalapi.schema.Schema

fun CMObjectField<Schema>.innerPairs() = toDopeType().getInnerPairs()

fun CMObjectField<Schema>.pairsNested(options: TypeExpression<ObjectType>? = null) = toDopeType().getNestedPairs(options)

fun CMObjectField<Schema>.pairsNested(options: CMObjectField<Schema>) = toDopeType().getNestedPairs(options.toDopeType())

fun TypeExpression<ObjectType>.pairsNested(options: CMObjectField<Schema>) = getNestedPairs(options.toDopeType())

fun CMObjectField<Schema>.paths(options: TypeExpression<ObjectType>? = null) = toDopeType().getPaths(options)

fun CMObjectField<Schema>.paths(options: CMObjectField<Schema>) = toDopeType().getPaths(options.toDopeType())

fun TypeExpression<ObjectType>.paths(options: CMObjectField<Schema>) = getPaths(options.toDopeType())
