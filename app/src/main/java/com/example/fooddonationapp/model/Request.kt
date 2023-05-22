package com.example.fooddonationapp.model

data class Request(var id : String, var acceptbyemail:String,var acceptbyname:String,var date:String, var location:String ,var ngoname:String,var quantity:String,var status:String,var time:String,var ngoemail : String,var phoneno : String) {

constructor():this("","","","","","","","","","","")
}