package ie.otormaigh.statemachine

open class Action
open class State
open class SideEffect : State()

open class ViewState : State()
sealed class ViewSideEffect : SideEffect() {
  sealed class Loading : ViewSideEffect() {
    object Show : Loading()
    object Hide : Loading()
  }

  open class Navigation : ViewSideEffect() {
    open class Error : Navigation()
  }

  open class Effect : ViewSideEffect()
}