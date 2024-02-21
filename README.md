# DOPE Query Builder

DOPE Query Builder is a transpiler that enables easily written queries in Kotlin for Couchbase and other databases. DOPE Query Builder is not yet
finished and is still a work in progress.

**Inclusion of the crystal-map project:**

```
includeBuild("../crystal-map") {
    dependencySubstitution {
        substitute(module("com.github.SchwarzIT.crystal-map:crystal-map-versioning-plugin")).using(project(":crystal-map-versioning-plugin"))
        substitute(module("com.github.SchwarzIT.crystal-map:crystal-map-processor")).using(project(":crystal-map-processor"))
        substitute(module("com.github.SchwarzIT.crystal-map:crystal-map-api")).using(project(":crystal-map-api"))
    }
}
```

## License

[![License](http://img.shields.io/:license-mit-blue.svg?style=flat-square)](http://badges.mit-license.org)
The project is available as open source under the terms of the [MIT License](./LICENSE).
