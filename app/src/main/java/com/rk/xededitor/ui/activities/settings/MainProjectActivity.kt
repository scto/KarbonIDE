package com.rk.xededitor.ui.activities.settings

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.rk.xededitor.BaseActivity
import com.rk.xededitor.ui.routes.ProjectsNavHost
import com.rk.xededitor.ui.routes.SettingsNavHost
import com.rk.xededitor.ui.theme.KarbonTheme

class MainProjectActivity : BaseActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KarbonTheme {
                val navController = rememberNavController()
                ProjectsNavHost(navController)
            }
        }
    }
}