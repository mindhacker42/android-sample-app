package si.matijav.kamino.ui.residents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import si.matijav.kamino.R
import si.matijav.kamino.databinding.ResidentsFragmentBinding
import si.matijav.kamino.App

class ResidentsFragment : Fragment() {

    private lateinit var residentsAdapter: ResidentAdapter
    private lateinit var dataBinding: ResidentsFragmentBinding
    private lateinit var viewModel: ResidentsViewModel
    private val args: ResidentsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.residents_fragment, container, false)

        initializeViewModel()

        dataBinding = ResidentsFragmentBinding.bind(rootView)
        dataBinding.lifecycleOwner = viewLifecycleOwner

        setupRecyclerView()

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.residents.observe(viewLifecycleOwner, Observer { residents ->
            dataBinding.executePendingBindings()
            this.residentsAdapter.residents = residents
        })
        viewModel.selectedResident.observe(viewLifecycleOwner, Observer { selectedResident ->
            val action =
                ResidentsFragmentDirections.actionResidentsFragmentToResidentDetailsFragment(
                    selectedResident
                )
            findNavController().navigate(action)
        })
    }

    fun setupRecyclerView() {
        val recyclerView = dataBinding.residentsView
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        residentsAdapter = ResidentAdapter(viewModel)
        recyclerView.adapter = residentsAdapter
    }

    private fun initializeViewModel() {
        val residentRepository = App.getApp(requireContext()).residentRepository
        viewModel = ViewModelProviders.of(this, ResidentsViewModel.Factory(residentRepository))
            .get(ResidentsViewModel::class.java)
        viewModel.setResidentIds(args.residentIds.asList())
    }
}
