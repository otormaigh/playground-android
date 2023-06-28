package ie.otormaigh.statemachine

interface Dispatcher {
  // Dispatch from ViewModel to View
  fun dispatchState(state: State) {}
  fun dispatchSideEffect(sideEffect: SideEffect) {}

  // Dispatch from View to ViewModel
  fun dispatchAction(action: Action) {}
}