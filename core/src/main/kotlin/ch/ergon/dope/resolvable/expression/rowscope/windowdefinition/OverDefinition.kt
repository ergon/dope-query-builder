package ch.ergon.dope.resolvable.expression.rowscope.windowdefinition

import ch.ergon.dope.resolvable.Resolvable

sealed interface OverDefinition : Resolvable

data class OverWindowDefinition(val windowDefinition: WindowDefinition) : OverDefinition

data class OverWindowReference(val windowReference: String) : OverDefinition
