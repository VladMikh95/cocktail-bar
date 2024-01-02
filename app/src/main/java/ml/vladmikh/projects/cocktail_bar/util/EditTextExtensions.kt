package ml.vladmikh.projects.cocktail_bar.util

import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData

fun EditText.bindTextTwoWay(liveData: MutableLiveData<String>, lifecycleOwner: LifecycleOwner) {
    this.doOnTextChanged { s, start, count, after ->
        liveData.value = s.toString()
    }

    liveData.observe(lifecycleOwner) { text ->
        //  проверка делается для того, чтобы не провоцировать рекурсию при изменении значения editText на точно такое же
        if (this.text.toString() == text) return@observe

        this.setText(text)
    }
}