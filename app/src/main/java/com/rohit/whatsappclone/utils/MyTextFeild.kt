package com.rohit.whatsappclone.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp


@Composable
fun MyTextField(
    label: String,
    keyboardCapitalization: KeyboardCapitalization= KeyboardCapitalization.None,
    keyboardTye: KeyboardType= KeyboardType.Text,
    value: String,
    icon: Int? = null,
    visualtransformation: VisualTransformation = VisualTransformation.None,
    onTrailingIconClick: (() -> Unit)?=null,
    onValueChange: (String) -> Unit,
) {

    OutlinedTextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        label = {
            Text(label)
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            capitalization = keyboardCapitalization,
            keyboardType = keyboardTye
        ),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            focusedLabelColor = Color(0xff00ADE1),
            focusedBorderColor = Color(0xff00ADE1),
            unfocusedBorderColor = Color.LightGray
        ),
        trailingIcon =icon?.let {
            {
                Icon(
                    painter = painterResource(it),
                    contentDescription = "",
                    modifier = Modifier.clickable{
                        onTrailingIconClick?.invoke()
                    }
                )
            }
        },
        modifier = Modifier.width(300.dp),
        shape = RoundedCornerShape(12.dp),
        visualTransformation = visualtransformation,
        singleLine = true
    )

}