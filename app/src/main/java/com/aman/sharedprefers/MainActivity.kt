package com.aman.sharedprefers

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.aman.sharedprefers.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    var namePref = "name"
    var numberPref = "number"
    var colorPref = "color"
    var selectedColor = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(resources.getString(R.string.app_name), MODE_PRIVATE)
        editor = sharedPreferences.edit()

        getSharedPrefs()
        binding.rbRed.setOnCheckedChangeListener { compoundButton, checked ->
            if(checked){
                selectedColor = 1
            }
            switchViewColors()
        }

        binding.rbGreen.setOnCheckedChangeListener { compoundButton, checked ->
            if(checked){
                selectedColor = 2
            }
            switchViewColors()
        }

        binding.rbBlue.setOnCheckedChangeListener { compoundButton, checked ->
            if(checked){
                selectedColor = 3
            }
            switchViewColors()
        }
    }

    fun getSharedPrefs(){
        if(sharedPreferences.contains(namePref)){
            binding.etName.setText(sharedPreferences.getString(namePref, ""))
        }else{
            binding.etName.text.clear()
        }

        if(sharedPreferences.contains(numberPref)){
            binding.etNumber.setText(sharedPreferences.getFloat(numberPref, -1f).toString())
        }else{
            binding.etNumber.text.clear()
        }
        if(sharedPreferences.contains(colorPref)){
            selectedColor = sharedPreferences.getInt(colorPref, 0)
        }else{
            selectedColor = 0
        }
        System.out.println("Selected color in 56 $selectedColor")
        switchViewColors()
    }

    fun save(view: View){
        if(binding.etName.text.toString().isNullOrEmpty()){
            binding.etName.error = resources.getString(R.string.enter_name_to_save)
            binding.etName.requestFocus()
        }else if(binding.etNumber.text.toString().isNullOrEmpty()){
            binding.etNumber.error = resources.getString(R.string.enter_number_to_save)
            binding.etNumber.requestFocus()
        }else if(binding.rgColor.getCheckedRadioButtonId() == -1){
            Toast.makeText(this, resources.getString(R.string.select_color_to_save), Toast.LENGTH_LONG).show()
        }else{
            editor.putString(namePref, binding.etName.text.toString())
            editor.putFloat(numberPref, binding.etNumber.text.toString().toFloat())
            editor.putInt(colorPref,selectedColor)
            editor.commit()
            editor.apply()
            Toast.makeText(this, resources.getString(R.string.preferences_saved), Toast.LENGTH_LONG).show()
        }
    }

    fun clearPref(view:View){
        editor.clear()
        editor.apply()
        Toast.makeText(this, resources.getString(R.string.preferences_cleared), Toast.LENGTH_LONG).show()
        getSharedPrefs()
    }

    fun switchViewColors(){
        System.out.println("selected color $selectedColor")
        when(selectedColor){
            0->{
                binding.view.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                binding.rgColor.clearCheck()
               /* binding.rbRed.isChecked = false
                binding.rbGreen.isChecked = false
                binding.rbBlue.isChecked = false*/
            }
            1->{
                binding.view.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
                binding.rbRed.isChecked = true
            }
            2->{
                binding.view.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
                binding.rbGreen.isChecked = true
            }
            3->{
                binding.view.setBackgroundColor(ContextCompat.getColor(this, R.color.blue))
                binding.rbBlue.isChecked = true
            }
        }
    }
}