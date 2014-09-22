##Common Practice
###Java Programming
######Type Convertion
1. `int to String`

		Integer.toString(int)

		String.valueOf(int)

2. `String to int,float,double`

		Interger.parseInt(String)
		Float.parseFloat(String)
		Double.parseDouble(String)

3. `List<Object> to Object[]`

		List<Object> list = new LinkedList<Object>();//or ArrayList
		Object[] objArray = list.toArray(new Object[list.size()]);

4. int[] to List

		List list = Arrays.asList(ArrayUtils.toObject(array));

		int[] array = {1,2,3,4,5};
		List<Integer> list = new ArrayList<Integer>();
		for(int i: array) {
		  list.add(i);
		}

5. List to Set

		Set<Integer> set = new HashSet<Integer>(list);
		
		Set<Integer> set = new TreeSet<Integer>(aComparator);
		set.addAll(list);


4. Char to String

		Charactar.toString(char)
		String.valueOf(char)

5. String to Date

		String string = "January 2, 2010";
		Date date = new SimpleDateFormat("MMMM d, yyyy", locale.ENGLISH).parse(string);
		System.out.println(date); // Sat Jan 02 00:00:00 BOT 2010

6. List<Integer> to int[]

		Using Common Lang:
		method 1:
		List<Integer> myList;
		......
		int[] intArray = ArrayUtils.toPrimitive(myList.toArray(new Integer[myList.size()]));
		method 2:
		static final Integer[] NO_INTS = new Integer[0];
		......
		int[] intArray2 = ArrayUtils.toPrimitive(myList.toArray(NO_INTS));

		Using Google Guava:
		List<Integer> list = ...
		int[] ints = Ints.toArray(list);

7. String to InputStream
	
		InputStream stream = new ByteArrayInputStream(exampleString.getBytes(StandardCharsets.UTF_8));

		String source = "This is the source of my input stream";
		InputStream in = IOUtils.toInputStream(source, "UTF-8");
		
8. InputStream to String

		StringWriter writer = new StringWriter();
		IOUtils.copy(inputStream, writer, encoding);
		String theString = writer.toString();

		static String convertStreamToString(java.io.InputStream is) {
    	java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
    	return s.hasNext() ? s.next() : "";
		}

9. Random String
	
		import java.util.UUID;
		String uuid = UUID.randomUUID().toString();

10. Split a String with all whitespace characters (' ', '\t', '\n', etc.) as delimiters
	
		myString.split("\\s+");

11. check if a String is a numeric type

method1:

		public static boolean isNumeric(String str)  
		{  
		  try  
		  {  
		    double d = Double.parseDouble(str);  
		  }  
		  catch(NumberFormatException nfe)  
		  {  
		    return false;  
		  }  
		  return true;  
		}

method2:

		public static boolean isNumeric(String str)
		{
		  return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
		}

method3:

		public static boolean isNumeric(String str)
		{
		  NumberFormat formatter = NumberFormat.getInstance();
		  ParsePosition pos = new ParsePosition(0);
		  formatter.parse(str, pos);
		  return str.length() == pos.getIndex();
		}

12. Repeat a string

method1:

		String str = "abc";
		String repeated = StringUtils.repeat(str, 3);
		
		repeated.equals("abcabcabc");

method2:

	repeated = new String(new char[n]).replace("\0", s);

13.

#####Date and Time
1. Date

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        String dbName = "ViewRecord_" + simpleDateFormat.format(calendar.getTime());

日期的加减：

		Calendar currentDay = Calendar.getInstance();
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyyMMdd");

		currentDay.add(Calendar.DATE, -1);

获取当天日期的string:

	String dayStr = new String(sdformat.format(new Date(currentDay.getTimeInMillis())));

#####Concurrecy

1. volatile int vs. AtomicInteger

AtomicInteger由processor保证compare and set，CAS (compare-and-swap) low-level CPU operations

CAS has three operands a memory location V on which to operate, the expected old value A, and the new value B. CAS atomically updates V to the new value B, but only if the value in V matches the expected old value A; otherwise it does nothing. In either case, it returns the value currently in V


