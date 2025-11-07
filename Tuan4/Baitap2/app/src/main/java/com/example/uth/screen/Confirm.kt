package com.example.uth.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.uth.R
import com.example.uth.ui.theme.Black
import com.example.uth.ui.theme.Bluee
import com.example.uth.ui.theme.GrayLight
import com.example.uth.ui.theme.GrayMedium
import com.example.uth.ui.theme.White
import com.example.uth.viewmodel.ForgetPasswordViewModel

@Composable
fun Confirm(viewModel: ForgetPasswordViewModel, onNext: () -> Unit,
            onBack: () -> Unit){
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Button(
            onClick = onBack,
            colors = ButtonDefaults.buttonColors(
                containerColor = Bluee,
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
            text = "Confirm",
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
            text = "We are here to help you!"
        )
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = viewModel.email,
            onValueChange = { viewModel.email = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(GrayLight),
            shape = RoundedCornerShape(12.dp),
            placeholder = { Text("Your Email") },
            leadingIcon = {
                Icon(
                    modifier = Modifier.padding(5.dp, 10.dp)
                        .size(20.dp),
                    painter = painterResource(id = R.drawable.ic_user),
                    contentDescription = "User Icon",
                    tint = GrayMedium
                )
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = viewModel.verifyCode,
            onValueChange = { viewModel.verifyCode = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(GrayLight),
            shape = RoundedCornerShape(12.dp),
            placeholder = { Text("Your verify code") },
            leadingIcon = {
                Icon(
                    modifier = Modifier.padding(5.dp, 10.dp)
                        .size(20.dp),
                    painter = painterResource(id = R.drawable.ic_mail),
                    contentDescription = "Email Icon",
                    tint = GrayMedium
                )
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = viewModel.newPassword,
            onValueChange = { viewModel.newPassword = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(GrayLight),
            shape = RoundedCornerShape(12.dp),
            placeholder = { Text("Your new password") },
            leadingIcon = {
                Icon(
                    modifier = Modifier.padding(5.dp, 10.dp)
                        .size(20.dp),
                    painter = painterResource(id = R.drawable.ic_lock),
                    contentDescription = "Lock Icon",
                    tint = GrayMedium
                )
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
            onClick = onNext
        ) {
            Text(
                text = "Next",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        }
    }
}