package zen.me.about.ui.skill

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import zen.me.about.R
import zen.me.about.util.toDp
import zen.me.about.util.toSp
import kotlin.math.PI

class SkillView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attr, defStyle) {

    private companion object {
        const val COLOR_PRIMARY: Int = Color.BLACK
        const val COLOR_ACCENT: Int = Color.BLUE
        const val COLOR_SHADOW: Int = Color.LTGRAY
        const val COLOR_TEXT: Int = Color.WHITE
        const val DEF_TEXT: String = "Skill"
        const val DEF_TEXT_SIZE: Float = 12f
        const val DEF_PROGRESS: Int = 50
        const val DEF_PROGRESS_SIZE: Float = 4f
        const val DEF_SPACING: Float = 8f
        const val DEF_START_ANGLE: Float = 270f  // 12 o'clock
        const val DEF_VIEW_SIZE: Int = 56
    }

    // Dimensions
    private var textSize: Float = DEF_TEXT_SIZE
    private var progressSize: Float = DEF_PROGRESS_SIZE
    private var radius: Float = 0f
    private var shift: Float = 0f

    // Values
    private var text: String
    private var progress: Int
    private var bgColor: Int
    private var progressColor: Int
    private var bgProgress: Int
    private var textColor: Int

    // Tools
    private var paint: Paint
    private var oval: RectF

    init {
        val a: TypedArray = context.theme.obtainStyledAttributes(attr, R.styleable.SkillView, 0, 0)

        try {
            bgColor = a.getColor(R.styleable.SkillView_backgroundColor, COLOR_PRIMARY)
            progressColor = a.getColor(R.styleable.SkillView_progressColor, COLOR_ACCENT)
            bgProgress = a.getColor(R.styleable.SkillView_backgroundProgress, COLOR_SHADOW)
            textColor = a.getColor(R.styleable.SkillView_textColor, COLOR_TEXT)
            text = a.getString(R.styleable.SkillView_text) ?: DEF_TEXT
            textSize = a.getDimension(R.styleable.SkillView_textSize, DEF_TEXT_SIZE.toSp(context))
            progressSize = a.getDimension(R.styleable.SkillView_progressSize, DEF_PROGRESS_SIZE.toDp(context))
            progress = a.getInteger(R.styleable.SkillView_progress, DEF_PROGRESS)
            paint = Paint()
            oval = RectF()
        } finally {
            a.recycle()
        }
    }

    override fun onMeasure(wMeasureSpec: Int, hMeasureSpec: Int) {

        val defSize = DEF_VIEW_SIZE.toDp(context)
        val widthMode = MeasureSpec.getMode(wMeasureSpec)
        val heightMode = MeasureSpec.getMode(hMeasureSpec)

        val minWidth = if (widthMode == MeasureSpec.UNSPECIFIED) defSize else wMeasureSpec
        val minHeight = if (heightMode == MeasureSpec.UNSPECIFIED) defSize else wMeasureSpec

        val size = if (minWidth <= minHeight) minWidth else minHeight
        setMeasuredDimension(size, size)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        shift = 24f - progressSize
        radius = width / 2f
        oval.set(DEF_SPACING, DEF_SPACING, radius * 2 - DEF_SPACING, radius * 2 - DEF_SPACING)
    }

    override fun onDraw(canvas: Canvas) {
        drawBackground(canvas)
        drawProgressBackground(canvas)
        animatedProgress(canvas)
        drawText(canvas)
    }

    private fun drawBackground(canvas: Canvas) {
        paint(Paint.Style.FILL_AND_STROKE)
        paint.color = bgColor
        paint.setShadowLayer(radius / (2 * PI.toFloat()) - shift, 0f, 5f, COLOR_SHADOW)
        canvas.drawCircle(radius, radius, radius - shift, paint)
    }

    private fun drawProgressBackground(canvas: Canvas) {
        paint(Paint.Style.STROKE, progressSize)
        paint.color = bgProgress
        canvas.drawCircle(radius, radius, radius - DEF_SPACING, paint)
    }

    private fun drawProgress(canvas: Canvas, progress: Int) {
        paint(Paint.Style.STROKE, progressSize)
        paint.color = progressColor
        canvas.drawArc(oval, DEF_START_ANGLE, progress.toPercent(), false, paint)
    }

    private fun drawText(canvas: Canvas) {
        paint(Paint.Style.FILL)

        paint.color = textColor
        paint.textAlign = Paint.Align.CENTER
        paint.textSize = textSize

        val offsetY = (paint.descent() + paint.ascent()) / 2
        canvas.drawText(text, radius, radius + (DEF_SPACING) - offsetY, paint)
    }

    private fun paint(paintStyle: Paint.Style, width: Float = 0f) {
        with(paint) {
            reset()
            style = paintStyle
            strokeWidth = width
            isAntiAlias = true
        }
    }

    private var nProgress: Int = 0
    private fun animatedProgress(canvas: Canvas) {
        if (nProgress != progress) {
            drawProgress(canvas, nProgress)
            nProgress++
            invalidate()
        } else drawProgress(canvas, nProgress)
    }

    fun setText(text: String) { this.text = text }

    fun setProgress(count: Int) { this.progress = count }

    // Percent from 360
    private fun Int.toPercent(): Float {
        return when {
            this > 100 -> 360f
            this <= 0 -> 0f
            else -> (this / 100f) * 360f
        }
    }
}