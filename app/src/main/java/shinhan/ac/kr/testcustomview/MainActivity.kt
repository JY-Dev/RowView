package shinhan.ac.kr.testcustomview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        test_btn02.setOnClickListener {
            row_view.setItemRow(Integer.parseInt(et_01.text.toString()))
            row_view.setItemMargin(Integer.parseInt(et_02.text.toString()))
        }

        test_btn.setOnClickListener {
            row_view.addItem {
                buttonadd()
            }
        }


    }

    fun buttonadd(): View {
        val lp = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val button = Button(this)
        button.setText("난나나")
        button.layoutParams = lp
        button.setOnClickListener {
            Toast.makeText(this, button.id.toString(), Toast.LENGTH_SHORT).show()
        }
        return button
    }

    fun test(){

    }


}
