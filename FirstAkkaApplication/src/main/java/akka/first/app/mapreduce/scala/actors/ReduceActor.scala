package akka.first.app.mapreduce.scala.actors

import akka.actor.{Actor, actorRef2Scala}
import akka.first.app.mapreduce.scala.{MapData, ReduceData, WordCount}

class ReduceActor extends Actor {
  def receive: Receive = {
    case MapData(dataList) =>
      sender ! reduce(dataList)
  }

  def reduce(words: IndexedSeq[WordCount]): ReduceData = ReduceData {
    words.foldLeft(Map.empty[String, Int]) { (index, words) =>
      if (index contains words.word)
        index + (words.word -> (index.get(words.word).get + 1))
      else
        index + (words.word -> 1)
    }
  }
}
