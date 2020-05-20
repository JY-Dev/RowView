package shinhan.ac.kr.testcustomview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.LinearLayout.HORIZONTAL
import android.widget.ScrollView
import java.util.*

class RowView : ScrollView {
    private var viewArray = mutableListOf<Viewview>()
    private var CHILD_WEIGHT = 1F
    private var itemMargin = 30
    private var rowCount = 3

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
        val attrsArray = context.theme.obtainStyledAttributes(attrs, R.styleable.RowView, 0, 0)
        rowCount = attrsArray.getInt(R.styleable.RowView_rowCount, 3)
        itemMargin = attrsArray.getInt(R.styleable.RowView_itemMargin, 30)



        setItemRow(rowCount)
        setItemMargin(itemMargin)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }


    private fun init() {
        setWillNotDraw(false)
    }

    private fun refreshView() {
        this.removeAllViews()
        this.requestLayout()
        createRow()
    }

    private fun createRow() {
        if (viewArray.size > 0) {
            val queue = LinkedList<View?>()
            viewArray.forEachIndexed { index, view ->
                queue.add(view.viewCreateFuc())
            }
            val lp = LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            val layout = LinearLayout(context)
            layout.orientation = LinearLayout.VERTICAL
            layout.layoutParams = lp

            for (i in 1..viewArray.size - 1 / rowCount) {
                val liChild = LinearLayout(context)

                liChild.orientation = HORIZONTAL
                liChild.weightSum = rowCount.toFloat()
                liChild.layoutParams = lp
                for (j in 1..rowCount) {
                    if (queue.isEmpty()) {
                        val liLayout = View(context)
                        liChild.addView(createItemView(liLayout, j))
                    } else {
                        liChild.addView(createItemView(queue.poll(), j))
                    }
                }
                layout.addView(liChild)
            }
            this.addView(layout)
        }

    }

    private fun createItemView(view: View?, count: Int): View? {
        val itemView = view
        val lp = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT)
        lp.weight = CHILD_WEIGHT
        lp.marginStart = itemMargin
        if (count % (rowCount) == 0) lp.marginEnd = itemMargin
        itemView?.layoutParams = lp
        return itemView
    }

    fun addItem(view: () -> View) {
        viewArray.add(Viewview(view))
        refreshView()
    }

    fun setItemRow(rc: Int) {
        rowCount = if (rc <= 0) 3
        else rc
        refreshView()
    }


    fun setItemMargin(margin: Int) {
        this.itemMargin = margin
        refreshView()
    }

    fun isItemEmpty(): Boolean {
        return viewArray.size > 0
    }


    class Viewview(val viewCreateFuc: () -> View)
}