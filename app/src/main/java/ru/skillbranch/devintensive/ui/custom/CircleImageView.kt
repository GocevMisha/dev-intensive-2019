package ru.skillbranch.devintensive.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import androidx.annotation.Dimension
import androidx.core.graphics.drawable.toBitmap
import ru.skillbranch.devintensive.R


class CircleImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatImageView(context, attrs, defStyleAttr) {
    companion object {
        private const val DEFAULT_BORDER_COLOR = Color.WHITE
        private const val DEFAULT_BORDER_WIDTH = 2f
    }
    private var borderColor = CircleImageView.DEFAULT_BORDER_COLOR

    private var borderWidth = CircleImageView.DEFAULT_BORDER_WIDTH
    private var image: Bitmap? = null
    private lateinit var paint: Paint
    private lateinit var paintBorder: Paint
    private var viewWidth = 0
    private var viewHeight = 0
    init {
        if(attrs!=null){
            val a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView)
            borderColor = a.getColor(
                R.styleable.CircleImageView_cv_borderColor,
                CircleImageView.DEFAULT_BORDER_COLOR
            )
            borderWidth = a.getDimension(
                R.styleable.CircleImageView_cv_borderWidth,
                CircleImageView.DEFAULT_BORDER_WIDTH)
            a.recycle()
        }
        setup()
    }



    fun setBorderColor(hex:String){
        borderColor = Color.parseColor(hex)
    }
    public fun getBorderColor():Int{
        return borderColor
    }

    @Dimension
    fun getBorderWidth():Int{
        return borderWidth.toInt()
    }

    fun setBorderWidth(@Dimension dp:Int){
        borderWidth = dp.toFloat()

    }
    private fun setup() {
        paint = Paint()
        paint.isAntiAlias = true
        paintBorder = Paint()
        paintBorder.color = borderColor
        paintBorder.isAntiAlias = true
    }


    private fun loadBitmap() {
        image = this.drawable.toBitmap()
    }
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        loadBitmap()
        if (image != null) {
            val shader = BitmapShader(
                Bitmap.createScaledBitmap(
                    image!!,
                    width,
                    height,
                    false
                ), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP
            )
            paint.shader = shader
            val circleCenter: Int = viewWidth / 2
            canvas?.drawCircle(
                circleCenter + borderWidth,
                circleCenter + borderWidth,
                circleCenter + borderWidth - 4.0f,
                paintBorder
            )
            canvas?.drawCircle(
                circleCenter + borderWidth,
                circleCenter + borderWidth,
                circleCenter - 4.0f,
                paint
            )
        }
    }
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width: Int = measureWidth(widthMeasureSpec)
        val height: Int = measureHeight(heightMeasureSpec, widthMeasureSpec)
        viewWidth = width - borderWidth.toInt() * 2
        viewHeight = height - borderWidth.toInt() * 2
        setMeasuredDimension(width, height)
    }
    private fun measureWidth(measureSpec: Int): Int {
        var result = 0
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else {
            // Measure the text
            result = viewWidth
        }
        return result
    }
    private fun measureHeight(measureSpecHeight: Int, measureSpecWidth: Int): Int {
        var result = 0
        val specMode = MeasureSpec.getMode(measureSpecHeight)
        val specSize = MeasureSpec.getSize(measureSpecHeight)
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else {
            result = viewHeight
        }
        return result + 2
    }

}