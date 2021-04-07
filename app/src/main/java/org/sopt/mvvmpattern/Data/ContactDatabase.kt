package org.sopt.mvvmpattern.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//실질적인 데이터베이스 인스턴스를 생성할 Database클래스
@Database(entities = [Contact::class], version = 1) //entity정의, SQLite버전 지정
abstract class ContactDatabase: RoomDatabase() {
    abstract fun contactDao(): ContactDao

    //인스턴스 싱글톤으로 사용하기 위해
    //생성자가 여러 차례 호출되더라도 실제로 생성되는 객체는 하나, 최초 생성 이후 호출된 생성자는 최초 객체를 리턴함
    //companion object : 어떤 클래스의 모든 인스턴스가 공유하는 객체를 만들고 싶을 때, 클래스당 하나만 가질 수 있음(=static)
    companion object{
        private var INSTANCE: ContactDatabase? = null

        fun getInstance(context: Context): ContactDatabase?{
            if(INSTANCE == null){
                synchronized(ContactDatabase::class){ //여러 스레드가 접근하지 못하도록 synchronized 설정
                    INSTANCE = Room.databaseBuilder(context.applicationContext, //인스턴스 생성
                        ContactDatabase::class.java,"contact")
                        .fallbackToDestructiveMigration() //데이터베이스 갱신시 기존 테이블 버리고 새로 사용하도록
                        .build()
                }
            }
            return INSTANCE
        }
    }

}