package ch.ergon.dope

import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.IteratorManager

data class DopeQuery(val queryString: String, val parameters: Map<String, Any>)

class DopeQueryManager {
    val parameterManager = ParameterManager()
    val iteratorManager = IteratorManager()
}
