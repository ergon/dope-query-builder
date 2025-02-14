package ch.ergon.dope.resolvable.expression

import ch.ergon.dope.resolvable.Selectable
import ch.ergon.dope.validtype.ValidType

interface Expression<T : ValidType> : Selectable
