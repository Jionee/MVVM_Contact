package org.sopt.mvvmpattern

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.sopt.mvvmpattern.Activity.AddActivity
import org.sopt.mvvmpattern.Adapter.ContactAdapter
import org.sopt.mvvmpattern.Data.Contact
import org.sopt.mvvmpattern.View.ContactViewModel
import org.sopt.mvvmpattern.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var contactViewModel :ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //onClick, onLongClick 두 개의 파라미터 넘기기
        //1. 데이터가 있는 부분을 클릭한 경우
        val adapter = ContactAdapter(
            {contact ->
            val intent = Intent(this, AddActivity::class.java)
            intent.putExtra(AddActivity.EXTRA_CONTACT_NAME,contact.name)
            intent.putExtra(AddActivity.EXTRA_CONTACT_NUMBER, contact.number)
            intent.putExtra(AddActivity.EXTRA_CONTACT_ID, contact.id)
            startActivity(intent)
        },  {contact ->
            deleteDialog(contact)
            })

        val lm = LinearLayoutManager(this)
        main_recycleview.adapter = adapter
        main_recycleview.layoutManager = lm
        main_recycleview.setHasFixedSize(true)


        //뷰모델 객체는 직접 초기화하는 것이 아니라 안드로이드 시스템을 통해 생성해준다. (ViewModelProviders를 통해 get해줌)
        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)
        //Observe로 뷰모델이 어느 액티비티/프래그먼트의 생명주기를 관찰할 것인지 결정함
        //LiveData가 변경되면 자동으로 변경 사항을 파악하고 이를 수행함. UI를 업데이트하면 됨
        contactViewModel.getAll().observe(this, Observer<List<Contact>> {
            contacts ->
            //update UI
            adapter.setContacts(contacts!!)
        })

        //ADD버튼을 클릭한 경우
        main_button.setOnClickListener{
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)error
        }
    }

    private fun deleteDialog(contact: Contact) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Delete selected contact?")
            .setNegativeButton("NO") { _, _ -> }
            .setPositiveButton("YES") { _, _ -> contactViewModel.delete(contact) }
    }
}
