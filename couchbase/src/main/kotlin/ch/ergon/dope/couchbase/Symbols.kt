package ch.ergon.dope.couchbase

import ch.ergon.dope.resolvable.expression.operator.InfixOperator
import ch.ergon.dope.resolvable.expression.type.arithmetic.AdditionExpression
import ch.ergon.dope.resolvable.expression.type.arithmetic.DivisionExpression
import ch.ergon.dope.resolvable.expression.type.arithmetic.ModuloExpression
import ch.ergon.dope.resolvable.expression.type.arithmetic.MultiplicationExpression
import ch.ergon.dope.resolvable.expression.type.arithmetic.NumberInfixExpression
import ch.ergon.dope.resolvable.expression.type.arithmetic.SubtractionExpression
import ch.ergon.dope.resolvable.expression.type.collection.InExpression
import ch.ergon.dope.resolvable.expression.type.collection.NotInExpression
import ch.ergon.dope.resolvable.expression.type.collection.NotWithinExpression
import ch.ergon.dope.resolvable.expression.type.collection.WithinExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayAppendExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayConcatExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayDistinctExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayExceptExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayFlattenExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayFunctionExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayInsertExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayIntersectExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayMoveExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayPutExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayRemoveExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayReplaceExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayReverseExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArraySortExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArraySymmetricDifference1Expression
import ch.ergon.dope.resolvable.expression.type.function.array.ArraySymmetricDifferenceExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArraySymmetricDifferenceNExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayUnionExpression
import ch.ergon.dope.resolvable.expression.type.function.comparison.GreatestExpression
import ch.ergon.dope.resolvable.expression.type.function.comparison.LeastExpression
import ch.ergon.dope.resolvable.expression.type.function.conditional.CoalesceExpression
import ch.ergon.dope.resolvable.expression.type.function.conditional.IfMissingExpression
import ch.ergon.dope.resolvable.expression.type.function.conditional.IfMissingOrNullExpression
import ch.ergon.dope.resolvable.expression.type.function.conditional.IfNullExpression
import ch.ergon.dope.resolvable.expression.type.function.conditional.NvlExpression
import ch.ergon.dope.resolvable.expression.type.function.date.ClockLocalExpression
import ch.ergon.dope.resolvable.expression.type.function.date.ClockMillisExpression
import ch.ergon.dope.resolvable.expression.type.function.date.ClockStringExpression
import ch.ergon.dope.resolvable.expression.type.function.date.ClockTimezoneExpression
import ch.ergon.dope.resolvable.expression.type.function.date.ClockUtcExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DateAddMillisExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DateAddStrExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DateDiffMillisExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DateDiffStrExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DateFormatStrExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DatePartMillisExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DatePartStrExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DateRangeMillisExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DateRangeStrExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DateTruncMillisExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DateTruncStrExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DurationToStringExpression
import ch.ergon.dope.resolvable.expression.type.function.date.MillisExpression
import ch.ergon.dope.resolvable.expression.type.function.date.MillisToStringExpression
import ch.ergon.dope.resolvable.expression.type.function.date.MillisToTimezoneExpression
import ch.ergon.dope.resolvable.expression.type.function.date.MillisToUtcExpression
import ch.ergon.dope.resolvable.expression.type.function.date.NowLocalExpression
import ch.ergon.dope.resolvable.expression.type.function.date.NowMillisExpression
import ch.ergon.dope.resolvable.expression.type.function.date.NowStringExpression
import ch.ergon.dope.resolvable.expression.type.function.date.NowTimezoneExpression
import ch.ergon.dope.resolvable.expression.type.function.date.NowUtcExpression
import ch.ergon.dope.resolvable.expression.type.function.date.StrToTimezoneExpression
import ch.ergon.dope.resolvable.expression.type.function.date.StrToUtcExpression
import ch.ergon.dope.resolvable.expression.type.function.date.StringToDurationExpression
import ch.ergon.dope.resolvable.expression.type.function.date.StringToMillisExpression
import ch.ergon.dope.resolvable.expression.type.function.date.WeekDayMillisExpression
import ch.ergon.dope.resolvable.expression.type.function.date.WeekDayStrExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.AbsoluteExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.ArcCosineExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.ArcSineExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.ArcTangent2Expression
import ch.ergon.dope.resolvable.expression.type.function.numeric.ArcTangentExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.CeilingExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.CosineExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.DegreesExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.EulerExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.ExponentExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.FloorExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.LogExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.LogNaturalisExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.NumberFunctionExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.PiExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.PowerExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.RadiansExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.RandomExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.RoundExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.SignExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.SineExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.SquareRootExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.TangentExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.TruncationExpression
import ch.ergon.dope.resolvable.expression.type.function.objects.ObjectAddExpression
import ch.ergon.dope.resolvable.expression.type.function.objects.ObjectConcatExpression
import ch.ergon.dope.resolvable.expression.type.function.objects.ObjectFieldExpression
import ch.ergon.dope.resolvable.expression.type.function.objects.ObjectInnerPairsExpression
import ch.ergon.dope.resolvable.expression.type.function.objects.ObjectInnerValuesExpression
import ch.ergon.dope.resolvable.expression.type.function.objects.ObjectLengthExpression
import ch.ergon.dope.resolvable.expression.type.function.objects.ObjectNamesExpression
import ch.ergon.dope.resolvable.expression.type.function.objects.ObjectPairsExpression
import ch.ergon.dope.resolvable.expression.type.function.objects.ObjectPairsNestedExpression
import ch.ergon.dope.resolvable.expression.type.function.objects.ObjectPathsExpression
import ch.ergon.dope.resolvable.expression.type.function.objects.ObjectPutExpression
import ch.ergon.dope.resolvable.expression.type.function.objects.ObjectRemoveExpression
import ch.ergon.dope.resolvable.expression.type.function.objects.ObjectRenameExpression
import ch.ergon.dope.resolvable.expression.type.function.objects.ObjectReplaceExpression
import ch.ergon.dope.resolvable.expression.type.function.objects.ObjectUnwrapExpression
import ch.ergon.dope.resolvable.expression.type.function.objects.ObjectValuesExpression
import ch.ergon.dope.resolvable.expression.type.function.string.Concat2Expression
import ch.ergon.dope.resolvable.expression.type.function.string.ConcatExpression
import ch.ergon.dope.resolvable.expression.type.function.string.ContainsExpression
import ch.ergon.dope.resolvable.expression.type.function.string.InitCapExpression
import ch.ergon.dope.resolvable.expression.type.function.string.LengthExpression
import ch.ergon.dope.resolvable.expression.type.function.string.LowerExpression
import ch.ergon.dope.resolvable.expression.type.function.string.LpadExpression
import ch.ergon.dope.resolvable.expression.type.function.string.LtrimExpression
import ch.ergon.dope.resolvable.expression.type.function.string.MBLengthExpression
import ch.ergon.dope.resolvable.expression.type.function.string.MBLpadExpression
import ch.ergon.dope.resolvable.expression.type.function.string.MBPosition1Expression
import ch.ergon.dope.resolvable.expression.type.function.string.MBPositionExpression
import ch.ergon.dope.resolvable.expression.type.function.string.MBRpadExpression
import ch.ergon.dope.resolvable.expression.type.function.string.MBSubstring1Expression
import ch.ergon.dope.resolvable.expression.type.function.string.MBSubstringExpression
import ch.ergon.dope.resolvable.expression.type.function.string.Position1Expression
import ch.ergon.dope.resolvable.expression.type.function.string.PositionExpression
import ch.ergon.dope.resolvable.expression.type.function.string.RepeatExpression
import ch.ergon.dope.resolvable.expression.type.function.string.ReplaceExpression
import ch.ergon.dope.resolvable.expression.type.function.string.ReverseExpression
import ch.ergon.dope.resolvable.expression.type.function.string.RpadExpression
import ch.ergon.dope.resolvable.expression.type.function.string.RtrimExpression
import ch.ergon.dope.resolvable.expression.type.function.string.SplitExpression
import ch.ergon.dope.resolvable.expression.type.function.string.Substring1Expression
import ch.ergon.dope.resolvable.expression.type.function.string.SubstringExpression
import ch.ergon.dope.resolvable.expression.type.function.string.SuffixesExpression
import ch.ergon.dope.resolvable.expression.type.function.string.TitleExpression
import ch.ergon.dope.resolvable.expression.type.function.string.TrimExpression
import ch.ergon.dope.resolvable.expression.type.function.string.UpperExpression
import ch.ergon.dope.resolvable.expression.type.function.string.UrlDecodeExpression
import ch.ergon.dope.resolvable.expression.type.function.string.UrlEncodeExpression
import ch.ergon.dope.resolvable.expression.type.function.type.IsArrayExpression
import ch.ergon.dope.resolvable.expression.type.function.type.IsAtomExpression
import ch.ergon.dope.resolvable.expression.type.function.type.IsBooleanExpression
import ch.ergon.dope.resolvable.expression.type.function.type.IsNumberExpression
import ch.ergon.dope.resolvable.expression.type.function.type.IsObjectExpression
import ch.ergon.dope.resolvable.expression.type.function.type.IsStringExpression
import ch.ergon.dope.resolvable.expression.type.function.type.ToArrayExpression
import ch.ergon.dope.resolvable.expression.type.function.type.ToBooleanExpression
import ch.ergon.dope.resolvable.expression.type.function.type.ToObjectExpression
import ch.ergon.dope.resolvable.expression.type.function.type.ToStringExpression
import ch.ergon.dope.resolvable.expression.type.function.type.TypeOfExpression
import ch.ergon.dope.resolvable.expression.type.logic.AndExpression
import ch.ergon.dope.resolvable.expression.type.logic.LogicalInfixExpression
import ch.ergon.dope.resolvable.expression.type.logic.OrExpression
import ch.ergon.dope.resolvable.expression.type.relational.EqualsExpression
import ch.ergon.dope.resolvable.expression.type.relational.GreaterOrEqualThanExpression
import ch.ergon.dope.resolvable.expression.type.relational.GreaterThanExpression
import ch.ergon.dope.resolvable.expression.type.relational.LessOrEqualThanExpression
import ch.ergon.dope.resolvable.expression.type.relational.LessThanExpression
import ch.ergon.dope.resolvable.expression.type.relational.LikeExpression
import ch.ergon.dope.resolvable.expression.type.relational.NotEqualsExpression
import ch.ergon.dope.resolvable.expression.type.relational.NotLikeExpression
import ch.ergon.dope.validtype.ValidType

