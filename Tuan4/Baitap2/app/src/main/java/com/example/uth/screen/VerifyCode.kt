package com.example.uth.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uth.R
import com.example.uth.ui.theme.UTHTheme

import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.ColorFilter
import com.example.uth.ui.theme.Black
import com.example.uth.ui.theme.Blue
import com.example.uth.ui.theme.Bluee
import com.example.uth.ui.theme.GrayMedium
import kotlin.also
import kotlin.collections.all
import kotlin.collections.forEachIndexed
import kotlin.collections.joinToString
import kotlin.collections.toMutableList
import kotlin.text.all
import kotlin.text.isDigit
import kotlin.text.isNotEmpty


@Composable
fun VerifyCode(email: String
               ,onNext: (String) -> Unit,
               onBack: () -> Unit){
    var code by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Button(
            onClick = onBack,
            colors = ButtonDefaults.buttonColors(
                containerColor = Blue,
                contentColor = White
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.align(Alignment.Start)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = "back",
                modifier = Modifier
                    .size(16.dp, 16.dp),
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(White),
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Image(
            painter = painterResource(R.drawable.img_uth),
            contentDescription = "uth",
            modifier = Modifier
                .size(102.dp, 70.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "SmartTasks",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Bluee
        )

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Verify code",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Black
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            color = GrayMedium,
            text = "Enter the code we just sent you on your registered Email"
        )
        Spacer(modifier = Modifier.height(20.dp))

        OtpInputField(
            otpLength = 5,
            onOtpComplete = { code ->
                Log.d("OTP", "User entered: $code")
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(50.dp),
            contentPadding = PaddingValues(
                horizontal = 8.dp,
                vertical = 16.dp
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = Bluee,
                contentColor = White
            ),
            onClick = { onNext(email) }
        ) {
            Text(
                text = "Next",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        }
        Text(email)
    }
}

@Composable
fun OtpInputField(
    otpLength: Int = 5,
    onOtpComplete: (String) -> Unit
) {
    var otp by remember { mutableStateOf(List(otpLength) { "" }) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        otp.forEachIndexed { index, value ->
            OutlinedTextField(
                value = value,
                onValueChange = { input ->
                    if (input.length <= 1 && input.all { it.isDigit() }) {
                        otp = otp.toMutableList().also { it[index] = input }
                        // khi nhập xong hết các ô thì trả kết quả ra ngoài
                        if (otp.all { it.isNotEmpty() }) {
                            onOtpComplete(otp.joinToString(""))
                        }
                    }
                },
                modifier = Modifier
                    .width(56.dp)
                    .height(56.dp),
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                ),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )
        }
    }
}
