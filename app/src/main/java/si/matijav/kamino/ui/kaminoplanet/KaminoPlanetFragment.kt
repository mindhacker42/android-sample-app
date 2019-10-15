package si.matijav.kamino.ui.kaminoplanet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import si.matijav.kamino.R
import si.matijav.kamino.data.Planet
import si.matijav.kamino.databinding.KaminoPlanetFragmentBinding

class KaminoPlanetFragment : Fragment() {

    private lateinit var transition: ChangeBounds
    private lateinit var enlargedImageConstraintSet: ConstraintSet
    private lateinit var defaultConstraintSet: ConstraintSet
    private lateinit var viewModel: KaminoPlanetViewModel
    private lateinit var dataBinding: KaminoPlanetFragmentBinding
    private var isImageEnlarged = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.kamino_planet_fragment, container, false)
        viewModel = ViewModelProviders.of(this).get(KaminoPlanetViewModel::class.java)

        dataBinding = KaminoPlanetFragmentBinding.bind(rootView)
        dataBinding.fragment = this
        dataBinding.viewModel = viewModel
        dataBinding.lifecycleOwner = viewLifecycleOwner

        setupImageAnimation()

        return dataBinding.root
    }

    fun onSeeAllResidents(planet: Planet?) {
        planet ?: return

        val action =
            KaminoPlanetFragmentDirections.actionKaminoPlanetFragmentToResidentsFragment(planet.residentIds.toIntArray())
        findNavController().navigate(action)
    }

    fun onEnlargeImage() {
        isImageEnlarged = !isImageEnlarged

        val constraintSet =
            if (isImageEnlarged) enlargedImageConstraintSet else defaultConstraintSet
        TransitionManager.beginDelayedTransition(dataBinding.rootLayout, transition)
        constraintSet.applyTo(dataBinding.rootLayout)
    }

    fun onLikePlanet() {
        viewModel.likeKaminoPlanet.observe(viewLifecycleOwner, Observer { result ->
            Toast.makeText(requireContext(), result, Toast.LENGTH_SHORT).show()
            dataBinding.executePendingBindings()
            viewModel.likeKaminoPlanet.removeObservers(viewLifecycleOwner)
        })
    }

    private fun setupImageAnimation() {
        defaultConstraintSet = ConstraintSet()
        defaultConstraintSet.clone(requireContext(), R.layout.kamino_planet_fragment)

        enlargedImageConstraintSet = ConstraintSet()
        enlargedImageConstraintSet.clone(defaultConstraintSet)
        enlargedImageConstraintSet.apply {
            setVisibility(R.id.scrollview, View.GONE)
            setVisibility(R.id.close, View.VISIBLE)
            constrainHeight(R.id.planet_image, ConstraintLayout.LayoutParams.MATCH_CONSTRAINT)
            connect(R.id.planet_image, ConstraintSet.BOTTOM, R.id.planet_name, ConstraintSet.TOP)
            connect(R.id.planet_name, ConstraintSet.BOTTOM, R.id.likes_count, ConstraintSet.TOP)
            connect(
                R.id.likes_count,
                ConstraintSet.BOTTOM,
                ConstraintLayout.LayoutParams.PARENT_ID,
                ConstraintSet.BOTTOM
            )
            setMargin(
                R.id.likes_count, ConstraintSet.BOTTOM,
                resources.getDimension(R.dimen.view_margin_bottom).toInt()
            )
        }

        transition = ChangeBounds()
        transition.setInterpolator(AnticipateOvershootInterpolator(1.0f))
        transition.setDuration(500)
    }
}
