package ch.ergon.dope.extension.aggregator

import ch.ergon.dope.asArrayField
import ch.ergon.dope.asField
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.MaxExpression
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.max
import ch.ergon.dope.validtype.ValidType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

@JvmName("maxNumber")
fun max(field: CMField<out Number>, quantifier: AggregateQuantifier? = null):
    MaxExpression<out ValidType> = max(field.asField(), quantifier)

@JvmName("maxString")
fun max(field: CMField<String>, quantifier: AggregateQuantifier? = null):
    MaxExpression<out ValidType> = max(field.asField(), quantifier)

@JvmName("maxBoolean")
fun max(field: CMField<Boolean>, quantifier: AggregateQuantifier? = null):
    MaxExpression<out ValidType> = max(field.asField(), quantifier)

@JvmName("maxNumber")
fun max(field: CMList<out Number>, quantifier: AggregateQuantifier? = null):
    MaxExpression<out ValidType> = max(field.asArrayField(), quantifier)

@JvmName("maxString")
fun max(field: CMList<String>, quantifier: AggregateQuantifier? = null):
    MaxExpression<out ValidType> = max(field.asArrayField(), quantifier)

@JvmName("maxBoolean")
fun max(field: CMList<Boolean>, quantifier: AggregateQuantifier? = null):
    MaxExpression<out ValidType> = max(field.asArrayField(), quantifier)
