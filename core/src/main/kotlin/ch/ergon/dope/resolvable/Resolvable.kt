package ch.ergon.dope.resolvable

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager

interface Resolvable {
    fun toDopeQuery(manager: DopeQueryManager): DopeQuery
}

interface Selectable : Resolvable

interface Fromable : Resolvable

interface Joinable : Resolvable

interface Deletable : Resolvable

interface Updatable : Resolvable

interface Returnable : Resolvable
