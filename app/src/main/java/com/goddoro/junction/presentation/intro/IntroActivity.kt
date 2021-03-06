package com.goddoro.junction.presentation.intro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.*
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.animation.OvershootInterpolator
import com.goddoro.junction.MainActivity
import com.goddoro.junction.databinding.ActivityIntroBinding
import com.goddoro.junction.extensions.disposedBy
import com.goddoro.junction.extensions.rxSingleTimer
import com.goddoro.junction.extensions.startActivity
import com.goddoro.junction.presentation.voice.VoiceActivity
import com.goddoro.junction.util.AppPreference
import com.goddoro.junction.util.setOnDebounceClickListener
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class IntroActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityIntroBinding

    private val mViewModel : IntroViewModel by viewModel()

    private val appPreference : AppPreference by inject()

    private val compositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityIntroBinding.inflate(LayoutInflater.from(this))
        mBinding.lifecycleOwner = this
        mBinding.vm = mViewModel

        setContentView(mBinding.root)

        initView()
        appPreference.isLogin = true

        window.sharedElementEnterTransition = TransitionSet().apply {
            interpolator = OvershootInterpolator(0.7f)
            ordering = TransitionSet.ORDERING_TOGETHER
            addTransition(ChangeBounds().apply{
                pathMotion = ArcMotion()
            })
            addTransition(ChangeTransform())
            addTransition(ChangeClipBounds())
            addTransition(ChangeImageTransform())
        }
    }

    private fun initView() {

        //mBinding.animTaxi.playAnimation()


        rxSingleTimer(3000){
            startActivity(VoiceActivity::class)
            finish()

           //mBinding.txtAnim.text = "zxcvdvzdfdlfkafadslkfjadslfkj"
        }.disposedBy(compositeDisposable)



    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.clear()
    }
}