val FunctionExpression<*>.symbol: String
    get() = when (this) {
        // Comparison
        is GreatestExpression<*> -> "GREATEST"
        is LeastExpression<*> -> "LEAST"

        // Conditional
        is IfMissingExpression<*> -> "IFMISSING"
        is IfMissingOrNullExpression<*> -> "IFMISSINGORNULL"
        is CoalesceExpression -> "COALESCE"
        is IfNullExpression<*> -> "IFNULL"
        is NvlExpression<*> -> "NVL"

        // Date/time
        is ClockMillisExpression -> "CLOCK_MILLIS"
        is ClockLocalExpression -> "CLOCK_LOCAL"
        is ClockStringExpression -> "CLOCK_STR"
        is ClockTimezoneExpression -> "CLOCK_TZ"
        is ClockUtcExpression -> "CLOCK_UTC"
        is DateAddMillisExpression -> "DATE_ADD_MILLIS"
        is DateAddStrExpression -> "DATE_ADD_STR"
        is DateDiffMillisExpression -> "DATE_DIFF_MILLIS"
        is DateDiffStrExpression -> "DATE_DIFF_STR"
        is DateFormatStrExpression -> "DATE_FORMAT_STR"
        is DatePartMillisExpression -> "DATE_PART_MILLIS"
        is DatePartStrExpression -> "DATE_PART_STR"
        is DateRangeMillisExpression -> "DATE_RANGE_MILLIS"
        is DateRangeStrExpression -> "DATE_RANGE_STR"
        is DateTruncMillisExpression -> "DATE_TRUNC_MILLIS"
        is DateTruncStrExpression -> "DATE_TRUNC_STR"
        is DurationToStringExpression -> "DURATION_TO_STR"
        is MillisExpression -> "MILLIS"
        is MillisToStringExpression -> "MILLIS_TO_STR"
        is MillisToTimezoneExpression -> "MILLIS_TO_TZ"
        is StrToTimezoneExpression -> "STR_TO_TZ"
        is MillisToUtcExpression -> "MILLIS_TO_UTC"
        is StrToUtcExpression -> "STR_TO_UTC"
        is NowMillisExpression -> "NOW_MILLIS"
        is NowLocalExpression -> "NOW_LOCAL"
        is NowStringExpression -> "NOW_STR"
        is NowTimezoneExpression -> "NOW_TZ"
        is NowUtcExpression -> "NOW_UTC"
        is StringToDurationExpression -> "STR_TO_DURATION"
        is StringToMillisExpression -> "STR_TO_MILLIS"
        is WeekDayMillisExpression -> "WEEKDAY_MILLIS"
        is WeekDayStrExpression -> "WEEKDAY_STR"

        // Object
        is ObjectAddExpression -> "OBJECT_ADD"
        is ObjectConcatExpression -> "OBJECT_CONCAT"
        is ObjectFieldExpression -> "OBJECT_FIELD"
        is ObjectInnerPairsExpression -> "OBJECT_INNER_PAIRS"
        is ObjectInnerValuesExpression -> "OBJECT_INNER_VALUES"
        is ObjectLengthExpression -> "OBJECT_LENGTH"
        is ObjectNamesExpression -> "OBJECT_NAMES"
        is ObjectPairsExpression -> "OBJECT_PAIRS"
        is ObjectPairsNestedExpression -> "OBJECT_PAIRS_NESTED"
        is ObjectPathsExpression -> "OBJECT_PATHS"
        is ObjectPutExpression -> "OBJECT_PUT"
        is ObjectRemoveExpression -> "OBJECT_REMOVE"
        is ObjectRenameExpression -> "OBJECT_RENAME"
        is ObjectReplaceExpression -> "OBJECT_REPLACE"
        is ObjectUnwrapExpression -> "OBJECT_UNWRAP"
        is ObjectValuesExpression -> "OBJECT_VALUES"

        // Type
        is TypeOfExpression<*> -> "TYPE"
        is ToBooleanExpression<*> -> "TOBOOLEAN"
        is ToArrayExpression<*> -> "TOARRAY"
        is ToObjectExpression<*> -> "TOOBJECT"
        is ToStringExpression<*> -> "TOSTRING"
        is IsArrayExpression<*> -> "ISARRAY"
        is IsAtomExpression<*> -> "ISATOM"
        is IsBooleanExpression<*> -> "ISBOOLEAN"
        is IsNumberExpression<*> -> "ISNUMBER"
        is IsObjectExpression<*> -> "ISOBJECT"
        is IsStringExpression<*> -> "ISSTRING"

        // String
        is ConcatExpression<*> -> "CONCAT"
        is Concat2Expression<*> -> "CONCAT2"
        is ContainsExpression -> "CONTAINS"
        is InitCapExpression -> "INITCAP"
        is TitleExpression -> "TITLE"
        is LengthExpression -> "LENGTH"
        is LowerExpression -> "LOWER"
        is LpadExpression -> "LPAD"
        is LtrimExpression -> "LTRIM"
        is MBLengthExpression -> "MB_LENGTH"
        is MBLpadExpression -> "MB_LPAD"
        is MBPositionExpression -> "MB_POSITION"
        is MBPosition1Expression -> "MB_POSITION1"
        is MBRpadExpression -> "MB_RPAD"
        is MBSubstringExpression -> "MB_SUBSTR"
        is MBSubstring1Expression -> "MB_SUBSTR1"
        is PositionExpression -> "POSITION"
        is Position1Expression -> "POSITION1"
        is RepeatExpression -> "REPEAT"
        is ReplaceExpression -> "REPLACE"
        is ReverseExpression -> "REVERSE"
        is RpadExpression -> "RPAD"
        is RtrimExpression -> "RTRIM"
        is SplitExpression -> "SPLIT"
        is SubstringExpression -> "SUBSTR"
        is Substring1Expression -> "SUBSTR1"
        is SuffixesExpression -> "SUFFIXES"
        is TrimExpression -> "TRIM"
        is UpperExpression -> "UPPER"
        is UrlDecodeExpression -> "URL_DECODE"
        is UrlEncodeExpression -> "URL_ENCODE"

        else -> throw IllegalArgumentException("Unsupported function expression: ${this::class.simpleName}")
    }

