package ch.ergon.dope.resolvable.fromable

import ch.ergon.dope.resolvable.Resolvable

interface Fromable : Resolvable

interface Joinable : Resolvable

interface Deletable : Resolvable

interface Updatable : Resolvable

interface Returnable : Resolvable

interface SingleReturnable : Returnable
