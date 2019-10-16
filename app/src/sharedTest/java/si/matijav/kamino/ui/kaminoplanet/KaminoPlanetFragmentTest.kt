package si.matijav.kamino.ui.kaminoplanet

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.VerificationMode
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import org.robolectric.annotation.TextLayoutMode
import si.matijav.kamino.FakePlanetRepository
import si.matijav.kamino.R
import si.matijav.kamino.kaminoPlanet
import si.matijav.kamino.ui.ServiceLocator

/**
 * Integration test.
 */
@RunWith(AndroidJUnit4::class)
@MediumTest
@LooperMode(LooperMode.Mode.PAUSED)
@TextLayoutMode(TextLayoutMode.Mode.REALISTIC)
@Config(sdk = [Build.VERSION_CODES.P])
class KaminoPlanetFragmentTest {

    private lateinit var planetRepository: FakePlanetRepository

    @Before
    fun initRepository() {
        planetRepository = FakePlanetRepository(kaminoPlanet)
        ServiceLocator.planetRepository = planetRepository
    }

    @Test
    fun planetFetched_displaysData() {
        val navController = mock(NavController::class.java)
        launchFragment(navController)

        onView(withId(R.id.like_button)).check(ViewAssertions.matches(isEnabled()))
        onView(withId(R.id.planet_name)).check(ViewAssertions.matches(withSubstring(planetRepository.planet.name)))
        onView(withId(R.id.likes_count)).check(ViewAssertions.matches(withSubstring(planetRepository.planet.likes.toString())))
        //onView(withId(R.id.population)).check(ViewAssertions.matches(withSubstring(planetRepository.planet.population.toString())))
        onView(withId(R.id.rotation_period)).check(ViewAssertions.matches(withSubstring(planetRepository.planet.rotationPeriod.toString())))
        onView(withId(R.id.orbital_period)).check(ViewAssertions.matches(withSubstring(planetRepository.planet.orbitalPeriod.toString())))
        onView(withId(R.id.diameter)).check(ViewAssertions.matches(withSubstring(planetRepository.planet.diameter.toString())))
        onView(withId(R.id.climate)).check(ViewAssertions.matches(withSubstring(planetRepository.planet.climate)))
        onView(withId(R.id.gravity)).check(ViewAssertions.matches(withSubstring(planetRepository.planet.gravity)))
        onView(withId(R.id.terrain)).check(ViewAssertions.matches(withSubstring(planetRepository.planet.terrain)))
        onView(withId(R.id.surface_water)).check(ViewAssertions.matches(withSubstring(planetRepository.planet.surfaceWater.toString())))
    }

    @Test
    fun residentsClick_navigatesToResidentsScreen() {
        val navController = mock(NavController::class.java)
        launchFragment(navController)

        onView(withId(R.id.show_residents_button))
            .perform(ViewActions.click())

        verify(navController).navigate(
            KaminoPlanetFragmentDirections.actionKaminoPlanetFragmentToResidentsFragment(
                planetRepository.planet.residentIds.toIntArray()
            )
        )
    }

    @Test
    fun likeSuccessful_disablesLike() {
        val navController = mock(NavController::class.java)
        launchFragment(navController)

        onView(withId(R.id.like_button)).perform(ViewActions.click())

        onView(withId(R.id.like_button)).check(ViewAssertions.matches(isChecked()))
        onView(withId(R.id.like_button)).check(ViewAssertions.matches(not(isEnabled())))
    }

    @Test
    fun imageClicked_enlargesImage() {
        val navController = mock(NavController::class.java)
        launchFragment(navController)

        onView(withId(R.id.planet_image)).perform(ViewActions.click())

        onView(withId(R.id.scrollview)).check(ViewAssertions.matches(not(isDisplayed())))
        onView(withId(R.id.close)).check(ViewAssertions.matches(isDisplayed()))
    }

    private fun launchFragment(navController: NavController?): FragmentScenario<KaminoPlanetFragment> {
        val scenario =
            launchFragmentInContainer<KaminoPlanetFragment>(
                Bundle.EMPTY,
                R.style.AppTheme
            )
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }
        return scenario
    }
}
