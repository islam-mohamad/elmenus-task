package com.islam.task.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.islam.task.core.navigation.NavControllerManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


abstract class BaseFragment<Binding : ViewBinding, STATE, EFFECT, INTENT, VM : BaseViewModel<STATE, EFFECT, INTENT>> :
    Fragment() {

    abstract var navControllerManager: NavControllerManager

    protected abstract val viewModel: VM

    private var _binding: Binding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindView(inflater = inflater, container = container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup(savedInstanceState)
        observeOnUIUpdates()
        observeOnEffectsUpdates()
    }

    override fun onResume() {
        super.onResume()
        navControllerManager.bind(NavHostFragment.findNavController(this))
    }

    private fun observeOnUIUpdates() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { uiState ->
                    renderState(uiState)
                }
            }
        }
    }

    private fun observeOnEffectsUpdates() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.effect.collectLatest {
                    renderEffects(it)
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        navControllerManager.unbind()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected abstract fun bindView(inflater: LayoutInflater, container: ViewGroup?): Binding

    abstract fun renderState(ui: STATE)
    abstract fun renderEffects(effect: EFFECT)
    abstract fun setup(savedInstanceState: Bundle?)

    protected fun showSnackBar(messageRes:Int){
        Snackbar.make(binding.root, messageRes, Snackbar.LENGTH_LONG)
            .show()
    }
}