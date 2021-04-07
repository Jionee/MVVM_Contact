package org.sopt.mvvmpattern.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact")
data class Contact (
    @PrimaryKey(autoGenerate = true) //PK설정, null일 경우 자동 생성되도록
    var id:Long?,
    @ColumnInfo(name = "name")
    var name: String?,
    @ColumnInfo(name = "number")
    var number: String?,
    @ColumnInfo(name = "initial")
    var initial: Char
){
    constructor() : this(null,"","",'\u0000')
}