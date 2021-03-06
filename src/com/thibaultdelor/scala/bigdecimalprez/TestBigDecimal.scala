package com.thibaultdelor.scala.bigdecimalprez

import scala.util.Random
import java.math.MathContext
import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator
import java.text.SimpleDateFormat

object BenchmarkBigDecimal {

  val ITER: Int = 10000000
  def main(args: Array[String]): Unit = {
    val myBds: Array[BigDecimal] =
      for (i <- Array.range(1, ITER))
        yield BigDecimal(Random.nextDouble, MathContext.DECIMAL32)
    val myDoubles: Array[Double]  = myBds.map(_.toDouble)

    val bdSize = ObjectSizeCalculator.getObjectSize(myBds)
    val doubleSize = ObjectSizeCalculator.getObjectSize(myDoubles)

    println("BigDecimal size : " + humanReadableByteCount(bdSize))
    println("Double size     : " + humanReadableByteCount(doubleSize))
    println("Ratios : " + bdSize.toDouble / doubleSize.toDouble)
    
    val ts1 = System.nanoTime()
    myBds.map(_ * 1000)
    val ts2 = System.nanoTime()
    println("Time to process BigDecimal : "+humanReadableduration(ts1, ts2))
    
    val ts3 = System.nanoTime()
    myDoubles.map(_ * 1000)
    val ts4 = System.nanoTime()
    println("Time to process Double     : "+humanReadableduration(ts3, ts4))
    println("Ratios : " + (ts2-ts1).toDouble / (ts4-ts3).toDouble)
  }

  def humanReadableByteCount(bytes: Long): String = {
    if (bytes < 1024) return bytes + " B";
    val exp = (Math.log(bytes) / Math.log(1024)).toInt

    "%.1f %sB".format(bytes / Math.pow(1024, exp), "KMGTPE".charAt(exp - 1))
  }

  def humanReadableduration(timestampStartInNano: Long,timestampEndInNano: Long): String = {
    val durationInMs = (timestampEndInNano - timestampStartInNano) / (1000 * 1000)
    println(durationInMs)
    "%d:%02d:%02d.%d".format((durationInMs / (1000*60*60)) % 24, durationInMs / (1000*60) % 60, durationInMs / 1000 % 60, durationInMs % 1000)
  }
}

object TestBigDecimal {

  def main(args: Array[String]): Unit = {
    
    println(BigDecimal(123456789, new MathContext(5)) / BigDecimal(10))

    
    println(BigDecimal(123456789, new MathContext(5)))
    println(BigDecimal(123456789l, new MathContext(5)))
    
    println(1d / 3d)
    println(BigDecimal(1) / BigDecimal(3))
    println(new java.math.BigDecimal(1).divide(new java.math.BigDecimal(3)))
  }
}
