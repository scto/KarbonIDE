package com.rk.xededitor.ui.routes

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rk.xededitor.ui.animations.NavigationAnimationTransitions
import com.rk.xededitor.ui.screens.project.view.ProjectView
import soup.compose.material.motion.animation.rememberSlideDistance

@Composable
fun ProjectsNavHost(navController: NavHostController){
    val slideDistance = rememberSlideDistance()
    NavHost(
        navController = navController,
        startDestination = ProjectsRoutes.Main.routes,
        enterTransition = { NavigationAnimationTransitions.enterTransition(slideDistance) },
        exitTransition = { NavigationAnimationTransitions.exitTransition(slideDistance) },
        popEnterTransition = { NavigationAnimationTransitions.popEnterTransition(slideDistance) },
        popExitTransition = { NavigationAnimationTransitions.popExitTransition(slideDistance) },
    ){
        composable(ProjectsRoutes.Main.routes){
            ProjectView()
        }

    }
    
}