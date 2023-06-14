package org.loghub.loghub.dto

import java.util.UUID

data class AggregationDto(
    val id: String?,
    val name: String,
    val accessor: List<AccessorDto>?
)