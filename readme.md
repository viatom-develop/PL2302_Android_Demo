/**
* 这个地方能收到数据
*/
* 
* 
```kotlin

    private val readDataLoop = Runnable {
        while (true) {
            readLen1 = mSerialMulti!!.PL2303Read(DeviceIndex1, readBuf1)
            if (readLen1 > 0) {
                mHandler1.post {
                    val data = readBuf1.copyOfRange(0, readLen1)
                    //这个地方能收到数据, data是数据
                }
            }
            delayTime(60)
            if (gThreadStop[DeviceIndex1]) {
                gRunningReadThread[DeviceIndex1] = false
                return@Runnable
            }
        }
    }


```
