package com.example.weatherapp.ui.feature

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.R
import com.example.weatherapp.data.model.DayWeatherInfo
import com.example.weatherapp.data.model.DayWeatherInfoMin
import com.example.weatherapp.data.model.FutureModel
import com.example.weatherapp.data.model.WeatherInfo
import com.example.weatherapp.data.model.WeatherInfoSevenDays
import com.example.weatherapp.data.model.WeatherInfoSixHours
import com.example.weatherapp.ui.theme.BlueSky
import com.example.weatherapp.ui.theme.WeatherAPPTheme
import java.time.format.DateTimeFormatter

@Composable
fun WeatherRoute(
    viewModel: WeatherViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val weatherInfoState by viewModel.weatherInfoState.collectAsStateWithLifecycle()
    val weatherInfoSevenDaysState by viewModel.weatherInfoSevenDaysState.collectAsStateWithLifecycle()
    val weatherInfoSixHoursState by viewModel.weatherInfoSixHoursState.collectAsStateWithLifecycle()
    WeatherScreen(weatherInfo = weatherInfoState.weatherInfo,
        weatherInfoSevenDays = weatherInfoSevenDaysState.weatherInfoSevenDays,
        weatherInfoSixHours = weatherInfoSixHoursState.weatherInfoSixHours,
        onAddItemClick = { }
        )

}

@Composable
fun WeatherDetailItem(icon:Int,value:String,label:String){
    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = icon), contentDescription = null
            ,modifier = Modifier.size(34.dp)
        )
        Text(text =value,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Text(text = label,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun FutureModelViewHolder(hour:String,temp:Float, iconDrawableResId:Int){
    Column(
        modifier = Modifier
            .width(90.dp)
            .wrapContentHeight()
            .padding(4.dp)
            .background(
                color = Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = hour,
            color = Color.White,
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            textAlign = TextAlign.Center
        )

        Image(
            painter = painterResource(
                id = iconDrawableResId,
            ),
            contentDescription = null,
            modifier = Modifier
                .size(45.dp)
                .padding(8.dp),
            contentScale = ContentScale.Crop
        )

        Text(
            text = "${temp}º",
            color = Color.White,
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun FutureItem(item:FutureModel){
    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(text = item.day,
            color=Color.White,
            fontSize = 14.sp
        )

        Image(painter = painterResource(id = item.idPic),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 32.dp)
                .size(45.dp)
        )

        Text(text = item.status,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            color = Color.White,
            fontSize = 14.sp
        )

        Text(text = "${item.highTemp}º",
            modifier = Modifier.padding(end = 16.dp),
            color = Color.White,
            fontSize = 14.sp
        )

        Text(text = "${item.lowTemp}º",
            modifier = Modifier.padding(end = 16.dp),
            color = Color.White,
            fontSize = 14.sp
        )

    }
}

@SuppressLint("DiscouragedApi")
fun getWeatherIconResourceId(context: Context, conditionIcon: String): Int {
    return context.resources.getIdentifier(
        "weather_$conditionIcon",
        "drawable",
        context.packageName
    )
}

@SuppressLint("DiscouragedApi")
@Composable
fun WeatherScreen(
    context: Context = LocalContext.current,
    weatherInfo: WeatherInfo?,
    weatherInfoSevenDays: WeatherInfoSevenDays?,
    weatherInfoSixHours: WeatherInfoSixHours?,
    onAddItemClick: (id: Long?) -> Unit
    ) {
    weatherInfo?.let {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = if(weatherInfo.isDay){
                BlueSky
            } else Color.DarkGray
        ) {
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .clickable { onAddItemClick(null) },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                Text(
                    text = weatherInfo.locationName,
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 20.dp),
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = weatherInfo.dayOfWeek,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(32.dp))

                val iconDrawableResId: Int = context.resources.getIdentifier(
                    "weather_${weatherInfo.conditionIcon}",
                    "drawable",
                    context.packageName
                )

                Image(
                    painter = painterResource(id = iconDrawableResId),
                    contentDescription = null,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = weatherInfo.condition,
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "${weatherInfo.temperature.toString()}º",
                    color = Color.White,
                    style = MaterialTheme.typography.displayLarge
                )

                // Essa Box contem as informações do quadrado cinza
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp)
                        .background(
                            color = Color.LightGray, // Escolha a cor desejada
                            shape = RoundedCornerShape(25.dp)
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement =  Arrangement.SpaceBetween

                    ) {
                        WeatherDetailItem(icon = getWeatherIconResourceId(context, "09n"),value = "${weatherInfo.rain}", label = "Rain")
                        WeatherDetailItem(icon = iconDrawableResId,value = "${weatherInfo.windSpeed} m/s", label = "Vento")
                        WeatherDetailItem(icon = iconDrawableResId,value = "${weatherInfo.humidity}%", label = "Hum.")
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.Center

                ) {
                    weatherInfoSixHours?.hours?.forEachIndexed{ _, dayWeatherInfoMin ->
                        FutureModelViewHolder(hour = dayWeatherInfoMin.hour, temp = dayWeatherInfoMin.maxTemp, iconDrawableResId = getWeatherIconResourceId(context, dayWeatherInfoMin.icon))
                    }
//                    FutureModelViewHolder("07:00", 31, iconDrawableResId)
//                    FutureModelViewHolder("08:00", 30, iconDrawableResId)
//                    FutureModelViewHolder("09:00", 30, iconDrawableResId)
//                    FutureModelViewHolder("10:00", 29, iconDrawableResId)
//                    FutureModelViewHolder("11:00", 28, iconDrawableResId)
//                    FutureModelViewHolder("12:00", 27, iconDrawableResId)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Futuro",
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier.weight(1f)
                    )
                    Text(text = "Próximos 7 dias",
                        fontSize = 14.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                }

                Column(
                    modifier = Modifier.padding(bottom = 20.dp)
                ) {
                    weatherInfoSevenDays?.days?.forEachIndexed { _, dayWeather ->
                        FutureItem(
                            FutureModel(
                                dayWeather.day,  // Dia da semana (sab, dom, seg, ...)
                                getWeatherIconResourceId(context, dayWeather.icon),  // Ícone correspondente
                                dayWeather.description,  // Descrição do tempo (ex: "Céu claro", "Nuvens dispersas", etc.)
                                dayWeather.maxTemp,  // Temperatura máxima
                                dayWeather.minTemp   // Temperatura mínima
                            )
                        )
                    }
                }

            }
        }
    }
}


