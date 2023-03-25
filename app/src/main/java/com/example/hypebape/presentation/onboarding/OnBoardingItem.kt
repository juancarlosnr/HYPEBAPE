package com.example.hypebape.presentation.onboarding

import com.example.hypebape.R

class OnBoardingItem(
    val title:Int,
    val text:Int,
    val image:Int,
) {

    companion object{

        fun get():List<OnBoardingItem>{
            return listOf(
                OnBoardingItem(R.string.onBoardingTitle1,R.string.onBoardingText1,R.drawable.onboarding1),
                OnBoardingItem(R.string.onBoardingTitle2,R.string.onBoardingText2,R.drawable.onboarding2),
                OnBoardingItem(R.string.onBoardingTitle3,R.string.onBoardingText3,R.drawable.onboarding3),
            )
        }

    }

}