package com.mdasrafulalam.cricwave.adapter

import android.os.Build
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mdasrafulalam.cricwave.R
import com.mdasrafulalam.cricwave.utils.MyConstants
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*



@BindingAdapter("app:setImageResources")
fun setImageResources(imageView: ImageView, imgUrl: String) {
    imgUrl.let {
        Glide.with(imageView.context)
            .load(imgUrl)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading)
            )
            .error(R.drawable.broken_image)
            .into(imageView)
    }
}

@BindingAdapter("app:setImageById")
fun setImageById(imageView: ImageView, id: Int) {
    val team = MyConstants.ALL_TEAMS.value?.firstOrNull { it.id == id }
    team?.let {
        val imgUrl = it.image_path
        Log.d("teamInfo", "countryImageUrl $imgUrl")
        Glide.with(imageView.context)
            .load(imgUrl)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading)
            )
            .error(R.drawable.broken_image)
            .into(imageView)
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("app:setDateAndTime")
fun setDateAndTime(textView: TextView, date:String?){
    if (date!=null){
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")
        val date1 = LocalDateTime.parse(date, formatter)
        // Convert UTC time to Bangladesh time
        val zoneId = ZoneId.systemDefault()
        val bangladeshTime = date1.atZone(ZoneId.of("UTC")).withZoneSameInstant(zoneId)
        val formattedDate = bangladeshTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a"))
        textView.text = formattedDate
    }else{
        textView.text =  textView.context.getString(R.string.not_found)
    }
}

@BindingAdapter("app:setVenuById")
fun setVenuById(textView: TextView, id: Int?) {
    if (MyConstants.ALL_VENUES.value?.isNotEmpty() == true && id!=null){
        val venuname = MyConstants.ALL_VENUES.value?.firstOrNull { it.id == id }!!.name
        val venucity = MyConstants.ALL_VENUES.value?.firstOrNull { it.id == id }!!.city
        val capacity  = MyConstants.ALL_VENUES.value?.firstOrNull { it.id == id }!!.capacity
        val venu = String.format("$venuname, $venucity, \n Capacity: $capacity")
        venu.let {
            Log.d("venuInfo", "venu $venu")
            textView.text = venu
        }
    }else{
        textView.text =  textView.context.getString(R.string.not_found)
    }


}

@BindingAdapter("app:setStageById")
fun setStageById(textView: TextView, id: Int?) {

    if (MyConstants.ALL_STAGES.value?.isNotEmpty() == true && id!=null){
        val stagehas = MyConstants.ALL_STAGES.value?.filter { it.id == id }?.map { it.name }
        if (stagehas != null) {
            if (stagehas.isNotEmpty()){
                val stage = stagehas[0]
                stage.let {
                    Log.d("venuInfo", "venu $stage")
                    textView.text = stage
                }
            }
        }
    }else{
        textView.text = textView.context.getString(R.string.not_found)
    }


}

@BindingAdapter("app:setPlayerPositionById")
fun setPlayerPositionById(textView: TextView, id: Int?) {
    var position = ""
    if (id!=null){
        when(id){
            1-> {
                position = MyConstants.playerPositionArray[1]
                textView.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.bat,0)
            }
            2-> {
                position = MyConstants.playerPositionArray[2]
                textView.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ball,0)
            }
            3-> {
                position = MyConstants.playerPositionArray[3]
                textView.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.gloves,0)
            }
            4-> {
                position = MyConstants.playerPositionArray[4]
                textView.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.allrounder,0)
            }
        }
        Log.d("playerInfo", "playerinfo $position")
        position.let {
            textView.text = position
        }
    }else{
        textView.text = textView.context.getString(R.string.not_found)
    }
}

@BindingAdapter("app:setPlayerNameById")
fun setPlayerNameById(textView: TextView, id: Int) {
    val player = MyConstants.ALL_PLAYERS.value?.firstOrNull { it.id == id }
    val fullname = player?.fullname ?: textView.context.getString(R.string.not_found)
    Log.d("playerInfo", "playerinfo $fullname")
    textView.text = fullname
}

@BindingAdapter("app:setNameCodeById")
fun setNameCodeById(textView: TextView, id: Int?) {
    if (id != null){
        val team = MyConstants.ALL_TEAMS.value?.find { it.id == id }
        if (team != null) {
            val code = team.code
            Log.d("teamInfo", "countryCode $code")
            code.let {
                textView.text = code
            }
        } else {
            Log.e("teamInfo", "No team found with id: $id")
        }
    }else{
        textView.text = textView.context.getString(R.string.not_found)
    }
}


@BindingAdapter("app:setCountryNameById")
fun setCountryNameById(textView: TextView, id: Int?) {
    if (id!=null){
        val countryName = MyConstants.ALL_COUNTRIES.value?.firstOrNull { it.id == id }!!.name
        Log.d("countryInfo", "countryName $countryName")
        countryName.let {
            textView.text = countryName
        }
    }else{
        textView.text = textView.context.getString(R.string.not_found)
    }
}

@BindingAdapter("app:setSeasonNameById")
fun setSeasonNameById(textView: TextView, id: Int?) {
   if (id!=null){
       val seasonName = MyConstants.ALL_SEASONS.value?.firstOrNull { it.id == id }!!.name
       Log.d("seasonInfo", "seasonInfo $seasonName")
       seasonName.let {
           textView.text = seasonName
       }
   }else{
       textView.text = textView.context.getString(R.string.not_found)
   }
}

@BindingAdapter("app:setBookMarkedIcon")
fun setBookMarkedIcon(imageView: ImageView, bookmarked: Boolean) {
    if (bookmarked) {
        imageView.setImageResource(R.drawable.bookmark_img)
    } else {
        imageView.setImageResource(R.drawable.bookmark_grey_img)
    }
}

