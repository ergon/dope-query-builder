package ch.ergon.dope.helper

import ch.ergon.dope.DopeQueryManager
import kotlin.test.BeforeTest

interface ManagerDependentTest {
    var manager: DopeQueryManager

    @BeforeTest
    fun setUp() {
        manager = DopeQueryManager()
    }
}
