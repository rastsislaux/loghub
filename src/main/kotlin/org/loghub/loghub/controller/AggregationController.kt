package org.loghub.loghub.controller

import org.loghub.loghub.dto.AccessorDto
import org.loghub.loghub.dto.AggregationDto
import org.loghub.loghub.service.AggregationService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/aggregations")
class AggregationController(
    private val aggregationService: AggregationService
) {

    @PostMapping("/all")
    fun findAll() = aggregationService.findAll()

    @PostMapping
    fun create(@RequestBody dto: AggregationDto) = aggregationService.create(dto)

    @PostMapping("/{id}/accessors")
    fun addAccessor(@PathVariable id: String, @RequestBody dto: AccessorDto) =
        aggregationService.addAccessor(id, dto)

    @GetMapping("/{id}/accessors/{accessorIndex}/logs")
    fun getLogs(@PathVariable id: String,
                @PathVariable accessorIndex: Int): String {
        return aggregationService.getLogs(id, accessorIndex)
    }

}