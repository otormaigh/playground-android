package ie.otormaigh.feature.login

import ie.otormaigh.statemachine.Action
import ie.otormaigh.statemachine.State

sealed class LoginAction : Action() {
  data class LoginClick(val email: String, val password: String) : LoginAction()
}

sealed class LoginState : State() {
  sealed class Error : LoginState() {
    object InvalidInput : Error()
  }
}