package si.matijav.kamino.ui.residents

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import si.matijav.kamino.FakeResidentRepository
import si.matijav.kamino.LiveDataTestUtil
import si.matijav.kamino.data.Resident
import si.matijav.kamino.ui.kaminoplanet.residents

class ResidentsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var residentsViewModel: ResidentsViewModel
    private lateinit var residentRepository: FakeResidentRepository

    @Before
    fun setupViewModel() {
        residentRepository = FakeResidentRepository(residents)
        residentsViewModel = ResidentsViewModel(residentRepository)
    }

    @Test
    fun loadResidents_loading() {
        residentsViewModel.setResidentIds(residents.map { it.id })

        // Load residents
        Truth.assertThat(LiveDataTestUtil.getValue(residentsViewModel.dataLoading)).isTrue()
        LiveDataTestUtil.getValue(residentsViewModel.residents)

        Truth.assertThat(LiveDataTestUtil.getValue(residentsViewModel.dataLoading)).isFalse()
    }

    @Test
    fun loadResidents_success() {
        residentsViewModel.setResidentIds(residents.map { it.id })

        // Load residents
        LiveDataTestUtil.getValue(residentsViewModel.residents)

        Truth.assertThat(LiveDataTestUtil.getValue(residentsViewModel.residents))
            .isEqualTo(residentRepository.residents)
    }

    @Test
    fun loadResidents_failure() {
        residentRepository.shouldReturnError = true
        residentsViewModel.setResidentIds(residents.map { it.id })

        // Load residents
        LiveDataTestUtil.getValue(residentsViewModel.residents)

        Truth.assertThat(LiveDataTestUtil.getValue(residentsViewModel.residents))
            .isEqualTo(emptyList<Resident>())
    }

    @Test
    fun selectResident_selected() {
        // At the beginning no resident is selected
        Truth.assertThat(LiveDataTestUtil.getValue(residentsViewModel.selectedResident)).isNull()

        val resident = residentRepository.residents.first()
        residentsViewModel.selectResident(resident)

        Truth.assertThat(LiveDataTestUtil.getValue(residentsViewModel.selectedResident))
            .isEqualTo(resident)
    }
}
