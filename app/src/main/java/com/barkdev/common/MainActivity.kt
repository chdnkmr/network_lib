package com.barkdev.common

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.barkdev.common.data.usecases.UserPosts
import com.barkdev.common.ui.theme.CommonlibraryTheme
import com.barkdev.network.sealed.ResultData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getData()
        setContent {
            CommonlibraryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    when (val response = viewModel.userPostLiveData.observeAsState().value) {
                        is ResultData.Loading -> {
//                            SimpleCircularProgressComponent()
                        }

                        is ResultData.Success -> {
                            val resp = response.data
                            Log.d("Chandan", resp?.first()?.title ?: "")
                            Greeting(name = resp?.first()?.title ?: "")
                        }

                        is ResultData.Failure -> {

                        }

                        else -> {}
                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun SimpleCircularProgressComponent() {
    // CircularProgressIndicator is generally used
    // at the loading screen and it indicates that
    // some progress is going on so please wait.
    Column(
        // we are using column to align our
        // imageview to center of the screen.
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),

        // below line is used for specifying
        // vertical arrangement.
        verticalArrangement = Arrangement.Center,

        // below line is used for specifying
        // horizontal arrangement.
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        // below line is use to display
        // a circular progress bar.
        CircularProgressIndicator(
            // below line is use to add padding
            // to our progress bar.
            modifier = Modifier.padding(16.dp),

            // below line is use to add color
            // to our progress bar.
            color = colorResource(id = R.color.purple_200),

            // below line is use to add stroke
            // width to our progress bar.
            strokeWidth = Dp(value = 4F)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CommonlibraryTheme {
        Greeting("Android")
    }
}