#####Lock

ReentrantLock 实现了标准的互斥操作，也就是一次只能有一个线程持有锁，也即所谓独占锁的概念。前面的章节中一直在强调这个特点。显然这个特点在一定程度上面减低了吞吐量，实际上独占锁是一种保守的锁策略，在这种情况下任何“读/读”，“写/读”，“写/写”操作都不能同时发生。但是同样需要强调的一个概念是，锁是有一定的开销的，当并发比较大的时候，锁的开销就比较客观了。所以如果可能的话就尽量少用锁，非要用锁的话就尝试看能否改造为读写锁。

ReadWriteLock 描述的是：一个资源能够被多个读线程访问，或者被一个写线程访问，但是不能同时存在读写线程。也就是说读写锁使用的场合是一个共享资源被大量读取操作，而只有少量的写操作（修改数据）。

ReadLock可以被多个线程持有并且在作用时排斥任何的WriteLock，而WriteLock则是完全的互斥.这一特性最为重要，因为对于高读取频率而相对较低写入的数据结构，使用此类锁同步机制则可以提高并发量。 

ReentrantReadWriteLock与ReentrantLock是单独的实现，彼此之间没有继承或实现的关系。


写线程获取写入锁后可以再次获取读取锁，但是读线程获取读取锁后却不能获取写入锁。

写入锁提供了条件变量(Condition)的支持，这个和独占锁一致，但是读取锁却不允许获取条件变量，将得到一个UnsupportedOperationException异常。

#####Class

自身类.class.isAssignableFrom(自身类或子类.class)  返回true

#####Lock-Free

    ConcurrentLinkedQueue (Java Platform SE 7 ) is wait-free according to the javadoc
    ConcurrentLinkedDeque (Java Platform SE 7 ) is lock-free according to the source
    ConcurrentSkipListMap (Java Platform SE 7 ) is lock-free according to the source
    ConcurrentSkipListSet (Java Platform SE 7 ) is based on the CSLMap and has the same guarantees
    NonBlockingHashMap (Highly Scalable Java) is a lock-free according to the javadoc
    NonBlockingHashSet (Highly Scalable Java) is based on the NBHMap and has the same guarantees
    ConcurrentHashMap (Java Platform SE 7 ) while not fully lock free this should still be called out since it frequently out performs the CSLMap and the NBHMap
    Disruptor by LMAX-Exchange is a framework with a wait-free ring buffer at its core 


###JVM Optimization

####堆大小设置

JVM 中最大堆大小有三方面限制：相关操作系统的数据模型（32-bt还是64-bit）限制；系统的可用虚拟内存限制；系统的可用物理内存限制。32位系统下，一般限制在1.5G~2G；64为操作系统对内存无限制。我在Windows Server 2003 系统，3.5G物理内存，JDK5.0下测试，最大可设置为1478m。

#####典型设置：

* java **-Xmx3550m -Xms3550m -Xmn2g -Xss128k**

**-Xmx3550m**：设置JVM最大可用内存为3550M。

**-Xms3550m**：设置JVM初始内存为3550m。此值可以设置与-Xmx相同，以避免每次垃圾回收完成后JVM重新分配内存。

**-Xmn2g**：设置年轻代大小为2G。**整个JVM内存大小=年轻代大小 + 年老代大小 + 持久代大小**。持久代一般固定大小为64m，所以增大年轻代后，将会减小年老代大小。此值对系统性能影响较大，Sun官方推荐配置为整个堆的3/8。

**-Xss128k**：设置每个线程的堆栈大小。JDK5.0以后每个线程堆栈大小为1M，以前每个线程堆栈大小为256K。更具应用的线程所需内存大小进行调整。在相同物理内存下，减小这个值能生成更多的线程。但是操作系统对一个进程内的线程数还是有限制的，不能无限生成，经验值在3000~5000左右。

* java -Xmx3550m -Xms3550m -Xss128k **-XX:NewRatio=4 -XX:SurvivorRatio=4 -XX:MaxPermSize=16m -XX:MaxTenuringThreshold=0**

