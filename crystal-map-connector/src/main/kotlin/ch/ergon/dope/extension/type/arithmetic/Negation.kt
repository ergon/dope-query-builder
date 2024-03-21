package ch.ergon.dope.extension.type.arithmetic

import ch.ergon.dope.asField
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.NegationExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.neg
import com.schwarz.crystalapi.schema.CMField

fun neg(number: CMField<Number>): NegationExpression = neg(number.asField())
