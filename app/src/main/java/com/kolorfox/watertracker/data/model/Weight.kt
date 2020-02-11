package com.kolorfox.watertracker.data.model

class Weight {
    var id:Int=0
    var weight:Float?= null



    constructor(){}

    constructor(id:Int, weight:Float)
    {
        this.id =id
        this.weight = weight


    }
}
