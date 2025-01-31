package nz.co.example.app.features.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavBackStackEntry

internal fun navEnterTransition(): AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition =
    {
        slideInFromLeft()
    }

internal fun navExitTransition(): AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
    slideOutToLeft()
}

internal fun navPopExitTransition(): AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition =
    {
        slideOutToRight()
    }

internal fun navPopEnterTransition(): AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition =
    {
        slideInFromRight()
    }


internal fun fadeIn() = fadeIn(animationSpec = tween(DEFAULT_TRANSITION_DURATION_IN_MILLIS))

internal fun fadeOut() = fadeOut(animationSpec = tween(DEFAULT_TRANSITION_DURATION_IN_MILLIS))

internal fun AnimatedContentTransitionScope<NavBackStackEntry>.slideInFromLeft() =
    slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Left,
        tween(DEFAULT_TRANSITION_DURATION_IN_MILLIS)
    )

internal fun AnimatedContentTransitionScope<NavBackStackEntry>.slideOutToLeft() =
    slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Left,
        tween(DEFAULT_TRANSITION_DURATION_IN_MILLIS)
    )

internal fun AnimatedContentTransitionScope<NavBackStackEntry>.slideOutToRight() =
    slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Right,
        tween(DEFAULT_TRANSITION_DURATION_IN_MILLIS)
    )

internal fun AnimatedContentTransitionScope<NavBackStackEntry>.slideInFromRight() =
    slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Right,
        tween(DEFAULT_TRANSITION_DURATION_IN_MILLIS)
    )

internal const val DEFAULT_TRANSITION_DURATION_IN_MILLIS = 600