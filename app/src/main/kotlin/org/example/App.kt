/*
 * This source file was generated by the Gradle 'init' task
 */
package org.example

import java.io.FileOutputStream
import java.io.FileReader
import kotlin.io.path.Path

const val VERSION = "2.2"
val ZSHRC_PATH = Path(System.getProperty("user.home"),".zshrc").toAbsolutePath().toString()
//val ZSHRC_PATH = "/Users/dadigua/Desktop/proxy/app/src/main/kotlin/org/example/a.txt"


fun setProxy(port:Int = 7890){
    val httpProxy = "export http_proxy=http://127.0.0.1:$port"
    val httpsProxy = "export https_proxy=http://127.0.0.1:$port"
    val proxies = listOf(httpProxy, httpsProxy)
    FileReader(ZSHRC_PATH).use { it ->
        val r = it.readLines().filter { !it.startsWith("export http") }.toList()
        val res = proxies.plus(r)
        FileOutputStream(ZSHRC_PATH).use {
            it.write(res.joinToString(separator = "\n").toByteArray(Charsets.UTF_8))
            it.close()
        }
        it.close()
    }
    println("add proxies SUCCESS -> \n$httpProxy\n$httpsProxy")

}
fun rm(){
    FileReader(ZSHRC_PATH).use { it ->
        val res = it.readLines().filter { !it.startsWith("export http") }.toList()
        FileOutputStream(ZSHRC_PATH).use {
            it.write(res.joinToString(separator = "\n").toByteArray(Charsets.UTF_8))
            it.close()
        }
        it.close()
    }
}
fun show() {
    println("===========show================")
    FileReader(ZSHRC_PATH).use { it ->
        it.readLines().filter { it.startsWith("export http") }.forEach { println(it.replace("export ", "")) }
        it.close()
    }
    println("===========end================")
}
fun main(args: Array<String>) {
    if(args.isEmpty()) {
        setProxy()
        return
    }
    try {
        when (args[0]){
            "-v" -> {
                println("the version is $VERSION")
            }
            "set" -> {
                if(args.size == 2){
                    setProxy(Integer.parseInt(args[1]))
                }else {
                    setProxy()
                }
            }
            "rm" -> {
                rm()
            }
            "show" -> {
                show()
            }
            else -> {
                listOf(
                    "proxy set [port] --to set proxy",
                    "proxy rm -- to rm proxy",
                    "proxy show -- show current proxy",
                    "proxy to set default 7890"
                ).forEach { println(it) }
            }
        }

    }catch (e:Exception){
        println("ERROR: ${e.message}")
    }
}
