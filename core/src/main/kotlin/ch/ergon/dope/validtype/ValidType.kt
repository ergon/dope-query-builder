package ch.ergon.dope.validtype

sealed interface ValidType

interface ComparableType : ValidType

interface BooleanType : ValidType

interface NumberType : ComparableType

interface StringType : ComparableType

interface NullType : ValidType

interface MissingType : ValidType

interface ArrayType<T : ValidType> : ValidType

class DopeSchemaArray<T : Any>(val schema: T, val name: String) : ValidType
