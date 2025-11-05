import java.io.File
import kotlin.math.ceil

enum class DiscordTier(val size: Double) {
    FREE(9.5),
    BASIC(49.5),
    NITRO(499.5),
}

fun split(fileName: String) {
    val file = File("temp/$fileName")
    val content = file.readBytes()
    val chunkSize = ceil(1024 * 1024 * DiscordTier.FREE.size).toInt()
    var current = 0
    var filePart: File
    var part = 0
    val directory = File("temp/${fileName}_split")
    if (!directory.exists()) {
        directory.mkdir()
    }
    while (current < content.size) {
        filePart = File("$directory/${fileName}.part${part}")
        if (current + chunkSize > content.size) {
            filePart.writeBytes(content.copyOfRange(current, content.size))
        } else {
            filePart.writeBytes(content.copyOfRange(current, current+chunkSize))
        }
        part++
        current+= chunkSize
    }
}

fun combine(dirName: String) {
    val dir = File("temp/$dirName")
    val files = dir.listFiles().sortedBy {
        file -> file.name.split(".")[2].substring(4).toInt()
    }
    val extension = files[0].name.split(".")[1]
    val recombined = File("temp/recombined.$extension")
    if (recombined.exists()) {
        recombined.delete()
    }
    for (file in files) {
        println(file)
        recombined.appendBytes(file.readBytes())
    }
}

fun main(args: Array<String>) {
    split("one.mp4")
    combine("one.mp4_split")
}