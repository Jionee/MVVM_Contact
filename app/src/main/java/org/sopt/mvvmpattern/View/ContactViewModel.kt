package org.sopt.mvvmpattern.View

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import org.sopt.mvvmpattern.Data.Contact
import org.sopt.mvvmpattern.Data.ContactRepository

class ContactViewModel(application: Application) : AndroidViewModel(application) {
    //application 대신 activity의 context를 쓰게 되면 액티비티 생명주기를 따르기 때문에 application을 인자로 넣어주어 instance를 생성한다.
    private val repository = ContactRepository(application)
    private val contacts = repository.getAll()

    fun getAll(): LiveData<List<Contact>> {
        return this.contacts
    }

    fun insert(contact: Contact){
        repository.insert(contact)
    }

    fun delete(contact: Contact){
        repository.delete(contact)
    }
}