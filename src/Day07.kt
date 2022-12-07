private data class File(
    val name: String,
    var fileSize: Long = 0,
    val children: MutableList<File> = mutableListOf()
)

private sealed class Instruction {
    data class MoveIn(val name: String) : Instruction()
    object MoveOut : Instruction()
    object MoveToRoot : Instruction()
    object List : Instruction()
}

fun main() {
    fun String.getInstruction(): Instruction {
        val trimmedInstr = substring(2)
        if (trimmedInstr == "ls") return Instruction.List
        val (_, direction) = substring(2).split(' ')
        return when (direction) {
            ".." -> Instruction.MoveOut
            "/" -> Instruction.MoveToRoot
            else -> Instruction.MoveIn(direction)
        }
    }

    fun buildFileSystem(input: List<String>): File {
        val root = File("/")
        val fileStack = ArrayDeque<File>()
        input.forEach { line ->
            if (line.startsWith('$')) {
                when (val instr = line.getInstruction()) {
                    is Instruction.MoveIn -> {
                        val nextFile = fileStack.last().children.first { it.name == instr.name }
                        fileStack.addLast(nextFile)
                    }

                    Instruction.MoveOut -> {
                        val previousDirectory = fileStack.removeLast()
                        fileStack.last().fileSize += previousDirectory.fileSize
                    }

                    Instruction.MoveToRoot -> {
                        if (fileStack.isNotEmpty()) {
                            var previousDirectory = fileStack.removeLast()
                            while (fileStack.isNotEmpty()) {
                                fileStack.last().fileSize += previousDirectory.fileSize
                                previousDirectory = fileStack.removeLast()
                            }
                        }
                        fileStack.add(root)
                    }

                    Instruction.List -> {
                        // Do nothing, the files will be added in the next lines
                    }
                }
            } else {
                val (sizeOrType, name) = line.split(" ")
                val file = File(name, sizeOrType.toLongOrNull() ?: 0)
                fileStack.last().apply {
                    children.add(file)
                    fileSize += file.fileSize
                }
            }
        }
        if (fileStack.isNotEmpty()) {
            var previousDirectory = fileStack.removeLast()
            while (fileStack.isNotEmpty()) {
                fileStack.last().fileSize += previousDirectory.fileSize
                previousDirectory = fileStack.removeLast()
            }
        }
        return root
    }

    fun getSmallDirectories(file: File, maxSize: Long, files: MutableSet<File>): Set<File> {
        if (file.fileSize <= maxSize && file.children.isNotEmpty()) files.add(file)
        file.children.forEach { child ->
            files.addAll(getSmallDirectories(child, maxSize, files))
        }
        return files
    }

    fun part1(input: List<String>): Long {
        val root = buildFileSystem(input)
        return getSmallDirectories(root, 100000, mutableSetOf())
            .sumOf { it.fileSize }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437L)

    val input = readInput("Day07")
    println(part1(input))
}