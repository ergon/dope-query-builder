package ch.ergon.dope.validtype

interface ValidType

interface ComparableType : ValidType

interface BooleanType : ValidType

interface NumberType : ComparableType

interface StringType : ComparableType

interface NullType : ValidType

interface MissingType : ValidType

interface ArrayType : ValidType
