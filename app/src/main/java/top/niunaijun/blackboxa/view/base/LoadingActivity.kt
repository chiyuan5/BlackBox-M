package top.niunaijun.blackboxa.view.base

import androidx.activity.OnBackPressedCallback
import com.roger.catloadinglibrary.CatLoadingView
import top.niunaijun.blackboxa.R

/**
 *
 * @Description: loading activity
 * @Author: BlackBox
 * @CreateDate: 2022/3/2 21:49
 */
abstract class LoadingActivity : BaseActivity() {

    private lateinit var loadingView: CatLoadingView

    private val loadingBackCallback = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            // Ignore predictive back while the loading dialog is visible.
        }
    }

    override fun onStart() {
        super.onStart()
        onBackPressedDispatcher.addCallback(this, loadingBackCallback)
    }

    fun showLoading() {
        if (!this::loadingView.isInitialized) {
            loadingView = CatLoadingView()
        }

        if (!loadingView.isAdded) {
            loadingView.setBackgroundColor(R.color.primary)
            loadingView.show(supportFragmentManager, "")
            supportFragmentManager.executePendingTransactions()
            loadingView.setClickCancelAble(false)
            loadingView.dialog?.setCancelable(false)
            loadingView.dialog?.setCanceledOnTouchOutside(false)
            loadingBackCallback.isEnabled = true
        }
    }

    fun hideLoading() {
        loadingBackCallback.isEnabled = false
        if (this::loadingView.isInitialized) {
            loadingView.dismiss()
        }
    }
}
