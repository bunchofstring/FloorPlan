package com.bunchofstring.experimental.floorplan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.Guideline




class MainActivity : AppCompatActivity() {

    val markers = mutableListOf<View>()
    var factor = 1.0f
    lateinit var grid: ConstraintLayout
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
                markers.forEach {
                    it.scaleX = 1/factor
                    it.scaleY = 1/factor
                }
                return true
            }

            override fun onScaleBegin(p0: ScaleGestureDetector?): Boolean {
                return true
            }

            override fun onScaleEnd(p0: ScaleGestureDetector?) {
            }

        })

        addButton(0.6f, 0.79f)
        addButton(0.1f, 0.19f)
        addButton(0.2f, 0.29f)
        addButton(0.3f, 0.39f)
        addButton(0.4f, 0.49f)
        addButton(0.5f, 0.59f)
        addButton(0.7f, 0.69f)
        addButton(0.8f, 0.89f)
    }

    private fun addButton(x: Float, y: Float){
        val button = Button(this).apply{
            id = View.generateViewId()
            text = "Hello again"
        }
        markers.add(button)
        grid.addView(button)
        positionView(button, x, y)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return sgd.onTouchEvent(event)
    }

    private fun getGuideline(): Guideline{
        return Guideline(this).apply{
            id = View.generateViewId()
        }
    }

    private fun getLayoutParams(percent: Float, orientation: Int): ConstraintLayout.LayoutParams{
        return ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        ).apply{
            this.guidePercent = percent
            this.orientation = orientation
        }
    }

    private fun positionView(widget: View, x: Float, y: Float){
        val guidelineX = getGuideline()
        val layoutParamsX = getLayoutParams(x, ConstraintLayout.LayoutParams.VERTICAL)
        grid.addView(guidelineX, layoutParamsX)

        val guidelineY = getGuideline()
        val layoutParamsY = getLayoutParams(y, ConstraintLayout.LayoutParams.HORIZONTAL)
        grid.addView(guidelineY, layoutParamsY)

        ConstraintSet().apply {
            clone(grid)
            connect(widget.id, ConstraintSet.LEFT, guidelineX.id, ConstraintSet.LEFT)
            connect(widget.id, ConstraintSet.RIGHT, guidelineX.id, ConstraintSet.RIGHT)
            connect(widget.id, ConstraintSet.TOP, guidelineY.id, ConstraintSet.TOP)
            connect(widget.id, ConstraintSet.BOTTOM, guidelineY.id, ConstraintSet.BOTTOM)
            applyTo(grid)
        }
    }
}
