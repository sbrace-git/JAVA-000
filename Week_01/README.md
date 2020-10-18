1.JVM字节码之整型入栈指令(iconst、bipush、sipush、ldc)
	当int取值 -1~5 采用iconst指令，取值 -128~127 采用bipush指令，取值 -32768~32767 采用sipush指令，取值 -2147483648~2147483647 采用 ldc 指令。

2.jvm启动配置

-Xmx  指定最大堆内存。 如 -Xmx4g. 这只是限制了 Heap 部分的最大值为4g。这个内存不包括栈内存，也不包括堆外使用的内存。建议生产环境最大不超过物理			内存的70%。
-Xms  指定堆内存空间的初始大小。 如 -Xms4g。 而且指定的内存大小，并不是操作系统实际分配的初始值，而是GC先规划好，用到才分配。 专用服务器上需要			保持 –Xms 和 –Xmx 一致，否则应用刚启动可能就有好几个 FullGC。当两者配置不一致时，堆内存扩容可能会导致性能抖动。
-Xmn  等价于 -XX:NewSize，年轻代内存大小,使用 G1 垃圾收集器 不应该 设置该选项，在其他的某些业务场景下可以设置。官方建议设置为 -Xmx 的 1/2 ~ 1/4.
-Xss    设置每个线程栈的字节数。 例如 -Xss1m 指定线程栈为 1MB，与XX:ThreadStackSize=1m 等价

-XX:MetaspaceSize		这个参数是初始化的Metaspace大小，该值越大触发Metaspace GC的时机就越晚。

​			随着GC的到来，虚拟机会根据实际情况调Metaspace的大小，可能增加上线也可能降低。

-XX:MaxMetaspaceSize		这个参数用于限制Metaspace增长的上限，防止因为某些情况导致Metaspace无限的使用本地内存，影响到其他程序。

-XX:MinMetaspaceFreeRatio		当进行过Metaspace GC之后，会计算当前Metaspace的空闲空间比，如果空闲比小于这个参数，那么虚拟机将增长Metaspace的大小。设置该参数可以控制		Metaspace的增长的速度，太小的值会导致Metaspace增长的缓慢，Metaspace的使用逐渐趋于饱和，可能会影响之后类的加载。而太大的值会导致                          Metaspace增长的过快，浪费内存。

-XX:MaxMetasaceFreeRatio
当进行过Metaspace GC之后， 会计算当前Metaspace的空闲空间比，如果空闲比大于这个参数，那么虚拟机会释放Metaspace的部分空间。

-XX:MaxMetaspaceExpansion		Metaspace增长时的最大幅度。

-XX:MinMetaspaceExpansion		Metaspace增长时的最小幅度。

-XX:MaxDirectMemorySize 堆外内存大小