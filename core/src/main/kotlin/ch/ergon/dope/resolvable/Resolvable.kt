package ch.ergon.dope.resolvable

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager

interface Resolvable {
    fun <T : DopeQuery> toDopeQuery(manager: DopeQueryManager<T>): T = manager.resolver.resolve(manager, this)
}

interface Selectable : Resolvable

interface Fromable : Resolvable

interface Joinable : Resolvable

interface Nestable : Resolvable

interface Deletable : Resolvable

interface Updatable : Resolvable

interface Returnable : Resolvable
