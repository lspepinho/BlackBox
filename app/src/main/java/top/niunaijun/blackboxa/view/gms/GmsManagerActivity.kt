package top.niunaijun.blackboxa.view.gms

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Switch
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import cbfg.rvadapter.RVAdapter
import top.niunaijun.blackboxa.R
import top.niunaijun.blackboxa.bean.GmsBean
import top.niunaijun.blackboxa.databinding.ActivityGmsBinding
import top.niunaijun.blackboxa.util.InjectionUtil
import top.niunaijun.blackboxa.util.inflate
import top.niunaijun.blackboxa.util.toast
import top.niunaijun.blackboxa.view.base.LoadingActivity

/**
 *
 * @Description: gms manager activity
 * @Author: BlackBox
 * @CreateDate: 2022/3/2 21:06
 */
class GmsManagerActivity : LoadingActivity() {

    private lateinit var viewModel: GmsViewModel

    private lateinit var mAdapter: RVAdapter<GmsBean>

    private val viewBinding: ActivityGmsBinding by inflate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        initToolbar(viewBinding.toolbarLayout.toolbar, R.string.gms_manager, true)
        initViewModel()

        initRecyclerView()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, InjectionUtil.getGmsFactory())[GmsViewModel::class.java]
        showLoading()

        viewModel.mInstalledLiveData.observe(this) {
            hideLoading()
            mAdapter.setItems(it)
        }

        viewModel.mUpdateInstalledLiveData.observe(this) { result ->
            if (result == null) {
                return@observe
            }

            val items = mAdapter.getItems()
            for (index in items.indices) {
                val bean = items[index]
                if (bean.userID == result.userID) {
                    if (result.success) {
                        bean.isInstalledGms = !bean.isInstalledGms
                    }
                    mAdapter.replaceAt( index,bean)
                    break
                }
            }

            hideLoading()

            if (result.success) {
                toast(result.msg)
            } else {
                AlertDialog.Builder(this)
                    .setTitle(R.string.gms_manager)
                    .setMessage(result.msg)
                    .setPositiveButton(R.string.done, null)
                    .show()
            }
        }

        viewModel.getInstalledUser()
    }

    private fun initRecyclerView() {
        mAdapter = RVAdapter<GmsBean>(this, GmsAdapter()).bind(viewBinding.recyclerView)
            .setItemClickListener { view, item, _ ->
                val checkbox = view.findViewById<Switch>(R.id.checkbox)
                if (item.isInstalledGms) {
                    uninstallGms(item.userID, checkbox)
                } else {
                    installGms(item.userID, checkbox)
                }
            }
        viewBinding.recyclerView.layoutManager = LinearLayoutManager(this)

    }

    private fun installGms(userID: Int, checkbox: Switch){
        AlertDialog.Builder(this)
            .setTitle(R.string.enable_gms)
            .setMessage(R.string.enable_gms_hint)
            .setPositiveButton(R.string.done) { _, _ ->
                showLoading()
                viewModel.installGms(userID)
            }
            .setNegativeButton(R.string.cancel) { _, _ ->
                checkbox.isChecked = !checkbox.isChecked
            }
            .show()
    }

    private fun uninstallGms(userID: Int, checkbox: Switch){
        AlertDialog.Builder(this)
            .setTitle(R.string.disable_gms)
            .setMessage(R.string.disable_gms_hint)
            .setPositiveButton(R.string.done) { _, _ ->
                showLoading()
                viewModel.uninstallGms(userID)
            }
            .setNegativeButton(R.string.cancel) { _, _ ->
                checkbox.isChecked = !checkbox.isChecked
            }
            .show()
    }


    companion object{
        fun start(context: Context){
            val intent = Intent(context,GmsManagerActivity::class.java)
            context.startActivity(intent)
        }
    }
}