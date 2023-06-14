package org.loghub.loghub.dto

import com.fasterxml.jackson.annotation.JsonInclude
import org.loghub.loghub.model.AccessorType
import org.loghub.loghub.model.DockerSSHConfig
import org.loghub.loghub.model.FileSSHConfig

@JsonInclude(JsonInclude.Include.NON_NULL)
data class AccessorDto(
    val name: String,
    val type: AccessorType,
    val dockerSSHConfig: DockerSSHConfig?,
    val fileSSHConfig: FileSSHConfig?
)