**-XX:NewRatio=4**:设置年轻代（包括Eden和两个Survivor区）与年老代的比值（除去持久代）。设置为4，则年轻代与年老代所占比值为1：4，年轻代占整个堆栈的1/5

**-XX:SurvivorRatio=4**：设置年轻代中Eden区与Survivor区的大小比值。设置为4，则两个Survivor区与一个Eden区的比值为2:4，一个Survivor区占整个年轻代的1/6

**-XX:MaxPermSize=16m**:设置持久代大小为16m。

**-XX:MaxTenuringThreshold=0**：设置垃圾最大年龄。**如果设置为0的话，则年轻代对象不经过Survivor区，直接进入年老代**。对于年老代比较多的应用，可以提高效率。**如果将此值设置为一个较大值，则年轻代对象会在Survivor区进行多次复制，这样可以增加对象在年轻代的存活时间**，增加在年轻代即被回收的概率。

####回收器选择

JVM给了三种选择：串行收集器、并行收集器、并发收集器，但是串行收集器只适用于小数据量的情况，所以这里的选择主要针对并行收集器和并发收集器。默认情况下，JDK5.0以前都是使用串行收集器，如果想使用其他收集器需要在启动时加入相应参数。JDK5.0以后，JVM会根据当前系统配置进行判断。

#####吞吐量优先的并行收集器

如上文所述，并行收集器主要以到达一定的吞吐量为目标，适用于科学技术和后台处理等。

######典型配置：

* java -Xmx3800m -Xms3800m -Xmn2g -Xss128k -XX:+UseParallelGC -XX:ParallelGCThreads=20
-XX:+UseParallelGC：选择垃圾收集器为并行收集器。此配置仅对年轻代有效。即上述配置下，年轻代使用并发收集，而年老代仍旧使用串行收集。
-XX:ParallelGCThreads=20：配置并行收集器的线程数，即：同时多少个线程一起进行垃圾回收。此值最好配置与处理器数目相等。

* java -Xmx3550m -Xms3550m -Xmn2g -Xss128k -XX:+UseParallelGC -XX:ParallelGCThreads=20 -XX:+UseParallelOldGC
-XX:+UseParallelOldGC：配置年老代垃圾收集方式为并行收集。JDK6.0支持对年老代并行收集。

* java -Xmx3550m -Xms3550m -Xmn2g -Xss128k -XX:+UseParallelGC  -XX:MaxGCPauseMillis=100
-XX:MaxGCPauseMillis=100:设置每次年轻代垃圾回收的最长时间，如果无法满足此时间，JVM会自动调整年轻代大小，以满足此值。

* java -Xmx3550m -Xms3550m -Xmn2g -Xss128k -XX:+UseParallelGC  -XX:MaxGCPauseMillis=100 -XX:+UseAdaptiveSizePolicy
-XX:+UseAdaptiveSizePolicy：设置此选项后，并行收集器会自动选择年轻代区大小和相应的Survivor区比例，以达到目标系统规定的最低相应时间或者收集频率等，此值建议使用并行收集器时，一直打开。

#####响应时间优先的并发收集器

如上文所述，并发收集器主要是保证系统的响应时间，减少垃圾收集时的停顿时间。适用于应用服务器、电信领域等。

######典型配置：

* java -Xmx3550m -Xms3550m -Xmn2g -Xss128k -XX:ParallelGCThreads=20 -XX:+UseConcMarkSweepGC -XX:+UseParNewGC
-XX:+UseConcMarkSweepGC：设置年老代为并发收集。测试中配置这个以后，-XX:NewRatio=4的配置失效了，原因不明。所以，此时年轻代大小最好用-Xmn设置。
-XX:+UseParNewGC:设置年轻代为并行收集。可与CMS收集同时使用。JDK5.0以上，JVM会根据系统配置自行设置，所以无需再设置此值。

