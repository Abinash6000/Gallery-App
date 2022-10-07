package com.project.gallary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.ImageView
import com.squareup.picasso.Picasso
import java.io.File

class ImageDetailActivity : AppCompatActivity() {

    lateinit var imgPath: String
    private lateinit var imageView: ImageView
    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private var mScaleFactor = 1.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_detail)

        imgPath = intent.getStringExtra("imgPath").toString()
        imageView = findViewById(R.id.idIVImage)
        scaleGestureDetector = ScaleGestureDetector(this, ScaleListener())
        val imgFile = File(imgPath)
        if (imgFile.exists()) {
            Picasso.get().load(imgFile).placeholder(R.drawable.ic_launcher_background).into(imageView)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        scaleGestureDetector.onTouchEvent(event);
        return true;
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(scaleGestureDetector: ScaleGestureDetector?): Boolean {
            mScaleFactor *= scaleGestureDetector?.scaleFactor ?: 0.0f
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f))
            imageView.setScaleX(mScaleFactor)
            imageView.setScaleY(mScaleFactor)
            return true
        }
    }

}