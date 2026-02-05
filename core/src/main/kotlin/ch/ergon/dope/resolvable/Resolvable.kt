package ch.ergon.dope.resolvable

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolver.QueryResolver

interface Resolvable {
    fun <T : DopeQuery> toDopeQuery(resolver: QueryResolver<T>): T = resolver.resolve(this)
}

interface Selectable : Resolvable

interface Fromable : Resolvable

interface Joinable : Resolvable

interface Nestable : Resolvable

interface Deletable : Resolvable

interface Updatable : Resolvable

interface Returnable : Resolvable
