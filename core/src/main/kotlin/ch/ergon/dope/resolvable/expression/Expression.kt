package ch.ergon.dope.resolvable.expression

import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.validtype.ValidType

interface Expression : Resolvable

interface SingleExpression<T : ValidType> : Expression

interface UnaliasedExpression<T : ValidType> : SingleExpression<T>

interface TypeExpression<T : ValidType> : UnaliasedExpression<T>
