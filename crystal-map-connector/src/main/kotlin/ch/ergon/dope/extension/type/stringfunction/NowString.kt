package ch.ergon.dope.extension.type.stringfunction

import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.NowStringExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.nowString
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

fun nowString(format: CMJsonField<String>): NowStringExpression = nowString(format.toDopeType())
