package com.sena.crud.ui.component
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
@Composable fun AppTextField(v:String,onV:(String)->Unit,label:String,singleLine:Boolean=true)=OutlinedTextField(v,onV,label={Text(label)},modifier=Modifier.fillMaxWidth(),singleLine=singleLine)
@Composable fun LoadingButton(text:String,loading:Boolean,onClick:()->Unit)=Button(onClick,enabled=!loading,modifier=Modifier.fillMaxWidth()){if(loading)CircularProgressIndicator(Modifier.size(20.dp),strokeWidth=2.dp)else Text(text)}
