package ie.otormaigh.feature.login

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ie.otormaigh.extension.observe
import ie.otormaigh.feature.splashscreen.databinding.ActivityLoginBinding
import ie.otormaigh.statemachine.SideEffect
import ie.otormaigh.statemachine.State
import ie.otormaigh.statemachine.StateMachine
import ie.otormaigh.statemachine.ViewSideEffect

class LoginActivity : AppCompatActivity(), StateMachine {
  private lateinit var binding: ActivityLoginBinding
  private val viewModel = LoginViewModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityLoginBinding.inflate(layoutInflater)
    setContentView(binding.root)

    observe(viewModel.state, ::processState)
    observe(viewModel.sideEffect, ::processSideEffect)

    binding.btnLogin.setOnClickListener {
      viewModel.processAction(
        LoginAction.LoginClick(
          binding.etEmail.text.toString(),
          binding.etPassword.text.toString()
        )
      )
    }
  }

  override fun processState(state: State): Unit = when (state) {
    is LoginState.Error -> processError(state)
    else -> super.processState(state)
  }

  override fun processSideEffect(sideEffect: SideEffect): Unit = when (sideEffect) {
    is ViewSideEffect -> processViewSideEffect(sideEffect)
    else -> super.processSideEffect(sideEffect)
  }

  private fun processError(error: LoginState.Error): Unit = when (error) {
    LoginState.Error.InvalidInput -> {
      Toast.makeText(this, "Invalid Input", Toast.LENGTH_LONG).show()
    }
  }

  private fun processViewSideEffect(viewSideEffect: ViewSideEffect): Unit = when (viewSideEffect) {
    ViewSideEffect.Loading.Show -> toggleInputFields(false)
    ViewSideEffect.Loading.Hide -> toggleInputFields(true)
    else -> {}
  }

  private fun toggleInputFields(isEnabled: Boolean) {
    binding.etEmail.isEnabled = isEnabled
    binding.etEmail.isEnabled = isEnabled
    binding.etEmail.isEnabled = isEnabled
  }
}