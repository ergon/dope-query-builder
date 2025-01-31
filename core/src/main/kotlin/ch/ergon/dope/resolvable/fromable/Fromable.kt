package ch.ergon.dope.resolvable.fromable

import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.validtype.ValidType

interface Selectable : Resolvable

interface RawSelectable<T : ValidType> : Selectable

interface Fromable : Resolvable

interface Joinable : Resolvable

interface Deletable : Resolvable

interface Updatable : Resolvable

interface Returnable : Resolvable
