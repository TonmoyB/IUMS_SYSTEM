package com.example.iums_system.AdminPart

class ModelPdf {

    //variables
    var uid:String = ""
    var id:String = ""
    var title:String = ""
    var description:String = ""
    //var catagoryId:String = ""
    var url:String = ""
    var timestamp:Long = 0
    //var viewCount:Long = 0
    //var downloadsCount:Long =0


    //empty constructor (required by firebase)
    constructor()



    //parameterized constructor
    constructor(
        uid: String,
        id: String,
        title: String,
        description: String,
        url: String,
        timestamp: Long
    ) {
        this.uid = uid
        this.id = id
        this.title = title
        this.description = description
        this.url = url
        this.timestamp = timestamp
    }

}