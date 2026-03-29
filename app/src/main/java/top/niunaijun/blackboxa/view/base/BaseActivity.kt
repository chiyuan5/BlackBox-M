package top.niunaijun.blackboxa.view.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    protected fun initToolbar(
        toolbar: Toolbar,
        title: Int,
        showBack: Boolean = false,
        onBack: (() -> Unit)? = null
    ) {
        setSupportActionBar(toolbar)
        toolbar.setTitle(title)
        if (showBack) {
            supportActionBar?.let {
                it.setDisplayHomeAsUpEnabled(true)
                toolbar.setNavigationOnClickListener {
                    if (onBack != null) {
                        onBack()
                    } else {
                        onBackPressedDispatcher.onBackPressed()
                    }
                }
            }
        }
    }

    protected fun applyToolbarInsets(toolbar: Toolbar) {
        val initialTop = toolbar.paddingTop
        ViewCompat.setOnApplyWindowInsetsListener(toolbar) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(top = initialTop + bars.top)
            insets
        }
        ViewCompat.requestApplyInsets(toolbar)
    }

    protected fun applyBottomInsets(vararg views: View) {
        views.forEach { target ->
            val initialBottom = target.paddingBottom
            ViewCompat.setOnApplyWindowInsetsListener(target) { view, insets ->
                val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                view.updatePadding(bottom = initialBottom + bars.bottom)
                insets
            }
            ViewCompat.requestApplyInsets(target)
        }
    }

    protected fun currentUserID(): Int = intent.getIntExtra("userID", 0)
}
