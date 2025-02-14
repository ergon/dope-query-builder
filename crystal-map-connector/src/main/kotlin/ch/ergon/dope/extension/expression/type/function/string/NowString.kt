package ch.ergon.dope.extension.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.function.string.NowStringExpression
import ch.ergon.dope.resolvable.expression.type.function.string.nowString
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

fun nowString(format: CMJsonField<String>): NowStringExpression = nowString(format.toDopeType())
