package ch.ergon.dope.validtype

sealed interface ValidType

interface ComparableType : ValidType

interface AtomType : ValidType

interface BooleanType : AtomType

interface NumberType : ComparableType, AtomType

interface StringType : ComparableType, AtomType

interface ArrayType<T : ValidType> : ValidType

interface ObjectType : ComparableType, AtomType

sealed interface SuperNullType<T : ValidType> : BooleanType, NumberType, StringType, ArrayType<T>, ObjectType

typealias NullType = SuperNullType<out ValidType>

interface MissingType : ValidType
