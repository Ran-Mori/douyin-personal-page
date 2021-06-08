package zy.douyinpersonalpage
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_main.*
import zy.douyinpersonalpage.adapter.MyViewPager2Adapter


/**
 * 最重要的MainActivity
 */
class MainActivity : AppCompatActivity() {
    private lateinit var viewPager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * 隐藏最上面的ActionBar
         */
        supportActionBar?.hide()

        /**
         * viewPager2相关设置
         */
        viewPager2 = bottomViewPager
        viewPager2.adapter = MyViewPager2Adapter(this)


        /**
         * 设置viwePager2翻页连带小黄条滑动
         * 逻辑原理是根据page.id和position位置来确定布局
         */
        viewPager2.setPageTransformer { page, position ->
            if (page.id == 2 && Math.abs(position) <= 0.5){
                Log.d("MainActivity","2 - ${position}")
                yellowBarOne.background = resources.getDrawable(R.color.yellow,null)
                yellowBarTwo.background = resources.getDrawable(R.color.background,null)
            }else if (page.id == 3 && Math.abs(position) <= 0.5){
                Log.d("MainActivity","3 - ${position}")
                yellowBarOne.background = resources.getDrawable(R.color.background,null)
                yellowBarTwo.background = resources.getDrawable(R.color.yellow,null)
                yellowBarThree.background = resources.getDrawable(R.color.background,null)
            }else if (page.id == 4 && Math.abs(position) <= 0.5){
                Log.d("MainActivity","4 - ${position}")
                yellowBarThree.background = resources.getDrawable(R.color.yellow,null)
                yellowBarTwo.background = resources.getDrawable(R.color.background,null)
            }
        }
    }
}
