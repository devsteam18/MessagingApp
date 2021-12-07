package com.example.messagingapp.model


open class Chat(
    val message: String,
    val senderId: String,
    val receiverId: String,
    val time:String,
    val date:String

){
    constructor():this("","","","","")
}



