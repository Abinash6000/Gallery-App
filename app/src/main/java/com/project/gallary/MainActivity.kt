package com.project.gallary

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val PERMISSION_REQUEST_CODE = 200
    private var imagePaths: ArrayList<String>? = null
    private var imagesRV: RecyclerView? = null
    private var imageRVAdapter: RecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        imagePaths = arrayListOf()
        imagesRV = findViewById(R.id.idRVImages)

        requestPermissions()

        prepareRecyclerView()

    }

    private fun checkPermission(): Boolean {
        val result: Int = ContextCompat.checkSelfPermission(applicationContext, READ_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        if (checkPermission()) {
            Toast.makeText(this, "Permissions granted..", Toast.LENGTH_SHORT).show()
            getImagePath()
        } else {
            requestPermission()
        }
    }

    private fun requestPermission() {
        val str = arrayOf(READ_EXTERNAL_STORAGE)
        ActivityCompat.requestPermissions(this, str, PERMISSION_REQUEST_CODE)
    }

    private fun prepareRecyclerView() {
        imageRVAdapter = imagePaths?.let { RecyclerViewAdapter(it, this@MainActivity) }
        val manager = GridLayoutManager(this, 4)

        imagesRV?.layoutManager = manager
        imagesRV?.adapter = imageRVAdapter
    }

    private fun getImagePath() {
        val isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)

        if (isSDPresent) {
            val columns = arrayOf(MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID)
            val orderBy = MediaStore.Images.Media._ID
            val cursor: Cursor? = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy)
            val count = cursor?.count
            for (i in 0 until count!!) {
                cursor.moveToPosition(i)
                val dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                imagePaths?.add(cursor.getString(dataColumnIndex))
            }
            imageRVAdapter?.notifyDataSetChanged()
            cursor.close()
        }
    }

    override fun onRequestPermissionsResult(

        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            PERMISSION_REQUEST_CODE ->
                if (grantResults.isNotEmpty()) {
                    val storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    if (storageAccepted) {
                        Toast.makeText(this@MainActivity, "Permissions Granted.....", Toast.LENGTH_SHORT).show()
                        getImagePath()
                    } else {
                        Toast.makeText(this@MainActivity, "Permissions denied, Permissions are required to show your images", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

}