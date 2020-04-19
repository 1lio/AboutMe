package zen.me.about.util

import android.content.Context
import android.util.TypedValue
import android.util.TypedValue.COMPLEX_UNIT_DIP

fun Float.toDp(context: Context) =
    TypedValue.applyDimension(COMPLEX_UNIT_DIP, this, context.resources.displayMetrics)

fun Int.toDp(context: Context): Int =
    TypedValue.applyDimension(COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics).toInt()

fun Float.toSp(context: Context) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this, context.resources.displayMetrics)

