package ie.otormaigh.feature.splashscreen

import android.os.Bundle
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import ie.otormaigh.extension.observe
import ie.otormaigh.feature.splashscreen.databinding.ActivitySplashBinding
import ie.otormaigh.statemachine.Action
import ie.otormaigh.statemachine.State
import ie.otormaigh.statemachine.StateMachine

// https://developer.android.com/guide/topics/ui/splash-screen
class SplashActivity : AppCompatActivity(), StateMachine {
  private lateinit var binding: ActivitySplashBinding
  private val viewModel = SplashViewModel()

  private val preDrawListener by lazy {
    object : ViewTreeObserver.OnPreDrawListener {
      override fun onPreDraw(): Boolean {
        return if (true) {
          binding.contentRoot.viewTreeObserver.removeOnPreDrawListener(this)
          true
        } else {
          false
        }
      }
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivitySplashBinding.inflate(layoutInflater)
    setContentView(binding.root)
    observe(viewModel.state, ::processState)
    observe(viewModel.sideEffect, ::processSideEffect)

    binding.contentRoot.viewTreeObserver.addOnPreDrawListener(preDrawListener)
  }

  override fun processState(state: State) {
    when (state) {
      is SplashState.Loading -> if (state.isLoading) {
        // Do something
      } else {
        // Do something else
      }
      else -> super.processState(state)
    }
  }

  override fun dispatchAction(action: Action) {
    super.dispatchAction(action)
    viewModel.processAction(action)
  }
}