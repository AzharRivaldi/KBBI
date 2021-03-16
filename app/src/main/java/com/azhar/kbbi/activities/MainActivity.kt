package com.azhar.kbbi.activities

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.azhar.kbbi.R
import com.azhar.kbbi.adapter.MainAdapter
import com.azhar.kbbi.model.ModelMain
import com.azhar.kbbi.networking.ApiEndpoint
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class MainActivity : AppCompatActivity() {

    var mainAdapter: MainAdapter? = null
    var modelMain: MutableList<ModelMain> = ArrayList()
    var listArti: MutableList<ModelMain> = ArrayList()
    var strInputTeks: String = ""
    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressDialog = ProgressDialog(this)
        progressDialog?.setTitle("Mohon Tunggu")
        progressDialog?.setCancelable(false)
        progressDialog?.setMessage("Sedang mencari data...")

        linearHasil.setVisibility(View.GONE)
        rvListTranslation.setLayoutManager(LinearLayoutManager(this))
        rvListTranslation.setHasFixedSize(true)

        btnTranslation.setOnClickListener {
            strInputTeks = teksInput.getText().toString()
            if (strInputTeks.isEmpty()) {
                Toast.makeText(this@MainActivity, "Form tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            } else {
                getDataBrand(strInputTeks)
            }
        }
    }

    private fun getDataBrand(strInputTeks: String) {
        progressDialog?.show()
        modelMain.clear()
        AndroidNetworking.get(ApiEndpoint.BASEURL)
                .addPathParameter("query", strInputTeks)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        progressDialog?.dismiss()
                        try {
                            val jsonObject = response.getJSONObject("data")
                            val jsonArray = jsonObject.getJSONArray("resultLists")
                            for (i in 0 until jsonArray.length()) {
                                val jsonObjectKata = jsonArray.getJSONObject(i)

                                val dataModel = ModelMain()
                                dataModel.strKata = jsonObjectKata.getString("kata")

                                val jsonArrayArti = jsonObjectKata.getJSONArray("arti")
                                for (j in 0 until jsonArrayArti.length()) {
                                    val artiKata = jsonArrayArti[j].toString()
                                    dataModel.strArti = artiKata
                                    listArti.add(dataModel)
                                }

                                modelMain.add(dataModel)
                            }
                            mainAdapter = MainAdapter(modelMain)
                            rvListTranslation.adapter = mainAdapter
                            mainAdapter?.notifyDataSetChanged()

                            linearHasil.visibility = View.VISIBLE
                        } catch (e: JSONException) {
                            Toast.makeText(this@MainActivity, "Oops, gagal menampilkan jenis dokumen.",
                                    Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onError(anError: ANError) {
                        progressDialog?.dismiss()
                        Toast.makeText(this@MainActivity, "Oops! Sepertinya ada masalah dengan koneksi internet kamu.",
                                Toast.LENGTH_SHORT).show()
                    }
                })
    }

}