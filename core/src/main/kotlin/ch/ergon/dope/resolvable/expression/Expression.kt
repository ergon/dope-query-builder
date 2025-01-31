package ch.ergon.dope.resolvable.expression

import ch.ergon.dope.resolvable.fromable.RawSelectable
import ch.ergon.dope.resolvable.fromable.Returnable
import ch.ergon.dope.validtype.ValidType

interface SingleExpression<T : ValidType> : RawSelectable<T>, Returnable

interface TypeExpression<T : ValidType> : SingleExpression<T>