* java -Xmx3550m -Xms3550m -Xmn2g -Xss128k -XX:+UseConcMarkSweepGC -XX:CMSFullGCsBeforeCompaction=5 -XX:+UseCMSCompactAtFullCollection
-XX:CMSFullGCsBeforeCompaction：由于并发收集器不对内存空间进行压缩、整理，所以运行一段时间以后会产生“碎片”，使得运行效率降低。此值设置运行多少次GC以后对内存空间进行压缩、整理。
-XX:+UseCMSCompactAtFullCollection：打开对年老代的压缩。可能会影响性能，但是可以消除碎片

####辅助信息

JVM提供了大量命令行参数，打印信息，供调试使用。主要有以下一些：
-XX:+PrintGC
输出形式：[GC 118250K->113543K(130112K), 0.0094143 secs]
                [Full GC 121376K->10414K(130112K), 0.0650971 secs]
-XX:+PrintGCDetails
输出形式：[GC [DefNew: 8614K->781K(9088K), 0.0123035 secs] 118250K->113543K(130112K), 0.0124633 secs]
                [GC [DefNew: 8614K->8614K(9088K), 0.0000665 secs][Tenured: 112761K->10414K(121024K), 0.0433488 secs] 121376K->10414K(130112K), 0.0436268 secs]
-XX:+PrintGCTimeStamps -XX:+PrintGC：PrintGCTimeStamps可与上面两个混合使用
输出形式：11.851: [GC 98328K->93620K(130112K), 0.0082960 secs]
-XX:+PrintGCApplicationConcurrentTime:打印每次垃圾回收前，程序未中断的执行时间。可与上面混合使用
输出形式：Application time: 0.5291524 seconds
-XX:+PrintGCApplicationStoppedTime：打印垃圾回收期间程序暂停的时间。可与上面混合使用
输出形式：Total time for which application threads were stopped: 0.0468229 seconds
-XX:PrintHeapAtGC:打印GC前后的详细堆栈信息
输出形式：
34.702: [GC {Heap before gc invocations=7:
 def new generation   total 55296K, used 52568K [0x1ebd0000, 0x227d0000, 0x227d0000)
eden space 49152K,  99% used [0x1ebd0000, 0x21bce430, 0x21bd0000)
from space 6144K,  55% used [0x221d0000, 0x22527e10, 0x227d0000)
  to   space 6144K,   0% used [0x21bd0000, 0x21bd0000, 0x221d0000)
 tenured generation   total 69632K, used 2696K [0x227d0000, 0x26bd0000, 0x26bd0000)
the space 69632K,   3% used [0x227d0000, 0x22a720f8, 0x22a72200, 0x26bd0000)
 compacting perm gen  total 8192K, used 2898K [0x26bd0000, 0x273d0000, 0x2abd0000)
   the space 8192K,  35% used [0x26bd0000, 0x26ea4ba8, 0x26ea4c00, 0x273d0000)
    ro space 8192K,  66% used [0x2abd0000, 0x2b12bcc0, 0x2b12be00, 0x2b3d0000)
    rw space 12288K,  46% used [0x2b3d0000, 0x2b972060, 0x2b972200, 0x2bfd0000)
34.735: [DefNew: 52568K->3433K(55296K), 0.0072126 secs] 55264K->6615K(124928K)Heap after gc invocations=8:
 def new generation   total 55296K, used 3433K [0x1ebd0000, 0x227d0000, 0x227d0000)
eden space 49152K,   0% used [0x1ebd0000, 0x1ebd0000, 0x21bd0000)
  from space 6144K,  55% used [0x21bd0000, 0x21f2a5e8, 0x221d0000)
  to   space 6144K,   0% used [0x221d0000, 0x221d0000, 0x227d0000)
 tenured generation   total 69632K, used 3182K [0x227d0000, 0x26bd0000, 0x26bd0000)
the space 69632K,   4% used [0x227d0000, 0x22aeb958, 0x22aeba00, 0x26bd0000)
 compacting perm gen  total 8192K, used 2898K [0x26bd0000, 0x273d0000, 0x2abd0000)
   the space 8192K,  35% used [0x26bd0000, 0x26ea4ba8, 0x26ea4c00, 0x273d0000)
    ro space 8192K,  66% used [0x2abd0000, 0x2b12bcc0, 0x2b12be00, 0x2b3d0000)
    rw space 12288K,  46% used [0x2b3d0000, 0x2b972060, 0x2b972200, 0x2bfd0000)
}
, 0.0757599 secs]
-Xloggc:filename:与上面几个配合使用，把相关日志信息记录到文件以便分析。

