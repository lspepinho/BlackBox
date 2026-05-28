package top.niunaijun.blackboxa.util

import android.content.Context
import android.content.Intent
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.core.graphics.drawable.toBitmap
import top.niunaijun.blackboxa.R
import top.niunaijun.blackboxa.app.App
import top.niunaijun.blackboxa.app.AppManager
import top.niunaijun.blackboxa.bean.AppInfo
import top.niunaijun.blackboxa.util.ContextUtil.openAppSystemSettings
import top.niunaijun.blackboxa.view.main.ShortcutActivity

/**
 *
 * @Description: 桌面快捷方式 工具类
 * @Author: BlackBox
 * @CreateDate: 2022/2/27 22:56
 */
object ShortcutUtil {


    /**
     * 创建桌面快捷方式
     * @param userID Int userID
     * @param info AppInfo
     */
    fun createShortcut(context: Context,userID: Int, info: AppInfo) {

        if (ShortcutManagerCompat.isRequestPinShortcutSupported(context)) {
            val labelName = info.name + userID
            val intent = Intent(context, ShortcutActivity::class.java)
                .setAction(Intent.ACTION_MAIN)
                .putExtra("pkg", info.packageName)
                .putExtra("userId", userID)
            val input = EditText(context)
            input.text.append(labelName)
            AlertDialog.Builder(context)
                .setTitle(R.string.app_shortcut)
                .setView(input)
                .setPositiveButton(R.string.done) { _, _ ->
                    val shortcutLabel = input.text.toString()
                    val shortcutInfo: ShortcutInfoCompat = ShortcutInfoCompat.Builder(context, info.packageName + userID)
                        .setIntent(intent)
                        .setShortLabel(shortcutLabel)
                        .setLongLabel(shortcutLabel)
                        .setIcon(IconCompat.createWithBitmap(info.icon.toBitmap()))
                        .build()

                    ShortcutManagerCompat.requestPinShortcut(context, shortcutInfo, null)
                    showAllowPermissionDialog(context)
                }
                .setNegativeButton(R.string.cancel, null)
                .show()

        } else {
            toast(R.string.cannot_create_shortcut)
        }
    }
                positiveButton(R.string.done)
                negativeButton(R.string.cancel)
            }

        } else {
            toast(R.string.cannot_create_shortcut)
        }
    }

    private fun showAllowPermissionDialog(context: Context){
        if (!AppManager.mBlackBoxLoader.showShortcutPermissionDialog()){
            return
        }

        AlertDialog.Builder(context)
            .setTitle(R.string.try_add_shortcut)
            .setMessage(R.string.add_shortcut_fail_msg)
            .setPositiveButton(R.string.done, null)
            .setNegativeButton(R.string.permission_setting) { _, _ ->
                App.getContext().openAppSystemSettings()
            }
            .setNeutralButton(R.string.no_reminders) { _, _ ->
                AppManager.mBlackBoxLoader.invalidShortcutPermissionDialog(false)
            }
            .show()
    }
}