package ie.otormaigh.feature.splashscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ie.otormaigh.statemachine.Action
import ie.otormaigh.statemachine.SideEffect
import ie.otormaigh.statemachine.State
import ie.otormaigh.statemachine.StateMachine

class SplashViewModel : StateMachine {
  private val _state = MutableLiveData<State>()
  val state: LiveData<State>
    get() = _state
  private val _sideEffect = MutableLiveData<SideEffect>()
  val sideEffect: LiveData<SideEffect>
    get() = _sideEffect

  override fun processAction(action: Action) {
    when (action) {
      SplashAction.ViewLoaded -> {}
      else -> super.processAction(action)
    }
  }

  override fun dispatchSideEffect(sideEffect: SideEffect) {
    super.dispatchSideEffect(sideEffect)
  }

  override fun dispatchState(state: State) {
    super.dispatchState(state)
  }
}