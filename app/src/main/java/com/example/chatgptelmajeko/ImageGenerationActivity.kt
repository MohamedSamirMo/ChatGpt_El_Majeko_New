package com.example.chatgptelmajeko

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatgptelmajeko.adapter.MessageAdapter
import com.example.chatgptelmajeko.api.ApiUtilities
import com.example.chatgptelmajeko.databinding.ActivityImageGenerationBinding
import com.example.chatgptelmajeko.models.messageModel
import com.example.chatgptelmajeko.models.request.lmageGenerationRequest
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.RequestBody

class ImageGenerationActivity : AppCompatActivity() {

    var list=ArrayList<messageModel>()
    private lateinit var adapter : MessageAdapter
    lateinit var mlayoutmassage: LinearLayoutManager

    private lateinit var binding:ActivityImageGenerationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityImageGenerationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backBtn.setOnClickListener {
            finish()
        }
        mlayoutmassage=LinearLayoutManager(this)
        mlayoutmassage.stackFromEnd=true
        adapter=MessageAdapter(list)
        binding.recyclerView.adapter=adapter
        binding.recyclerView.layoutManager=mlayoutmassage

        binding.sentbtn.setOnClickListener {
            if (binding.userMess.text!!.isEmpty()){
                Toast.makeText(this,"Please ask  your question?", Toast.LENGTH_SHORT).show()
            }else{
                CallApi()
            }
        }

    }

    private fun CallApi() {
        list.add(messageModel(true,false,binding.userMess.text.toString()))
        adapter.notifyItemInserted(list.size - 1)
        binding.recyclerView.recycledViewPool.clear()
        binding.recyclerView.smoothScrollToPosition(list.size-1)


        val apiInterface= ApiUtilities.getApiInterface()
        val requestBody= RequestBody.create(
            MediaType.parse("application/json"),
            Gson().toJson(
                lmageGenerationRequest(
                    1,
                    binding.userMess.text.toString(),
                    "1024x1024"
                )
            )
        )
        val  contentType="application/json"
        val authorization="Bearer ${Utils.API_KEY}"
        lifecycleScope.launch(Dispatchers.IO){
            try {
                val response=apiInterface.generateImage(
                    contentType,authorization,requestBody
                )
                val textResponse=response.data.first().url
                list.add(messageModel(false,true,textResponse))
                withContext(Dispatchers.Main){
                    adapter.notifyItemInserted(list.size - 1)
                    binding.recyclerView.recycledViewPool.clear()
                    binding.recyclerView.smoothScrollToPosition(list.size-1)
                }

                binding.userMess.text!!.clear()
            }catch (e:Exception){
                withContext(Dispatchers.Main){

                    Toast.makeText(this@ImageGenerationActivity,e.message, Toast.LENGTH_SHORT).show()
                }
            }



        }
    }
}