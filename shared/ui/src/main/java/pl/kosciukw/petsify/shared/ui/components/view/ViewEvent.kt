package pl.kosciukw.petsify.shared.ui.components.view

/**
 * Represents a user-driven interaction or input that should trigger a change in the application state.
 *
 * All user events (e.g., text input, button clicks, lifecycle events) should be modeled as implementations of this interface.
 */
interface ViewEvent

/**
 * Represents the complete, immutable state of the UI at any given time.
 *
 * This should contain all data needed to render the screen, including:
 * - Form fields
 * - Loading flags
 * - Validation messages
 * - Error states
 */
interface ViewState

/**
 * Represents a one-time effect or action that the UI should handle once and not persist in state.
 *
 * Examples include:
 * - Navigation actions
 * - Snackbar/toast messages
 * - Dialog triggers
 */
interface ViewSingleAction