package com.rk.xededitor

import android.content.Context
import android.graphics.Color
import com.rk.xededitor.Settings.SettingsData
import com.rk.xededitor.Settings.SettingsData.isDarkMode
import com.rk.xededitor.rkUtils.runOnUiThread
import io.github.rosemoe.sora.lang.Language
import io.github.rosemoe.sora.langs.textmate.TextMateColorScheme
import io.github.rosemoe.sora.langs.textmate.TextMateLanguage
import io.github.rosemoe.sora.langs.textmate.registry.FileProviderRegistry
import io.github.rosemoe.sora.langs.textmate.registry.GrammarRegistry
import io.github.rosemoe.sora.langs.textmate.registry.ThemeRegistry
import io.github.rosemoe.sora.langs.textmate.registry.model.ThemeModel
import io.github.rosemoe.sora.langs.textmate.registry.provider.AssetsFileResolver
import io.github.rosemoe.sora.widget.CodeEditor
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme
import org.eclipse.tm4e.core.registry.IThemeSource
import java.io.File

class SetupEditor(val editor: CodeEditor, private val ctx: Context) {

    fun setupLanguage(fileName: String) {
        when (fileName.substringAfterLast('.', "")) {
            "java", "bsh" -> setLanguage("source.java")
            "html" -> setLanguage("text.html.basic")
            "kt", "kts" -> setLanguage("source.kotlin")
            "py" -> setLanguage("source.python")
            "xml" -> setLanguage("text.xml")
            "js" -> setLanguage("source.js")
            "md" -> setLanguage("text.html.markdown")
            "c" -> setLanguage("source.c")
            "cpp", "h" -> setLanguage("source.cpp")
            "json" -> setLanguage("source.json")
            "css" -> setLanguage("source.css")
            "cs" -> setLanguage("source.cs")
            "yml", "eyaml", "eyml", "yaml", "cff" -> setLanguage("source.yaml")
        }
    }

    companion object {
        private var isInit = false
        private var darkThemeRegistry: ThemeRegistry? = null
        private var oledThemeRegistry: ThemeRegistry? = null
        private var lightThemeRegistry: ThemeRegistry? = null

        fun init(context: Context) {
            if (!isInit) {
                initGrammarRegistry(context)
                initTextMateTheme(context)
                isInit = true
            }
        }

        private fun initGrammarRegistry(context: Context) {
            FileProviderRegistry.getInstance().addFileProvider(
                AssetsFileResolver(context.applicationContext?.assets)
            )
            GrammarRegistry.getInstance().loadGrammars("textmate/languages.json")
        }

        private fun initTextMateTheme(context: Context) {
            Assets.verify(context)
            darkThemeRegistry = ThemeRegistry()
            oledThemeRegistry = ThemeRegistry()
            lightThemeRegistry = ThemeRegistry()

            try {
                fun registerDarkTheme() {
                    val path = File(context.filesDir.parentFile, "unzip/textmate/darcula.json").absolutePath
                    if (!File(path).exists()) {
                        runOnUiThread {
                            rkUtils.toast( context.resources.getString(R.string.theme_not_found_err))
                        }
                        return
                    }
                    darkThemeRegistry?.loadTheme(
                        ThemeModel(
                            IThemeSource.fromInputStream(
                                FileProviderRegistry.getInstance().tryGetInputStream(path),
                                path,
                                null
                            ), "darcula"
                        )
                    )
                }

                fun registerOledTheme() {
                    val path = File(context.filesDir.parentFile, "unzip/textmate/black/darcula.json").absolutePath
                    if (!File(path).exists()) {
                        runOnUiThread {
                            rkUtils.toast( context.resources.getString(R.string.theme_not_found_err))
                        }
                        return
                    }
                    oledThemeRegistry?.loadTheme(
                        ThemeModel(
                            IThemeSource.fromInputStream(
                                FileProviderRegistry.getInstance().tryGetInputStream(path),
                                path,
                                null
                            ), "darcula-oled"
                        )
                    )
                }

                fun registerLightTheme() {
                    val path = File(context.filesDir.parentFile, "unzip/textmate/quietlight.json").absolutePath
                    if (!File(path).exists()) {
                        runOnUiThread {
                            rkUtils.toast( context.resources.getString(R.string.theme_not_found_err))
                        }
                        return
                    }

                    lightThemeRegistry?.loadTheme(
                        ThemeModel(
                            IThemeSource.fromInputStream(
                                FileProviderRegistry.getInstance().tryGetInputStream(path),
                                path,
                                null
                            ), "quietlight"
                        )
                    )
                }

                registerDarkTheme()
                registerOledTheme()
                registerLightTheme()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun setLanguage(languageScopeName: String) {
        val language = TextMateLanguage.create(
            languageScopeName, true /* true for enabling auto-completion */
        )
        editor.setEditorLanguage(language as Language)
    }

    fun ensureTextmateTheme(context:Context) {
        Assets.verify(context)
        init(context)
        val themeRegistry = when {
            isDarkMode(ctx) && SettingsData.isOled() -> oledThemeRegistry
            isDarkMode(ctx) -> darkThemeRegistry
            else -> lightThemeRegistry
        }

        themeRegistry?.let {
            val editorColorScheme: EditorColorScheme = TextMateColorScheme.create(it)
            if (isDarkMode(ctx) && SettingsData.isOled()) {
                editorColorScheme.setColor(EditorColorScheme.WHOLE_BACKGROUND, Color.BLACK)
            }
            runOnUiThread {
                editor.colorScheme = editorColorScheme
            }
        }
    }
}
