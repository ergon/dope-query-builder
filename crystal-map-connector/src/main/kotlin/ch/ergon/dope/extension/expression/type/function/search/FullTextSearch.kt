package ch.ergon.dope.extension.expression.type.function.search

import ch.ergon.dope.resolvable.expression.type.function.search.fullTextSearch
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

fun fullTextSearch(field: CMJsonField<String>, stringSearchExpression: String, options: Map<String, Any>? = null) =
    fullTextSearch(field.toDopeType(), stringSearchExpression, options)

fun fullTextSearch(field: CMJsonField<String>, objectSearchExpression: Map<String, Any>, options: Map<String, Any>? = null) =
    fullTextSearch(field.toDopeType(), objectSearchExpression, options)
