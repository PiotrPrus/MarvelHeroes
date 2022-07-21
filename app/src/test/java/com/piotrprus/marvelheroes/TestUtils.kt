package com.piotrprus.marvelheroes

import java.io.*

object TestUtils {
     @Throws(IOException::class)
     fun readFile(fileName: String): String {
          var inputStream: InputStream? = null
          try {
               inputStream =
                    javaClass.classLoader?.getResourceAsStream(fileName)
               val builder = StringBuilder()
               val reader = BufferedReader(InputStreamReader(inputStream))

               var str: String? = reader.readLine()
               while (str != null) {
                    builder.append(str)
                    str = reader.readLine()
               }
               return builder.toString()
          } finally {
               inputStream?.close()
          }
     }
}