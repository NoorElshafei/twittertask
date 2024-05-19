package com.halan.ui.models

import androidx.annotation.Keep
import java.util.Random

@Keep
open class BaseModel(
  open val nextPage: String? = null,
  open val previousPage: String? = null,
  open val itemId: String = Random().nextInt().toString()
)