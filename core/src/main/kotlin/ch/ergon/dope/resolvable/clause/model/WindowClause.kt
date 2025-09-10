package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.clause.ISelectGroupByClause
import ch.ergon.dope.resolvable.clause.ISelectWindowClause
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowDefinition
import ch.ergon.dope.validtype.ValidType

data class WindowClause<T : ValidType>(
    val windowDeclaration: WindowDeclaration,
    val windowDeclarations: List<WindowDeclaration> = emptyList(),
    val parentClause: ISelectGroupByClause<T>,
) : ISelectWindowClause<T>

data class WindowDeclaration(val reference: String, val windowDefinition: WindowDefinition? = null) : Resolvable

fun String.asWindowDeclaration(windowDefinition: WindowDefinition? = null) = WindowDeclaration(this, windowDefinition)
