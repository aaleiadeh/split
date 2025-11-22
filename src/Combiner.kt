import java.io.File
import kotlin.math.ceil

fun main(args: Array<String>) {
    combine("${args[0]}")
}

fun combine(dirName: String) {
    val dir = File(dirName)
    val files = dir.listFiles().sortedBy {
            file -> file.name.split(".")[2].substring(4).toInt()
    }
    val extension = files[0].name.split(".")[1]
    val recombined = File("recombined.$extension")
    if (recombined.exists()) {
        recombined.delete()
    }
    for (file in files) {
        println(file)
        recombined.appendBytes(file.readBytes())
    }
}
