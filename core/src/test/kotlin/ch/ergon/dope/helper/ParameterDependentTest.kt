package ch.ergon.dope.helper

import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import org.junit.jupiter.api.BeforeEach

interface ParameterDependentTest {
    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }
}
