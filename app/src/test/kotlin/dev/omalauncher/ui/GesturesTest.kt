package dev.omalauncher.ui

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class GesturesTest {
    /** Preferences save the id, so every action the settings list must round-trip. */
    @Test fun idsRoundTrip() {
        for (action in GestureAction.entries) {
            assertEquals(action, GestureAction.of(action.id))
        }
    }

    @Test fun unknownIdsArePickedAsNone() {
        assertNull(GestureAction.of(null))
        assertNull(GestureAction.of("screen-off"))
    }
}
