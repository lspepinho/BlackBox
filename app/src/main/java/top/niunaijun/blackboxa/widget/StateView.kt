package top.niunaijun.blackboxa.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import top.niunaijun.blackboxa.R

class StateView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val loadingView: View
    private val emptyView: View
    private var contentView: View? = null

    init {
        loadingView = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            addView(ProgressBar(context).apply {
                layoutParams = LinearLayout.LayoutParams(64.dp, 64.dp)
            })
            addView(TextView(context).apply {
                text = context.getString(R.string.loading)
                textSize = 14f
                gravity = Gravity.CENTER
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply { topMargin = 16.dp }
            })
        }

        emptyView = TextView(context).apply {
            text = context.getString(R.string.empty_empty)
            textSize = 16f
            gravity = Gravity.CENTER
        }

        addView(loadingView, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
        addView(emptyView, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))

        loadingView.visibility = View.GONE
        emptyView.visibility = View.GONE
    }

    private val Int.dp: Int
        get() = (this * resources.displayMetrics.density).toInt()

    fun showLoading() {
        loadingView.visibility = View.VISIBLE
        emptyView.visibility = View.GONE
        contentView?.visibility = View.GONE
    }

    fun showEmpty() {
        loadingView.visibility = View.GONE
        emptyView.visibility = View.VISIBLE
        contentView?.visibility = View.GONE
    }

    fun showContent() {
        loadingView.visibility = View.GONE
        emptyView.visibility = View.GONE
        contentView?.visibility = View.VISIBLE
    }

    override fun addView(child: View?, index: Int, params: android.view.ViewGroup.LayoutParams?) {
        if (child === loadingView || child === emptyView) {
            super.addView(child, index, params)
            return
        }
        if (contentView != null) {
            removeView(contentView)
        }
        contentView = child
        child?.visibility = View.GONE
        super.addView(child, index, params)
    }
}
