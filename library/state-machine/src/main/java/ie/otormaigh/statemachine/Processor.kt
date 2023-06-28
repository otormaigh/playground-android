package ie.otormaigh.statemachine

interface Processor {
  // Process in View from ViewModel
  fun processState(state: State) {}
  fun processSideEffect(sideEffect: SideEffect) {}

  // Process in ViewModel from View
  fun processAction(action: Action) {}
}