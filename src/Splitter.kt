import java.io.File
import kotlin.math.ceil

fun main(args: Array<String>) {
    split(args[0], args[1].toDouble())
}

fun split(fileName: String, size: Double) {
    val file = File(fileName)
    val content = file.readBytes()
    val chunkSize = ceil(1024 * 1024 * size).toInt()
    var current = 0
    var filePart: File
    var part = 0
    val directory = File("${fileName}_split")
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

