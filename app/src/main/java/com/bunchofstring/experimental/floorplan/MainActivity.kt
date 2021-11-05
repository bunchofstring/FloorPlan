package com.bunchofstring.experimental.floorplan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {

    var factor = 1.0f
    lateinit var grid: ViewGroup
    lateinit var marker: View
    lateinit var sgd: ScaleGestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        grid = findViewById(R.id.grid)
        marker = findViewById(R.id.marker)
        sgd = ScaleGestureDetector(this, object: ScaleGestureDetector.OnScaleGestureListener {
            override fun onScale(p0: ScaleGestureDetector): Boolean {
                factor *= p0.scaleFactor
                grid.scaleX = factor
                grid.scaleY = factor
                marker.scaleX = 1/factor
                marker.scaleY = 1/factor
                return true
            }

            override fun onScaleBegin(p0: ScaleGestureDetector?): Boolean {
                return true
            }

            override fun onScaleEnd(p0: ScaleGestureDetector?) {
            }

        })
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return sgd.onTouchEvent(event)
    }
}