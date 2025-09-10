package ch.ergon.dope.helper

import kotlin.test.BeforeTest

interface ManagerDependentTest {
//    var manager: DopeQueryManager

    @BeforeTest
    fun setUp() {
//        manager = DopeQueryManager()
    }
}
