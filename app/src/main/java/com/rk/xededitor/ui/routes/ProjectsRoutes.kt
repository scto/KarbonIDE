package com.rk.xededitor.ui.routes

sealed class ProjectsRoutes(val routes: String) {
    data object Main : ProjectsRoutes("main")
}