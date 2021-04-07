package org.sopt.mvvmpattern.Data

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import org.sopt.mvvmpattern.etc.MyApplication

class ContactRepository(application: Application) {
    val CONTACTREPOSITORY = "CONTACTREPOSITORY" //Log message

    //initialize
    private val contactDatabase = ContactDatabase.getInstance(application)!!
    private val contactDao: ContactDao = contactDatabase.contactDao()
    private val contacts: LiveData<List<Contact>> = contactDao.getAll()

    fun getAll(): LiveData<List<Contact>>{
        return contacts
    }

    fun insert(contact:Contact){
        try {
            val thread = Thread(Runnable{
                contactDao.insert(contact)
            })
            thread.start()
        }catch(e:Exception){
            Log.d(CONTACTREPOSITORY,"insert error")
        }
    }

    fun delete(contact:Contact){
        try{
            val thread = Thread(Runnable{
                contactDao.delete(contact)
            })
            thread.start()
        }catch(e: Exception){
            Log.d(CONTACTREPOSITORY,"delete error")
            //Toast.makeText(MyApplication.ApplicationContext(),"ContactRepository : delete error",Toast.LENGTH_SHORT)
        }
    }
}