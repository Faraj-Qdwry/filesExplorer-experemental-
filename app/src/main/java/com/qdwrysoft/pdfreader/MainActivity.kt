package com.qdwrysoft.pdfreader

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import com.nbsp.materialfilepicker.MaterialFilePicker
import com.nbsp.materialfilepicker.ui.FilePickerActivity
import java.util.regex.Pattern
import com.itextpdf.text.pdf.parser.PdfTextExtractor
import com.itextpdf.text.pdf.PdfReader


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showChooser()
    }

    private fun showChooser() {
//        val intent = Intent(Intent.ACTION_GET_CONTENT)
//        intent.type = "file/*"
//        startActivityForResult(intent, 100)

        MaterialFilePicker()
                .withActivity(this)
                .withRequestCode(100)
                .withFilter(Pattern.compile(".*\\.pdf$"))
                .start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === 100 && resultCode === Activity.RESULT_OK) {
            val filePath = data?.getStringExtra(FilePickerActivity.RESULT_FILE_PATH)
            loadFromFile(filePath)
            //loadFromFile(data?.data?.path)
        }
    }

    private fun loadFromFile(path: String?) {
        try {
            //var lastPage = ""
            val reader = PdfReader(path)
            val n = reader.numberOfPages
            for (i in 0 until n) {
                val page = PdfTextExtractor.getTextFromPage(reader, i + 1).trim { it <= ' ' } + "\n" //Extracting the content from the different pages
                Log.d("page" , page)
                //lastPage = page
            }
            //textView.text = lastPage
            reader.close()
        } catch (e: Exception) {
            println(e)
        }


    }

}
