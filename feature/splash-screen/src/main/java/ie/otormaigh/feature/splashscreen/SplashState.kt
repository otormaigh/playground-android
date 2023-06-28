package ie.otormaigh.feature.splashscreen

import ie.otormaigh.statemachine.Action
import ie.otormaigh.statemachine.State

sealed class SplashState : State() {
  data class Loading(val isLoading: Boolean) : SplashState()
}

sealed class SplashAction : Action() {
  object ViewLoaded : SplashAction()
}