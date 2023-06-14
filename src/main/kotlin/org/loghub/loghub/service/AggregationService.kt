package org.loghub.loghub.service

import com.jcraft.jsch.ChannelExec
import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session
import org.loghub.loghub.dto.AccessorDto
import org.loghub.loghub.dto.AggregationDto
import org.loghub.loghub.model.*
import org.loghub.loghub.repository.AggregationRepository
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream

@Service
class AggregationService(
    private val jSch: JSch,
    private val aggregationRepository: AggregationRepository
) {

    // TODO: Proper exception
    fun findById(id: String) = aggregationRepository.findById(id).orElseThrow { IllegalStateException() }

    // TODO: Filters
    fun findAll(): List<Aggregation> = aggregationRepository.findAll()

    // TODO: Validation
    fun create(dto: AggregationDto): Aggregation {
        val aggregation = Aggregation(name = dto.name)
        return aggregationRepository.save(aggregation)
    }

    private fun makeAccessor(dto: AccessorDto): Accessor {
        return Accessor(dto.name, dto.type, dto.dockerSSHConfig, dto.fileSSHConfig)
    }

    fun addAccessor(id: String, dto: AccessorDto): Accessor {
        val aggregation = findById(id)
        val accessor = makeAccessor(dto)
        aggregation.accessors.add(accessor)
        aggregationRepository.save(aggregation)
        return accessor
    }

    private fun getSSHSession(user: String, password: String, address: String, port: Int) =
        jSch.getSession(user, address, port).apply {
            setPassword(password)
            setConfig("StrictHostKeyChecking", "no")
            connect()
        }

    private fun execSSHCmd(user: String, password: String, address: String, port: Int, cmd: String): String {
        var session: Session? = null
        var channel: ChannelExec? = null
        try {
            session = getSSHSession(user, password, address, port)
            channel = session.openChannel("exec") as ChannelExec
            with(channel) {
                setCommand(cmd)
                outputStream = ByteArrayOutputStream()
                connect()
            }
            while (channel.isConnected) { Thread.sleep(100) }
            return channel.outputStream.toString()
        } finally {
            session?.disconnect()
            channel?.disconnect()
        }
    }

    private fun getFileSSHLogs(fileSSHConfig: FileSSHConfig) =
        execSSHCmd(fileSSHConfig.user, fileSSHConfig.password, fileSSHConfig.address, 22,
            "cat ${fileSSHConfig.filePath}")

    private fun getDockerSSHLogs(dockerSSHConfig: DockerSSHConfig) =
        execSSHCmd(dockerSSHConfig.user, dockerSSHConfig.password, dockerSSHConfig.address, 22,
            "docker logs ${dockerSSHConfig.containerName}")

    fun getLogs(id: String, accessorIndex: Int): String {
        val aggregation = findById(id)
        val accessor = aggregation.accessors[accessorIndex]

        return when (accessor.type) {
            AccessorType.DOCKER_SSH -> getDockerSSHLogs(accessor.dockerSSHConfig!!)
            AccessorType.FILE_SSH -> getFileSSHLogs(accessor.fileSSHConfig!!)
        }
    }

}