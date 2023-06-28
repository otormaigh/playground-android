package ie.otormaigh.feature.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ie.otormaigh.playground.store.AccountStore
import ie.otormaigh.statemachine.Action
import ie.otormaigh.statemachine.SideEffect
import ie.otormaigh.statemachine.State
import ie.otormaigh.statemachine.StateMachine
import ie.otormaigh.statemachine.ViewSideEffect

class LoginViewModel : StateMachine {
  private val _state = MutableLiveData<State>()
  val state: LiveData<State>
    get() = _state
  private val _sideEffect = MutableLiveData<SideEffect>()
  val sideEffect: LiveData<SideEffect>
    get() = _sideEffect

  private val accountStore = AccountStore()

  override fun processAction(action: Action): Unit = when (action) {
    is LoginAction.LoginClick -> {
      val isEmailValid = validateInput(action.email) { isNotEmpty() }
      val isPasswordValid = validateInput(action.password) { isNotEmpty() }

      when {
        !isEmailValid -> dispatchState(LoginState.Error.InvalidInput)
        !isPasswordValid -> dispatchState(LoginState.Error.InvalidInput)
        else -> attemptLogin(action.email, action.password)
      }
    }
    else -> super.processAction(action)
  }

  private fun validateInput(input: String, validator: String.() -> Boolean): Boolean =
    validator(input)

  private fun attemptLogin(email: String, password: String) {
    dispatchSideEffect(ViewSideEffect.Loading.Show)

    try {
      accountStore.login(email, password)
    } catch (e: Exception) {

    } finally {
      dispatchSideEffect(ViewSideEffect.Loading.Hide)
    }
  }
}