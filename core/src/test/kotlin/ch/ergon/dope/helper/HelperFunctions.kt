package ch.ergon.dope.helper

fun unifyString(unmodified: String) = unmodified.replace("\\s+".toRegex(), " ").trim()
