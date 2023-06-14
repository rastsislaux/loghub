package org.loghub.loghub.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed

class Aggregation(

    @Id
    var id: String? = null,

    @Indexed(unique = true)
    var name: String,

    val accessors: MutableList<Accessor> = mutableListOf()

)