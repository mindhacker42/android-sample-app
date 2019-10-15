package si.matijav.kamino

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.robolectric.annotation.LooperMode
import org.robolectric.annotation.TextLayoutMode
import si.matijav.kamino.ui.kaminoplanet.KaminoPlanetFragment
import si.matijav.kamino.ui.kaminoplanet.KaminoPlanetFragmentDirections

/**
 * Integration test.
 */
@RunWith(AndroidJUnit4::class)
@MediumTest
@LooperMode(LooperMode.Mode.PAUSED)
@TextLayoutMode(TextLayoutMode.Mode.REALISTIC)
class KaminoPlanetFragmentTest {
    @Test
    fun allResident_navigatesToResidentsScreen() {
        // GIVEN - On the "Add Task" screen.
        val navController = mock(NavController::class.java)
        launchFragment(navController)

        // WHEN - Valid title and description combination and click save
        onView(ViewMatchers.withId(R.id.show_residents_button))
            .perform(ViewActions.click())

        // THEN - Verify that we navigated back to the tasks screen.
        verify(navController).navigate(
            KaminoPlanetFragmentDirections.actionKaminoPlanetFragmentToResidentsFragment(emptyList<Int>().toIntArray())
        )
    }


    private fun launchFragment(navController: NavController?) {
        val scenario =
            launchFragmentInContainer<KaminoPlanetFragment>(Bundle.EMPTY, R.style.AppTheme)
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }
    }
}