val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

@Preview
@Composable
private fun WeatherScreenPreview() {
    WeatherAPPTheme {
        WeatherScreen(
            weatherInfo = WeatherInfo(
                locationName = "Uberlândia",
                conditionIcon = "02d",
                condition = "Cloudy",
                temperature = 32,
                dayOfWeek = "Saturday",
                isDay = true,
                humidity = 5,
                windSpeed = 30.2,
                rain = 0.0
            ),
            weatherInfoSevenDays = WeatherInfoSevenDays(
                days = listOf(
                    DayWeatherInfo(
                        day = "Sab",
                        maxTemp = 27.0f,
                        minTemp = 21.0f,
                        icon = "02d",
                        description = "Sol",
                    ),
                    DayWeatherInfo(
                        day = "Dom",
                        maxTemp = 28.0f,
                        minTemp = 22.0f,
                        icon = "02d",
                        description = "Sol",
                    ),
                    DayWeatherInfo(
                        day = "Seg",
                        maxTemp = 29.0f,
                        minTemp = 23.0f,
                        icon = "01d",
                        description = "Sol",
                    ),
                    DayWeatherInfo(
                        day = "Ter",
                        maxTemp = 30.0f,
                        minTemp = 24.0f,
                        icon = "01d",
                        description = "Sol",
                    ),
                    DayWeatherInfo(
                        day = "Qua",
                        maxTemp = 31.0f,
                        minTemp = 25.0f,
                        icon = "01d",
                        description = "Sol",
                    ),
                    DayWeatherInfo(
                        day = "Qui",
                        maxTemp = 32.0f,
                        minTemp = 26.0f,
                        icon = "02d",
                        description = "Parcialmente Nublado",
                    ),
                    DayWeatherInfo(
                        day = "Sex",
                        maxTemp = 33.0f,
                        minTemp = 27.0f,
                        icon = "03d",
                        description = "Nuvens",
                    )
                )

            ),
            weatherInfoSixHours = WeatherInfoSixHours(
                        hours = listOf(
                            DayWeatherInfoMin(
                                hour = "12:00",
                                icon = "02d",
                                maxTemp = 22f
                            ),
                            DayWeatherInfoMin(
                                hour = "12:00",
                                icon = "02d",
                                maxTemp = 22f
                            ),
                            DayWeatherInfoMin(
                                hour = "12:00",
                                icon = "02d",
                                maxTemp = 22f
                            ),
                            DayWeatherInfoMin(
                                hour = "12:00",
                                icon = "02d",
                                maxTemp = 22f
                            ),
                            DayWeatherInfoMin(
                                hour = "12:00",
                                icon = "02d",
                                maxTemp = 22f
                            ),
                            DayWeatherInfoMin(
                                hour = "12:00",
                                icon = "02d",
                                maxTemp = 22f
                            ),
                        )
                    ),

            onAddItemClick = {  },

        )
    }
}

