package ch.ergon.dope.extension.expression.aggregate

import ch.ergon.dope.resolvable.expression.rowscope.aggregate.AggregateQuantifier
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.count
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList

@JvmName("countNumber")
fun count(field: CMJsonField<out Number>, quantifier: AggregateQuantifier? = null) =
    count(field.toDopeType(), quantifier)

@JvmName("countString")
fun count(field: CMJsonField<String>, quantifier: AggregateQuantifier? = null) =
    count(field.toDopeType(), quantifier)

@JvmName("countBoolean")
fun count(field: CMJsonField<Boolean>, quantifier: AggregateQuantifier? = null) =
    count(field.toDopeType(), quantifier)

@JvmName("countNumber")
fun count(field: CMJsonList<out Number>, quantifier: AggregateQuantifier? = null) =
    count(field.toDopeType(), quantifier)

@JvmName("countString")
fun count(field: CMJsonList<String>, quantifier: AggregateQuantifier? = null) =
    count(field.toDopeType(), quantifier)

@JvmName("countBoolean")
fun count(field: CMJsonList<Boolean>, quantifier: AggregateQuantifier? = null) =
    count(field.toDopeType(), quantifier)