####常见配置汇总
堆设置
-Xms:初始堆大小
-Xmx:最大堆大小
-XX:NewSize=n:设置年轻代大小
-XX:NewRatio=n:设置年轻代和年老代的比值。如:为3，表示年轻代与年老代比值为1：3，年轻代占整个年轻代年老代和的1/4
-XX:SurvivorRatio=n:年轻代中Eden区与两个Survivor区的比值。注意Survivor区有两个。如：3，表示Eden：Survivor=3：2，一个Survivor区占整个年轻代的1/5
-XX:MaxPermSize=n:设置持久代大小
收集器设置
-XX:+UseSerialGC:设置串行收集器
-XX:+UseParallelGC:设置并行收集器
-XX:+UseParalledlOldGC:设置并行年老代收集器
-XX:+UseConcMarkSweepGC:设置并发收集器
垃圾回收统计信息
-XX:+PrintGC
-XX:+PrintGCDetails
-XX:+PrintGCTimeStamps
-Xloggc:filename
并行收集器设置
-XX:ParallelGCThreads=n:设置并行收集器收集时使用的CPU数。并行收集线程数。
-XX:MaxGCPauseMillis=n:设置并行收集最大暂停时间
-XX:GCTimeRatio=n:设置垃圾回收时间占程序运行时间的百分比。公式为1/(1+n)
并发收集器设置
-XX:+CMSIncrementalMode:设置为增量模式。适用于单CPU情况。
-XX:ParallelGCThreads=n:设置并发收集器年轻代收集方式为并行收集时，使用的CPU数。并行收集线程数。

###调优总结
年轻代大小选择
响应时间优先的应用：尽可能设大，直到接近系统的最低响应时间限制（根据实际情况选择）。在此种情况下，年轻代收集发生的频率也是最小的。同时，减少到达年老代的对象。
吞吐量优先的应用：尽可能的设置大，可能到达Gbit的程度。因为对响应时间没有要求，垃圾收集可以并行进行，一般适合8CPU以上的应用。
年老代大小选择
响应时间优先的应用：年老代使用并发收集器，所以其大小需要小心设置，一般要考虑并发会话率和会话持续时间等一些参数。如果堆设置小了，可以会造成内存碎片、高回收频率以及应用暂停而使用传统的标记清除方式；如果堆大了，则需要较长的收集时间。最优化的方案，一般需要参考以下数据获得：
并发垃圾收集信息
持久代并发收集次数
传统GC信息
花在年轻代和年老代回收上的时间比例
减少年轻代和年老代花费的时间，一般会提高应用的效率
吞吐量优先的应用：一般吞吐量优先的应用都有一个很大的年轻代和一个较小的年老代。原因是，这样可以尽可能回收掉大部分短期对象，减少中期的对象，而年老代尽存放长期存活对象。
较小堆引起的碎片问题
因为年老代的并发收集器使用标记、清除算法，所以不会对堆进行压缩。当收集器回收时，他会把相邻的空间进行合并，这样可以分配给较大的对象。但是，当堆空间较小时，运行一段时间以后，就会出现“碎片”，如果并发收集器找不到足够的空间，那么并发收集器将会停止，然后使用传统的标记、清除方式进行回收。如果出现“碎片”，可能需要进行如下配置：
-XX:+UseCMSCompactAtFullCollection：使用并发收集器时，开启对年老代的压缩。
-XX:CMSFullGCsBeforeCompaction=0：上面配置开启的情况下，这里设置多少次Full GC后，对年老代进行压缩