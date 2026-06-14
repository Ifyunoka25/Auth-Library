package university.miva.designsystem.transitions

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavBackStackEntry

object MotionDurations {
    const val Emphasized = 500
    const val EmphasizedDecelerate = 400
    const val EmphasizedAccelerate = 200
    const val Standard = 300
    const val StandardDecelerate = 250
    const val StandardAccelerate = 200
}

object MotionEasings {
    val EmphasizedDecelerate = CubicBezierEasing(0.05f, 0.7f, 0.1f, 1.0f)
    val EmphasizedAccelerate = CubicBezierEasing(0.3f, 0.0f, 0.8f, 0.15f)
    val Standard = FastOutSlowInEasing
    val StandardDecelerate = LinearOutSlowInEasing
    val StandardAccelerate = FastOutLinearInEasing
}

fun defaultEnterTransition(
    initial: NavBackStackEntry,
    target: NavBackStackEntry,
): EnterTransition =
    slideInHorizontally(
        initialOffsetX = { it },
        animationSpec = tween(MotionDurations.Emphasized),
    ) + fadeIn(animationSpec = tween(MotionDurations.Emphasized))

fun defaultExitTransition(
    initial: NavBackStackEntry,
    target: NavBackStackEntry,
): ExitTransition =
    slideOutHorizontally(
        targetOffsetX = { -it },
        animationSpec = tween(MotionDurations.Emphasized),
    ) + fadeOut(animationSpec = tween(MotionDurations.Emphasized))

fun defaultPopEnterTransition(
    initial: NavBackStackEntry,
    target: NavBackStackEntry,
): EnterTransition =
    slideInHorizontally(
        initialOffsetX = { -it },
        animationSpec = tween(MotionDurations.Emphasized),
    ) + fadeIn(animationSpec = tween(MotionDurations.Emphasized))

fun defaultPopExitTransition(
    initial: NavBackStackEntry,
    target: NavBackStackEntry,
): ExitTransition =
    slideOutHorizontally(
        targetOffsetX = { it },
        animationSpec = tween(MotionDurations.Emphasized),
    ) + fadeOut(animationSpec = tween(MotionDurations.Emphasized))
