package org.loghub.loghub.model

import com.fasterxml.jackson.annotation.JsonProperty

class Accessor(
    val name: String,
    val type: AccessorType,
    val dockerSSHConfig: DockerSSHConfig?,
    val fileSSHConfig: FileSSHConfig?
)

data class DockerSSHConfig(
    val address: String,
    val user: String,

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    val password: String,
    val containerName: String
)

data class FileSSHConfig(
    val address: String,
    val user: String,

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    val password: String,
    val filePath: String
)

enum class AccessorType {
    DOCKER_SSH,
    FILE_SSH,
}