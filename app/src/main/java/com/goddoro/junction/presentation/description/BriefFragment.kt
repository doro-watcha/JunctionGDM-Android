package com.goddoro.junction.presentation.description

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.goddoro.junction.databinding.FragmentBriefBinding
import java.util.*

class BriefFragment : Fragment(){

    private lateinit var textToSpeech : TextToSpeech

    private lateinit var mBinding : FragmentBriefBinding



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentBriefBinding.inflate(inflater,container,false).also { mBinding = it}.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.lifecycleOwner = viewLifecycleOwner


        textToSpeech = TextToSpeech(context!!) {
            if ( it != TextToSpeech.ERROR) {
                textToSpeech.language =(Locale.ENGLISH)

            }
            textToSpeech.speak(
                    "It will take 13 km to Seoul Station, approximately 35 minutes" +
                    "The estimated fare is KRW 17,000." + "Lets start driving now." ,
                TextToSpeech.QUEUE_FLUSH,null,null)

        }

        mBinding.txtBody.apply {

            text = ""
            setCharacterDelay(65)

            animateText(
                    "It will take 13 km to Seoul Station, approximately 35 minutes.\n \n \n" +
                    "The estimated fare is KRW 17,000.\n \n \n" + "Let's start driving now.")
        }
    }

    companion object {

        fun newInstance() = BriefFragment()
    }
}