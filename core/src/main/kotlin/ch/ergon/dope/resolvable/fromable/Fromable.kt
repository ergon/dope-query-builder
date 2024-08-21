package ch.ergon.dope.resolvable.fromable

import ch.ergon.dope.resolvable.Resolvable

interface Fromable : Resolvable

interface IBucket : Fromable

interface Updatable : IBucket
