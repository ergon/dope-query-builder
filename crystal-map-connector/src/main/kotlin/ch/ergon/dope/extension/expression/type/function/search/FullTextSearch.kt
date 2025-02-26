package ch.ergon.dope.extension.expression.type.function.search

import ch.ergon.dope.resolvable.expression.type.function.search.fullTextSearch
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

fun fullTextSearch(identifier: CMJsonField<String>, searchQuery: String, options: Map<String, Any>? = null) =
    fullTextSearch(identifier.toDopeType(), searchQuery, options)

fun fullTextSearch(identifier: CMJsonField<String>, searchQuery: Map<String, Any>, options: Map<String, Any>? = null) =
    fullTextSearch(identifier.toDopeType(), searchQuery, options)
