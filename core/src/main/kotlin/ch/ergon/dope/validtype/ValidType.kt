package ch.ergon.dope.validtype

sealed interface ValidType

interface ComparableType : ValidType

interface AtomType : ValidType

interface BooleanType : AtomType

interface NumberType : ComparableType, AtomType

interface StringType : ComparableType, AtomType

interface NullType : ValidType

interface MissingType : ValidType

interface ArrayType<T : ValidType> : ValidType

class DopeSchemaArray<T : Any>(val schema: T, val name: String) : ValidType
