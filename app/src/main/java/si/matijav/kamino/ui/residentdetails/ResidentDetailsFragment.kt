package si.matijav.kamino.ui.residentdetails

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs

import si.matijav.kamino.R
import si.matijav.kamino.databinding.KaminoPlanetFragmentBinding
import si.matijav.kamino.databinding.ResidentDetailsFragmentBinding
import si.matijav.kamino.databinding.ResidentsFragmentBinding
import si.matijav.kamino.ui.kaminoplanet.KaminoPlanetViewModel
import si.matijav.kamino.ui.residents.ResidentsFragmentArgs
import si.matijav.kamino.ui.residents.ResidentsViewModel

class ResidentDetailsFragment : Fragment() {

    private lateinit var viewModel: ResidentDetailsViewModel
    private lateinit var dataBinding: ResidentDetailsFragmentBinding
    private val args: ResidentDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.resident_details_fragment, container, false)
        viewModel = ViewModelProviders.of(this).get(ResidentDetailsViewModel::class.java)
        viewModel.resident = args.resident

        dataBinding = ResidentDetailsFragmentBinding.bind(rootView)
        dataBinding.viewModel = viewModel
        dataBinding.lifecycleOwner = viewLifecycleOwner

        return dataBinding.root
    }
}
