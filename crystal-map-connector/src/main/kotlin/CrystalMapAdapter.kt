import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.expression.unaliased.type.Primitive
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.DocType

fun CMField<String>.toStringTypeField(): Field<StringType> = Field(this.name, this.path)

fun CMField<Number>.toNumberTypeField(): Field<NumberType> = Field(this.name, this.path)

fun CMField<Boolean>.toBooleanTypeField(): Field<BooleanType> = Field(this.name, this.path)

fun CMField<DocType>.toStringType(): Primitive<StringType> = Primitive(this.name)
