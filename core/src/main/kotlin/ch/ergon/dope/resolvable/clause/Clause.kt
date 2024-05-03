package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.expression.unaliased.type.resetCounter

interface Clause : Resolvable {
    fun build(): String {
        resetCounter()
        return toQueryString()
    }
}
