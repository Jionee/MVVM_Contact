package org.sopt.mvvmpattern.Data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
//@Query, @Insert, @Update, @Delete 등의 어노테이션 제공
interface ContactDao {
    //전체 연락처 반환
    @Query("SELECT * FROM contact ORDER BY name ASC")
    fun getAll(): LiveData<List<Contact>>

    @Insert(onConflict = OnConflictStrategy.REPLACE) //onConflict 속성 : 중복 시 처리 방법 (Insert,Update)
    fun insert(contact : Contact)

    @Delete
    fun delete(contact: Contact)
}