val NumberFunctionExpression.symbol: String
    get() = when (this) {
        is AbsoluteExpression -> "ABS"
        is ArcCosineExpression -> "ACOS"
        is ArcSineExpression -> "ASIN"
        is ArcTangentExpression -> "ATAN"
        is ArcTangent2Expression -> "ATAN2"
        is CeilingExpression -> "CEIL"
        is CosineExpression -> "COS"
        is DegreesExpression -> "DEGREES"
        is EulerExpression -> "E"
        is ExponentExpression -> "EXP"
        is FloorExpression -> "FLOOR"
        is LogNaturalisExpression -> "LN"
        is LogExpression -> "LOG"
        is PiExpression -> "PI"
        is PowerExpression -> "POWER"
        is RadiansExpression -> "RADIANS"
        is RandomExpression -> "RANDOM"
        is RoundExpression -> "ROUND"
        is SignExpression -> "SIGN"
        is SineExpression -> "SIN"
        is SquareRootExpression -> "SQRT"
        is TangentExpression -> "TAN"
        is TruncationExpression -> "TRUNC"
    }

val <T : ValidType> ArrayFunctionExpression<T>.symbol: String
    get() = when (this) {
        is ArrayAppendExpression -> "ARRAY_APPEND"
        is ArrayConcatExpression -> "ARRAY_CONCAT"
        is ArrayDistinctExpression -> "ARRAY_DISTINCT"
        is ArrayExceptExpression -> "ARRAY_EXCEPT"
        is ArrayFlattenExpression -> "ARRAY_FLATTEN"
        is ArrayInsertExpression -> "ARRAY_INSERT"
        is ArrayIntersectExpression -> "ARRAY_INTERSECT"
        is ArrayMoveExpression -> "ARRAY_MOVE"
        is ArrayPutExpression -> "ARRAY_PUT"
        is ArrayRemoveExpression -> "ARRAY_REMOVE"
        is ArrayReplaceExpression -> "ARRAY_REPLACE"
        is ArrayReverseExpression -> "ARRAY_REVERSE"
        is ArraySortExpression -> "ARRAY_SORT"
        is ArraySymmetricDifferenceExpression -> "ARRAY_SYMDIFF"
        is ArraySymmetricDifference1Expression -> "ARRAY_SYMDIFF1"
        is ArraySymmetricDifferenceNExpression -> "ARRAY_SYMDIFFN"
        is ArrayUnionExpression -> "ARRAY_UNION"
    }

val NumberInfixExpression.symbol: String
    get() = when (this) {
        is AdditionExpression -> "+"
        is SubtractionExpression -> "-"
        is MultiplicationExpression -> "*"
        is DivisionExpression -> "/"
        is ModuloExpression -> "%"
    }

val LogicalInfixExpression.symbol: String
    get() = when (this) {
        is AndExpression -> "AND"
        is OrExpression -> "OR"
    }

val InfixOperator.symbol: String
    get() = when (this) {
        is EqualsExpression<*> -> "="
        is NotEqualsExpression<*> -> "!="
        is LessThanExpression<*> -> "<"
        is GreaterThanExpression<*> -> ">"
        is LessOrEqualThanExpression<*> -> "<="
        is GreaterOrEqualThanExpression<*> -> ">="
        is LikeExpression -> "LIKE"
        is NotLikeExpression -> "NOT LIKE"
        is InExpression<*> -> "IN"
        is NotInExpression<*> -> "NOT IN"
        is WithinExpression<*> -> "WITHIN"
        is NotWithinExpression<*> -> "NOT WITHIN"
        else -> throw IllegalArgumentException("Unsupported infix operator: ${this::class.simpleName}")
    }

val Enum<*>.queryString: String
    get() = name.replace('_', ' ')
