package org.sopt.mvvmpattern.Activity

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_add.*
import org.sopt.mvvmpattern.Data.Contact
import org.sopt.mvvmpattern.View.ContactViewModel
import org.sopt.mvvmpattern.databinding.ActivityAddBinding

class AddActivity :AppCompatActivity(){
    private lateinit var binding:ActivityAddBinding
    private lateinit var contactViewModel: ContactViewModel
    private var id: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //viewModel객체 생성
        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)

        //수정할 경우
        if(intent != null && intent.hasExtra(EXTRA_CONTACT_NAME) && intent.hasExtra(EXTRA_CONTACT_ID) && intent.hasExtra(EXTRA_CONTACT_NUMBER)){
            add_edittext_name.setText(intent.getStringExtra(EXTRA_CONTACT_NAME))
            add_edittext_number.setText(intent.getStringExtra(EXTRA_CONTACT_NUMBER))
            id = intent.getLongExtra(EXTRA_CONTACT_ID, -1)
        }

        add_button.setOnClickListener{
            val name = add_edittext_name.text.toString().trim()
            val number = add_edittext_number.text.toString()

            if(name.isEmpty() || number.isEmpty()){
                Toast.makeText(this, "Please enter name and number.", Toast.LENGTH_SHORT).show()
            } else {
                val initial = name[0].toUpperCase()
                val contact = Contact(id, name, number, initial) //entity 생성해서 insert해줌
                contactViewModel.insert(contact)
                finish()
            }
        }
    }

    companion object {
        const val EXTRA_CONTACT_NAME = "EXTRA_CONTACT_NAME"
        const val EXTRA_CONTACT_NUMBER = "EXTRA_CONTACT_NUMBER"
        const val EXTRA_CONTACT_ID = "EXTRA_CONTACT_ID"
    }
}