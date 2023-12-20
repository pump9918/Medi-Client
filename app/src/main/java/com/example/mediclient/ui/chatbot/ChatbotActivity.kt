package com.example.mediclient.ui.chatbot

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mediclient.BuildConfig
import com.example.mediclient.R
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

class ChatbotActivity : AppCompatActivity() {

    var recycler_view: RecyclerView? = null
    var iv_welcome: ImageView? = null
    var et_msg: EditText? = null
    var btn_send: ImageButton? = null
    var messageList: MutableList<Message>? = null
    var messageAdapter: MessageAdapter? = null
    private lateinit var client: OkHttpClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatbot)
        recycler_view = findViewById<RecyclerView>(R.id.recycler_view)
        iv_welcome = findViewById<ImageView>(R.id.iv_welcome)
        et_msg = findViewById<EditText>(R.id.et_msg)
        btn_send = findViewById<ImageButton>(R.id.btn_send)
        recycler_view?.setHasFixedSize(true)
        val manager = LinearLayoutManager(this)
        manager.stackFromEnd = true
        recycler_view?.setLayoutManager(manager)
        messageList = ArrayList<Message>()
        messageAdapter = MessageAdapter(messageList!!)
        recycler_view?.setAdapter(messageAdapter)
        client = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
        btn_send?.setOnClickListener(
            View.OnClickListener {
                val question = et_msg?.getText().toString().trim { it <= ' ' }
                addToChat(question, Message.SENT_BY_ME)
                et_msg?.setText("")
                callAPI(question)
                iv_welcome?.setVisibility(View.GONE)
            },
        )
    }

    fun addToChat(message: String?, sentBy: String?) {
        runOnUiThread {
            messageList!!.add(Message(message, sentBy))
            messageAdapter!!.notifyDataSetChanged()
            recycler_view!!.smoothScrollToPosition(messageAdapter!!.itemCount)
        }
    }

    fun addResponse(response: String?) {
        messageList!!.removeAt(messageList!!.size - 1)
        addToChat(response, Message.SENT_BY_BOT)
    }

    fun callAPI(question: String) {
        messageList?.add(Message("...", Message.SENT_BY_BOT))

        val arr = JSONArray()
        val baseAi = JSONObject().apply {
            put("role", "user")
            put("content", "You are a helpful and kind AI Assistant.")
        }
        val userMsg = JSONObject().apply {
            put("role", "user")
            put("content", question)
        }
        arr.put(baseAi)
        arr.put(userMsg)

        val jsonObject = JSONObject().apply {
            put("model", "gpt-3.5-turbo")
            put("messages", arr)
        }

        val body = jsonObject.toString().toRequestBody(JSON)
        val request = Request.Builder()
            .url("https://api.openai.com/v1/chat/completions")
            .header("Authorization", "Bearer $MY_SECRET_KEY")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                addResponse("Failed to load response due to ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    try {
                        val jsonResponse = JSONObject(response.body!!.string())
                        val jsonArray = jsonResponse.getJSONArray("choices")
                        val result =
                            jsonArray.getJSONObject(0).getJSONObject("message").getString("content")
                        addResponse(result.trim())
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                } else {
                    addResponse("Failed to load response due to ${response.body!!.string()}")
                }
            }
        })
    }

    companion object {
        val JSON = "application/json; charset=utf-8".toMediaType()
        private val MY_SECRET_KEY = BuildConfig.MY_SECRET_KEY
    }
}
