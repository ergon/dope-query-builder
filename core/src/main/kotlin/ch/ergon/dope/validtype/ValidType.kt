package ch.ergon.dope.validtype

sealed interface ValidType

interface ComparableType : ValidType

interface AtomType : ValidType

interface BooleanType : AtomType

interface NumberType : ComparableType, AtomType

interface StringType : ComparableType, AtomType

interface ArrayType<T : ValidType> : ValidType

interface ObjectType : ComparableType, AtomType

interface NullType : BooleanType, NumberType, StringType, ArrayType<ValidType>, ObjectType

interface MissingType : ValidType
