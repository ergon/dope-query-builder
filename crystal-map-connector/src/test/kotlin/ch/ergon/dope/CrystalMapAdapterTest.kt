package ch.ergon.dope

class CrystalMapAdapterTest {
// @Test
//    fun `should convert CMJsonField with Number`() {
//        val cmJsonField = CMJsonField<Number>("testName", "testPath")
//
//        val actual: IField<NumberType> = cmJsonField.toDopeType()
//
//        assertEquals("`${cmJsonField.path}`.`${cmJsonField.name}`", actual)
//    }
//
//    @Test
//    fun `should convert CMJsonField with String`() {
//        val cmJsonField = CMJsonField<String>("testName", "testPath")
//
//        val actual: IField<StringType> = cmJsonField.toDopeType()
//
//        assertEquals("`${cmJsonField.path}`.`${cmJsonField.name}`", actual)
//    }
//
//    @Test
//    fun `should convert CMJsonField with Boolean`() {
//        val cmJsonField = CMJsonField<Boolean>("testName", "testPath")
//
//        val actual: IField<BooleanType> = cmJsonField.toDopeType()
//
//        assertEquals("`${cmJsonField.path}`.`${cmJsonField.name}`", actual)
//    }
//
//    @Test
//    fun `should convert CMJsonList string`() {
//        val cmJsonList = CMJsonList<String>("testName", "testPath")
//
//        val actual: Field<ArrayType<StringType>> = cmJsonList.toDopeType()
//
//        assertEquals("`${cmJsonList.path}`.`${cmJsonList.name}`", actual)
//    }
//
//    @Test
//    fun `should convert CMJsonList number`() {
//        val cmJsonList = CMJsonList<Number>("testName", "testPath")
//
//        val actual: Field<ArrayType<NumberType>> = cmJsonList.toDopeType()
//
//        assertEquals("`${cmJsonList.path}`.`${cmJsonList.name}`", actual)
//    }
//
//    @Test
//    fun `should convert CMJsonList boolean`() {
//        val cmJsonList = CMJsonList<Boolean>("testName", "testPath")
//
//        val actual: Field<ArrayType<BooleanType>> = cmJsonList.toDopeType()
//
//        assertEquals("`${cmJsonList.path}`.`${cmJsonList.name}`", actual)
//    }
//
//    @Test
//    fun `should throw exception when resolving corrupt field`() {
//        val string = someString()
//        val corruptField = someCorruptField()
//
//        assertThrows<IllegalArgumentException> {
//            string.toDopeType(corruptField)
//        }
//    }
//
//    @Test
//    fun `should support parameter with number converter`() {
//        val cmField = someCMConverterNumberField()
//        val value = Date(1737021572000)
//        val expected = DopeQuery(
//            queryString = "\$testParameter",
//            parameters = DopeParameters(
//                namedParameters = mapOf("testParameter" to 1737021572L),
//            ),
//        )
//
//        val actual: NumberParameter = value.asParameter(cmField.typeConverter, "testParameter")
//
//        assertEquals(expected, actual)
//    }
//
//    @Test
//    fun `should support parameter with string converter`() {
//        val cmField = someCMConverterStringField()
//
//        val value = Date(1737021572000)
//        val expected = DopeQuery(
//            queryString = "\$testParameter",
//            parameters = DopeParameters(
//                namedParameters = mapOf("testParameter" to "1737021572"),
//            ),
//        )
//
//        val actual: StringParameter = value.asParameter(cmField.typeConverter, "testParameter")
//
//        assertEquals(expected, actual)
//    }
//
//    @Test
//    fun `should support parameter with boolean converter`() {
//        val cmField = someCMConverterBooleanField()
//        val value = Date(1737021572000)
//        val expected = DopeQuery(
//            queryString = "\$testParameter",
//            parameters = DopeParameters(
//                namedParameters = mapOf("testParameter" to true),
//            ),
//        )
//
//        val actual: BooleanParameter = value.asParameter(cmField.typeConverter, "testParameter")
//
//        assertEquals(expected, actual)
//    }
}
