package com.rk.libcommons

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class CustomScope : CoroutineScope {
    private val job =
        SupervisorJob() // Use SupervisorJob to avoid cancellation of other coroutines when one
                        // fails
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job + CoroutineName("CustomScope")

    fun cancel() {
        job.cancel()
    }
}
