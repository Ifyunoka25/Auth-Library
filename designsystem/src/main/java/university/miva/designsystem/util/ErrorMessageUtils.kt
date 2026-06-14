package university.miva.designsystem.util

fun cleanErrorMessage(message: String): String =
    message
        .replace(Regex("rpc error: code\\s*=\\s*\\w+\\s*desc\\s*=\\s*"), "")
        .trim()
