package top.niunaijun.blackboxa.view.base

import android.app.AlertDialog
import android.view.KeyEvent
import top.niunaijun.blackboxa.R

abstract class LoadingActivity : BaseActivity() {

    private var loadingDialog: AlertDialog? = null

    fun showLoading() {
        if (loadingDialog?.isShowing == true) return
        val view = layoutInflater.inflate(R.layout.dialog_loading, null)
        loadingDialog = AlertDialog.Builder(this, R.style.Theme_BlackBox_Dialog)
            .setView(view)
            .setCancelable(false)
            .setOnKeyListener { _, keyCode, _ ->
                keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_ESCAPE
            }
            .create()
        loadingDialog?.show()
    }

    fun hideLoading() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }
}
