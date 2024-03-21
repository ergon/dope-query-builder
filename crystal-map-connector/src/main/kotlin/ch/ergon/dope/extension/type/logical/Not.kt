package ch.ergon.dope.extension.type.logical

import ch.ergon.dope.asField
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.NotExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.not
import com.schwarz.crystalapi.schema.CMField

fun not(boolean: CMField<Boolean>): NotExpression = not(boolean.asField())
