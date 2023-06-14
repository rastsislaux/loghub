package org.loghub.loghub.repository

import org.loghub.loghub.model.Aggregation
import org.springframework.data.mongodb.repository.MongoRepository

interface AggregationRepository: MongoRepository<Aggregation, String>