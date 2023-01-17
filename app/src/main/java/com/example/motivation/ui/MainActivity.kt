package com.example.motivation.ui


import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.example.motivation.infra.MotivationConstants
import com.example.motivation.R
import com.example.motivation.data.Mock
import com.example.motivation.infra.SecurityPreferences
import com.example.motivation.databinding.ActivityMainBinding
import kotlinx.coroutines.selects.select
import java.util.Locale

class MainActivity : AppCompatActivity(), View.OnClickListener {


    private lateinit var binding: ActivityMainBinding
    private var categoryId = MotivationConstants.FILTER.ALL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Hide bar
        supportActionBar?.hide()

        handleUserName()
        handleFilter(R.id.image_all)
        handleNextPhrase(categoryId)
        //Events
        binding.buttonNewPhrase.setOnClickListener(this)
        binding.imageAll.setOnClickListener(this)
        binding.imageHappy.setOnClickListener(this)
        binding.imageSunny.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        if(view?.id == R.id.button_new_phrase){
            handleNextPhrase(categoryId)
        }else if(view?.id in listOf(R.id.image_all, R.id.image_happy, R.id.image_sunny)){
            handleFilter(view?.id)
        }
    }


    private fun handleNextPhrase(category: Int){

        binding.textFreeze.text = Mock().filterByCategoryId(category, Locale.getDefault().language)
    }

    private fun handleFilter(id: Int?){
        disableImage()

        when (id) {
            R.id.image_all -> {
                binding.imageAll.setColorFilter(ContextCompat.getColor(this, R.color.white))
                categoryId = MotivationConstants.FILTER.ALL
            }
            R.id.image_happy -> {
                binding.imageHappy.setColorFilter(ContextCompat.getColor(this, R.color.white))
                categoryId = MotivationConstants.FILTER.HAPPY
            }
            R.id.image_sunny -> {
                binding.imageSunny.setColorFilter(ContextCompat.getColor(this, R.color.white))
                categoryId = MotivationConstants.FILTER.SUNNY
            }
        }

    }

    private fun disableImage(){
        binding.imageAll.setColorFilter(ContextCompat.getColor(this, R.color.dark_purple))
        binding.imageSunny.setColorFilter(ContextCompat.getColor(this, R.color.dark_purple))
        binding.imageHappy.setColorFilter(ContextCompat.getColor(this, R.color.dark_purple))
    }

    private fun handleUserName(){
        val name = SecurityPreferences(this).getString(MotivationConstants.key.USER_NAME)

        binding.textUserName.text = "${getString(R.string.hello)}, $name!"
    }

}