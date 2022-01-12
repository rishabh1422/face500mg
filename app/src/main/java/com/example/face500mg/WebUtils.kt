package com.example.face500mg

import android.content.Context
import org.apache.http.HttpResponse
import org.apache.http.client.ClientProtocolException
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.DefaultHttpClient
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

object WebUtils {
    fun WriteFiletoInternalStorage(context: Context?, str: String?) {}
    fun getPostResponse(
        context: Context?,
        arrayList: ArrayList<Any>,
        jSONObject: JSONObject,
        str: String?,
        str2: String?,
        str3: String?
    ): String {
        var str4 = ""
        val currentTimeMillis = System.currentTimeMillis()
        val defaultHttpClient = DefaultHttpClient()


        val httpPost = HttpPost(str)
        httpPost.setHeader("Content-type", "application/json")
        httpPost.setHeader("Accept", "application/json")
        httpPost.setHeader("Authentication" ,"GUM3LEHQTKN2PPGFU9P2WXYUJFCWBP");
        try {
            val stringEntity = StringEntity(jSONObject.toString(), "utf-8")
            httpPost.setEntity(stringEntity)
            httpPost.setEntity(stringEntity)
            val execute: HttpResponse = defaultHttpClient.execute(httpPost)
            if (execute != null) {
                val content: InputStream = execute.getEntity().getContent()
                str4 = if (content != null) convertInputStreamToString(content) else "Did not work!"
            }
            val currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis
            WriteFiletoInternalStorage(
                context,
                "\n \n----------------------------------------------------------------------------------------------------------------------\n\n"
            )
            val sb = StringBuilder()
            sb.append("\n \nModule Name: ")
            sb.append(str2)
            WriteFiletoInternalStorage(context, sb.toString())
            val sb2 = StringBuilder()
            sb2.append("\n \nPage Name: ")
            sb2.append(str3)
            sb2.append(StringUtils.GB2312)
            WriteFiletoInternalStorage(context, sb2.toString())
            val sb3 = StringBuilder()
            sb3.append("\n \nApi Name:\n")
            sb3.append(str)
            sb3.append("\n \nTime taken: ")
            sb3.append(currentTimeMillis2.toString())
            sb3.append(StringUtils.GB2312)
            WriteFiletoInternalStorage(context, sb3.toString())
            val sb4 = StringBuilder()
            sb4.append("\n \nPost Data Value:\n")
            sb4.append(jSONObject)
            sb4.append(StringUtils.GB2312)
            WriteFiletoInternalStorage(context, sb4.toString())
            val sb5 = StringBuilder()
            sb5.append("\n \nApi Responce \n\n: ")
            sb5.append(str4)
            sb5.append(StringUtils.GB2312)
            WriteFiletoInternalStorage(context, sb5.toString())
        } catch (e: ClientProtocolException) {
            e.printStackTrace()
        } catch (e2: IOException) {
            e2.printStackTrace()
        }
        return str4
    }

    @Throws(IOException::class)
    private fun convertInputStreamToString(inputStream: InputStream): String {
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        var str = ""
        while (true) {
            val readLine = bufferedReader.readLine()
            str = if (readLine != null) {
                val sb = StringBuilder()
                sb.append(str)
                sb.append(readLine)
                sb.toString()
            } else {
                inputStream.close()
                return str
            }
        }
    }
}