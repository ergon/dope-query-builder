# DOPE Query Builder

## Introduction

DOPE Query Builder is a powerful, type-safe query builder designed specifically for Kotlin. It enables developers to construct N1QL and SQL++
queries safely and efficiently, targeting Couchbase and other compatible databases. DOPE Query Builder is not yet finished and is still a *work
in progress*.

## Features

* **Type Safety:** Reduce runtime errors with compile-time checks, ensuring that your queries are correct before execution.
* **Kotlin-Optimized:** Specifically designed for Kotlin, offering idiomatic constructs and seamless integration with Kotlin projects.
* **Flexible Query Construction:** Build complex N1QL and SQL++ queries using a fluent and intuitive API.
* **Easy Integration:** Available through JitPack for easy integration into your existing projects.

## License

[![License](http://img.shields.io/:license-mit-blue.svg?style=flat-square)](http://badges.mit-license.org)
The project is available as open source under the terms of the [MIT License](./LICENSE).

## Implementation

### [**Setup**](https://github.com/ergon/dope-query-builder/wiki/%5B0%5D-Setup-Guide)

### [**Core**](https://github.com/ergon/dope-query-builder/wiki/%5B1%5D-Core)

### Backends via Resolvers

- Core exposes a generic `build` API that takes a backend-specific resolver to turn the DSL into an executable backend result.
- The previous no-argument `build()` is removed; always pass a resolver.

- Couchbase usage (returns `DopeQuery`):

```
import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.build.build
import ch.ergon.dope.couchbase.CouchbaseResolver

val n1ql: ch.ergon.dope.DopeQuery = QueryBuilder
    .selectAsterisk()
    .from(/* ... */)
    .where(/* ... */)
    .build(CouchbaseResolver())
```

- Mongo usage (returns `String`):

```
import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.build.build
import ch.ergon.dope.mongo.MongoResolver

val mongoQuery: String = QueryBuilder
    .selectAsterisk()
    .from(/* ... */)
    .where(/* ... */)
    .build(MongoResolver())
```

Note: Mongo currently returns a placeholder string. The resolver will translate the same DSL into MongoDB query strings incrementally.

### [**Crystal-Map-Connector**](https://github.com/ergon/dope-query-builder/wiki/%5B2%5D-Crystal%E2%80%90Map%E2%80%90Connector)

### [**DOPE functionality**](https://github.com/ergon/dope-query-builder/wiki/%5B3%5D-DOPE-functionality)
