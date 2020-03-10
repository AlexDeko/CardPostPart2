package com.cardpost

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.cardpost.data.Location
import com.cardpost.data.Video
import java.lang.Character.isLetter
import java.lang.Character.isDigit as isDigit

class MainActivity : AppCompatActivity() {

    private var countLike: TextView? = null
    private var videoPost: ImageButton? = null
    private var isLiked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_item)

        initViews()
        setCountLike()
        setCountCommentAndReply()
        setLike()
        setDate()
        setLocation()
        setVideo()
    }

    private fun setDate() {
        val dateItem: TextView = findViewById(R.id.dateItem)
        val time: Long = System.currentTimeMillis()
        val postTime: Long = 1_583_287_384
        val targetTime = time.minus(postTime)
        dateItem.text = getDateFormat(targetTime)
    }

    private fun setLocation() {
        val location: ImageButton = findViewById(R.id.imageButtonLocation)
        val query = "Москва"
        val coordinate = Location(333.33, 332.33) // для примера работы с координат
        location.setOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse(
                    if (isLetter(query) && isDigit(query) || isLetter(query)) {
                        getString(R.string.geoAddressGM, query)
                    } else getString(R.string.geoCoordinatesGM, coordinate.component1().toString(),
                        coordinate.component2().toString())
                )
            }
            startActivity(intent)
        }
    }

    private fun initViews() {
        countLike = findViewById(R.id.countLikes)
        videoPost = findViewById(R.id.imageButtonVideo)
    }

    private fun setCountLike() {
        if (countLike!!.text.toString() == "0") {
            countLike!!.visibility = View.INVISIBLE
        } else {
            countLike!!.visibility = View.VISIBLE
        }
    }

    private fun setCountCommentAndReply() {


        val countComment: TextView = findViewById(R.id.countComments)
        if (countComment.text.toString() == "0") {
            countComment.visibility = View.INVISIBLE
        } else {
            countComment.visibility = View.VISIBLE
        }

        val countReply: TextView = findViewById(R.id.countReply)
        if (countReply.text.toString() == "0") {
            countReply.visibility = View.INVISIBLE
        } else {
            countReply.visibility = View.VISIBLE
        }
    }

    private fun setVideo(){
        val video = Video(url = "https://www.youtube.com/watch?v=WhWc3b3KhnY")
        if (video.isHas()) {
            videoPost?.setOnClickListener {
                videoPost?.visibility = View.VISIBLE
                val intent = Intent().apply {
                    action = Intent.ACTION_VIEW
                    data = Uri.parse(video.url)
                }
                startActivity(intent)
            }
        } else videoPost?.visibility = View.GONE
    }

    private fun setLike() {
        val like: ImageButton = findViewById(R.id.imageButtonLike)
        var countLikesL: Long = countLike?.text.toString().toLong()
        like.setOnClickListener {
            isLiked = !isLiked
            if (!isLiked) {
                like.setImageResource(R.drawable.ic_favorite_border_24dp)
                countLikesL -= 1
            } else {
                like.setImageResource(R.drawable.ic_favorite_24dp)
                countLikesL += 1
            }
            countLike?.text = countLikesL.toString()
            setCountLike()
        }
    }

    private fun isLetter(query: String): Boolean {
        for (element in query) if (isLetter(element)) {
            return true
        }
        return false
    }

    private fun isDigit(query: String): Boolean {
        for (element in query) if (isDigit(element)) {
            return true
        }
        return false
    }

    private fun getDateFormat(itemTime: Long): String {
        if (itemTime in 60..119L) return "${itemTime / 60} минуту назад"
        if (itemTime in 120L..299L) return "${itemTime / 60} минуты назад"
        if (itemTime in 300L..1_259L) return "${itemTime / 60} минут назад"

        if (itemTime in 1_260L..1_319L) return "${itemTime / 60} минуту назад"
        if (itemTime in 1_320L..1_499L) return "${itemTime / 60} минуты назад"
        if (itemTime in 1_500L..1859L) return "${itemTime / 60} минут назад"

        if (itemTime in 1860L..1919L) return "${itemTime / 60} минуту назад"
        if (itemTime in 1920L..2099L) return "${itemTime / 60} минуты назад"
        if (itemTime in 2100L..2459L) return "${itemTime / 60} минут назад"

        if (itemTime in 2_460L..2519L) return "${itemTime / 60} минуту назад"
        if (itemTime in 2_520L..2699L) return "${itemTime / 60} минуты назад"
        if (itemTime in 2_700L..3059L) return "${itemTime / 60} минут назад"

        if (itemTime in 3_060L..3119L) return "${itemTime / 60} минуту назад"
        if (itemTime in 3_120L..3299L) return "${itemTime / 60} минуты назад"
        if (itemTime in 3_300L..3599L) return "${itemTime / 60} минут назад"

        if (itemTime in 3_600L..7_199L) return "${itemTime / 60} час назад"
        if (itemTime in 7_200L..17_999L) return "${itemTime / 60} часа назад"
        if (itemTime in 18_000L..75_599L) return "${itemTime / 60} часов назад"

        if (itemTime in 76_000L..86_399L) return "несколько часов назад"

        if (itemTime in 86_400L..172_799L) return "${itemTime / 60} день назад"
        if (itemTime in 172_800L..604_799L) return "несколько дней назад"

        if (itemTime in 604_800L..1_209_599L) return "неделю назад"


        if (itemTime >= 1_209_600L) return "Слишком много времени уже прошло"

        return "Меньше минуты назад";
    }
}
