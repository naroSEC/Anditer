package com.playground.anditer

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.TypedValue
import android.view.KeyEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.playground.anditer.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.skydoves.balloon.*

class MainActivity : AppCompatActivity() {

    var initTime = 0L
    private var isFabOpen = true
    lateinit var fabMain: FloatingActionButton
    lateinit var fabHome: FloatingActionButton
    lateinit var fabTip: FloatingActionButton
    lateinit var fabShare: FloatingActionButton

    class ZoomOutPageTransformer : ViewPager2.PageTransformer {
        val MIN_SCALE = 0.85f
        val MIN_ALPHA = 0.5f
        override fun transformPage(view: View, position: Float) {
            view.apply {
                val pageWidth = width
                val pageHeight = height
                when {
                    position < -1 -> {
                        alpha = 0f
                    }
                    position <= 1 -> {
                        val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position))
                        val vertMargin = pageHeight * (1 - scaleFactor) / 2
                        val horzMargin = pageWidth * (1 - scaleFactor) / 2
                        translationX = if (position < 0) {
                            horzMargin - vertMargin / 2
                        } else {
                            horzMargin + vertMargin / 2
                        }

                        scaleX = scaleFactor
                        scaleY = scaleFactor

                        alpha = (MIN_ALPHA +
                                (((scaleFactor - MIN_SCALE) / (1 - MIN_SCALE)) * (1 - MIN_ALPHA)))
                    }
                    else -> {
                        alpha = 0f
                    }
                }
            }
        }
    }

    fun changeFragment(distinction: String, adapterPosition: Int, title: String) {
        val bottomSheetFragment = BottomSheetFagment(applicationContext, distinction, adapterPosition, title)
        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        fabMain = binding.fabadd
        fabHome = binding.home
        fabTip = binding.tip
        fabShare = binding.share
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val balloon = createBalloon(this) {
            setArrowOrientation(ArrowOrientation.TOP)
            setWidthRatio(0.6f)
            setHeight(BalloonSizeSpec.WRAP)
            setText("test")
            setTextColorResource(R.color.white)
            setTextSize(15f)
            setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
            setArrowSize(10)
            setArrowPosition(0.5f)
            setPadding(10)
            setCornerRadius(10f)
            setBackgroundColorResource(R.color.lite_blue)
            setBalloonAnimation(BalloonAnimation.ELASTIC)
            build()
        }

        val adapter = MyFragmentPagerAdapter(this)
        binding.viewpager.adapter = adapter
        binding.viewpager.setPageTransformer(MainActivity.ZoomOutPageTransformer())
        binding.tabs.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.text) {

                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        TabLayoutMediator(binding.tabs, binding.viewpager) { tab, position ->
            when(position) {
                0 -> tab.text = "루팅"
                1 -> tab.text = "디버깅"
                2 -> tab.text = "에뮬레이터"
                3 -> tab.text = "프리다"
                4 -> tab.text = "피닝"
                5 -> tab.text = "무결성"
                6 -> tab.text = "동적 로딩"
                7 -> tab.text = "잠금화면"
                8 -> tab.text = "네이티브"
            }
        }.attach()

        fabMain.setOnClickListener {
            toggleFab(balloon)
        }

        fabHome.setOnClickListener {
            CustomAlert(this).smaliDialog("알림", "Let's Go git?", "Home")
        }

        fabTip.setOnClickListener {
            Toast.makeText(this, "미구현", Toast.LENGTH_SHORT).show()
        }

        fabShare.setOnClickListener {
            Toast.makeText(this, "미구현", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - initTime > 3000) {
                Toast.makeText(this, "죵로하려면 한 번 더 누르세요!", Toast.LENGTH_LONG).show()
                initTime = System.currentTimeMillis()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun toggleFab(balloon: Balloon) {
        val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60f, this.resources.displayMetrics)

        if (isFabOpen) {
            val animation = AnimationUtils.loadAnimation(this, R.anim.floting_rotate_plus)
            fabMain.animation = animation
            fabMain.startAnimation(animation)

            val homeAnimator = ObjectAnimator.ofFloat(fabHome, "translationY", 0f, -px)
            homeAnimator.duration = 400
            homeAnimator.interpolator = OvershootInterpolator()
            homeAnimator.target = fabHome
            homeAnimator.start()

            val tipAnimator = ObjectAnimator.ofFloat(fabTip, "translationY", 0f, -px*2)
            tipAnimator.duration = 400
            tipAnimator.interpolator = OvershootInterpolator()
            tipAnimator.target = fabTip
            tipAnimator.start()

            val shareAnimator = ObjectAnimator.ofFloat(fabShare, "translationY", 0f, -px*3)
            shareAnimator.duration = 400
            shareAnimator.interpolator = OvershootInterpolator()
            shareAnimator.target = fabShare
            shareAnimator.start()

        } else {
            val animation = AnimationUtils.loadAnimation(this, R.anim.floting_rotate_close)
            fabMain.animation = animation
            fabMain.startAnimation(animation)

            val homeAnimator = ObjectAnimator.ofFloat(fabHome, "translationY", -px, 0f)
            homeAnimator.duration = 400
            homeAnimator.interpolator = OvershootInterpolator()
            homeAnimator.target = fabHome
            homeAnimator.start()

            val tipAnimator = ObjectAnimator.ofFloat(fabTip, "translationY", -px*2, 0f)
            tipAnimator.duration = 400
            tipAnimator.interpolator = OvershootInterpolator()
            tipAnimator.target = fabTip
            tipAnimator.start()

            val shareAnimator = ObjectAnimator.ofFloat(fabShare, "translationY", -px*3, 0f)
            shareAnimator.duration = 400
            shareAnimator.interpolator = OvershootInterpolator()
            shareAnimator.target = fabShare
            shareAnimator.start()
        }

        isFabOpen = !isFabOpen
    }
}