package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager

interface Clause : Resolvable {
    fun build(): DopeQuery {
        ParameterManager.resetCounter()
        return toQuery()
    }
}
