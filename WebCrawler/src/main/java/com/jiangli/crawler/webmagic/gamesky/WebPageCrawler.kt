package com.jiangli.crawler.webmagic.gamesky

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

/**
ZAB 协议是为分布式协调服务ZooKeeper专门设计的一种支持崩溃恢复的一致性协议。基于该协议，ZooKeeper 实现了一种主从模式的系统架构来保持集群中各个副本之间的数据一致性。今天主要看看这个zab协议的工作原理。

一、什么是ZAB协议

话说在分布式系统中一般都要使用主从系统架构模型，指的是一台leader服务器负责外部客户端的写请求。然后其他的都是follower服务器。leader服务器将客户端的写操作数据同步到所有的follower节点中。


就这样，客户端发送来的写请求，全部给Leader，然后leader再转给Follower。这时候需要解决两个问题：

（1）leader服务器是如何把数据更新到所有的Follower的。

（2）Leader服务器突然间失效了，怎么办？

因此ZAB协议为了解决上面两个问题，设计了两种模式：

（1）消息广播模式：把数据更新到所有的Follower

（2）崩溃恢复模式：Leader发生崩溃时，如何恢复

OK。现在带着这两个问题，我们来详细的看一下：

二、ZAB协议工作原理

1、消息广播模式：

如果你了解过2PC协议的话，理解起来就简单很多了，消息广播的过程实际上是一个简化版本的二阶段提交过程。我们来看一下这个过程：

（1）Leader将客户端的request转化成一个Proposal（提议）

（2）Leader为每一个Follower准备了一个FIFO队列，并把Proposal发送到队列上。‘

（3）leader若收到follower的半数以上ACK反馈

（4）Leader向所有的follower发送commit。

其实通俗的理解就比较简单了，我是领导，我要向各位传达指令，不过传达之前我先问一下大家支不支持我，若有一半以上的人支持我，那我就向各位传达指令了。


（1）leader首先把proposal发送到FIFO队列里

（2）FIFO取出队头proposal给Follower

（3）Follower反馈一个ACK给队列

（4）队列把ACK交给leader

（5）leader收到半数以上ACK，就会发送commit指令给FIFO队列

（6）FIFO队列把commit给Follower。

这就是整个消息广播模式。下面我们开始看一下，如果这个leader节点崩溃了，怎么办？也就是第二种模式：崩溃回复模式。

2、崩溃恢复模式

leader就是一个领导，既然领导挂了，整个组织肯定不会散架，毕竟离开谁都能活下去是不是，这时候我们只需要选举一个新的领导即可，而且还要把前leader还未完成的工作做完，也就是说不仅要进行leader服务器选取，而且还要进行崩溃恢复。我们一个一个来解决。

（1）leader服务器选取

话说江湖上有一个神秘组织，这个组织分工明确，各司其职，平时这个组织的成员有三种状态：

looking状态：也就是观望状态，这时候是由于组织出现内部问题，那就停下来，做一些其他的事。

following状态：自身是一个组织成员，做自己的事。

leading状态：自身是一个组织老大，做自己的事。

但是这个组织只有一个老大。突然有一天，老大挂掉了，于是每一个成员的状态变成了looking状态。于是成员宣布要选举新的leader。


既然是选老大，每个人都想做，于是成员ABC开始了公平选举的过程。但是为了方便，每个人都有一个记录表，来记录当前的信息。

第一步：成员A告诉BC说我要成为老大，BC记录下来。（A成员广播）

第二步：B回复可以，C回复不可以。（B成员广播）

第三步：A和C收到B的消息，更新自己的记录表。

此时A：2票，B：0票，C：0票。

第四步：C这时候不满意了，也要选举成为老大。而且还给自己投了一票。

第五步：A回复可以，B回复可以。更新自己的记录表。

第六步：C收到AB的回复，更新。

此时A：0票，B：0，C：3票。于是确定C就是下一届组织老大了。

这就是整个选举的过程。并且每个人的选举，都代表了一个事件，为了保证分布式系统的时间有序性，因此给每一个事件都分配了一个Zxid。相当于编了一个号。32位是按照数字递增，即每次客户端发起一个proposal,低32位的数字简单加1。高32位是leader周期的epoch编号。

每当选举出一个新的leader时，新的leader就从本地事物日志中取出ZXID,然后解析出高32位的epoch编号，进行加1，再将32位的全部设置为0。这样就保证了每次新选举的leader后，保证了ZXID的唯一性而且是保证递增的。


OK，老大选举完了，这时候前老大遗留下来的事还没完成呢，此时就要开始恢复了。

（2）崩溃恢复

既然要恢复，有些场景是不能恢复的，ZAB协议崩溃恢复要求满足如下2个要求： 第一： 确保已经被leader提交的proposal必须最终被所有的follower服务器提交。 第二：确保丢弃已经被leader出的但是没有被提交的proposal。

好了，现在开始进行恢复。

第一步：选取当前取出最大的ZXID，代表当前的事件是最新的。

第二步：新leader把这个事件proposal提交给其他的follower节点

第三步：follower节点会根据leader的消息进行回退或者是数据同步操作。最终目的要保证集群中所有节点的数据副本保持一致。

这就是整个恢复的过程，其实就是相当于有个日志一样的东西，记录每一次操作，然后把出事前的最新操作恢复，然后进行同步即可。

OK。这个就是ZAB协议的整个过程。


最近在学习ZooKeeper，一直想写篇相关博文记录下学习内容，碍于自己是个拖延症重度患者总是停留在准备阶段，直到今天心血来潮突然想干点什么，于是便一不做二不休，通过对《从Paxos到Zookeeper 分布式一致性原理与实践》一书中ZAB相关内容的总结，以及对一些优秀博文的整理码出来这篇简文。本文首先对ZooKeeper进行一个简单的介绍，然后重点介绍ZooKeeper采用的ZAB（ZooKeeper Atomic Broadcast）一致性协议算法，加深自己对ZAB协议的理解的同时也希望与简友们一起分享讨论。

ZooKeeper介绍
ZooKeeper是一个具有高效且可靠的分布式协调服务， 由Yahoo创建的基于Google的Chubby开源实现，并于2010年11月正式成为Apache软件基金会的顶级项目。

ZooKeeper是一个典型的分布式数据一致性解决方案，分布式应用程序可基于它实现诸如数据发布/订阅、负载均衡、命名服务、分布式协调/通知、集群管理、Master选举、分布式锁和分布式队列等功能。

ZooKeeper语义保证
保证如下分布式一致性特性

顺序一致性

从同一个客户端发起的事务请求，最终将会严格地按照其发生顺序被应用到ZooKeeper中。

原子一致性

所有事务请求的处理结果在整个集群中所有机器上的应用情况是一致的，即要么整个集群所有机器都成功应用了某一个事务，要么都没有应用，一定不会出现集群中部分机器应用了该事务，而另外一部分没有应用的情况。

单一视图

无论客户端连接的是哪个ZK服务器，其看到的服务端数据模型都是一致的。

可靠性

一旦服务端成功地应用了一个事务并完成对客户端的响应，那么该事务所引起的服务端状态变更将会被一直保留下来，除非另外一个事务又对其进行了变更。

实时性

通常人们看到实时性的第一反应是，一旦一个事务被成功应用，那么客户端能够立即从服务端上读取到这个事务变更后的最新数据状态。但这个需要注意的是，ZooKeeper仅仅保证在一定时间段内，客户端最终一定能够从服务端上读取到最新的数据状态。

ZooKeeper服务器角色
Leader 一个ZooKeeper集群同一时间只会有一个实际工作的Leader，它会发起并维护与各Follwer及Observer间的心跳。所有的写操作必须要通过Leader完成再由Leader将写操作广播给其它服务器。

Follower 一个ZooKeeper集群可能同时存在多个Follower，它会响应Leader的心跳。Follower可直接处理并返回客户端的读请求，同时会将写请求转发给Leader处理，并且负责在Leader处理写请求时对请求进行投票。

Observer 角色与Follower类似，但是无投票权。

ZAB协议
协议简介
ZooKeeper Atomic Broadcast (ZAB, ZooKeeper原子消息广播协议)是ZooKeeper实现分布式数据一致性的核心算法，ZAB借鉴Paxos算法，但又不像Paxos算法那样，是一种通用的分布式一致性算法，它是一种特别为ZooKeeper专门设计的支持崩溃恢复的原子广播协议。

协议核心
ZAB协议的核心是定义了对于那些会改变ZooKeeper服务器数据状态的事务请求处理方式，即：

所有事务请求必须由一个全局唯一的服务器来协调处理，这样的服务器被称为Leader服务器，而余下的其他服务器称为Follower服务器。Leader服务器负责将一个客户端事务请求转换成一个事务Proposal(提议)，并将该Proposal分发给集群中所有的Follower服务器。之后Leader服务器需要等待所有的Follower服务器的反馈，一旦超过半数的Follower服务器进行了正确的反馈后，那么Leader就会再次向所有的Follower服务器分发Commit消息，要求其将前一个Proposal进提交。

协议阶段划分
ZAB协议整体可划分为两个基本的模式：消息广播和崩溃恢复

按协议原理可细分为四个阶段：选举（Leader Election）、发现（Discovery）、同步（Synchronization）和广播(Broadcast)

按协议实现分为三个时期：选举（Fast Leader Election）、恢复(Recovery Phase)和广播(Broadcast Phase)

消息广播
ZAB协议的消息广播过程使用的是一个原子广播协议，类似于一个二阶段提交过程。针对客户端的事务请求，Leader服务器会为其生成对应的事务Proposal，并将其发送给集群中其余所有的机器，然后在分别收集各自的选票，最后进行事务提交，此处与二阶段提交过程略有不同，ZAB协议的二阶段提交过程中，移除了中断逻辑，所有的Follower服务器要么正常反馈Leader提出的事务Proposal，要么就抛弃Leader服务器。同时，ZAB协议将二阶段提交中的中断逻辑移除意味着我们可以在过半的Follower服务器已经反馈Ack之后就开始提交事务Proposal了，而不需求等待集群中所有的Follower服务器都反馈响应。

然而，在这种简化的二阶段提交模型下，无法处理Leader服务器崩溃退出而带来的数据不一致问题，因此ZAB协议添加了崩溃恢复模式来解决这个问题，另外，整个消息广播协议是基于有FIFO特性的TCP协议来进行网络通信的，因此很容易地保证消息广播过程中消息接收和发送的顺序性。

在整个消息广播过程中，Leader服务器会为每个事务请求生成对应的Proposal来进行广播，并且在广播事务Proposal之前，Leader服务器会首先为这个事务Proposal分配一个全局单调递增的唯一ID，我们称之为事务ID(即ZXID)。由于ZAB协议需要保证每一个消息严格的因果关系，因此必须将每一个事务Proposal按照其ZXID的先后顺序进行排序和处理。

具体的，在消息广播过程中，Leader服务器会为每个Follower服务器都各自分配一个单独的队列，然后将需要广播的事务Proposal依次放入这些队列中取，并且根据FIFO策略进行消息发送。每一个Follower服务器在接收到这个事务Proposal之后，都会首先将其以事务日志的形式写入本地磁盘中，并且成功写入后反馈给Leader服务器一个Ack相应。当Leader服务器接收到过半数Follower的Ack响应后，就会广播一个Commit消息给所有的Follower服务器以通知其进行事务提交，同时Leader自身也会完成对事务的提交，而每个Follower服务器在接收到Commit消息后，也会完成对事务的提交。

崩溃恢复
上面讲解的ZAB协议的这个基于原子广播协议的消息广播过程，在正常运行情况下运行非常良好，但是一旦Leader服务器出现崩溃或者由于网络原因导致Leader服务器失去了与过半Follower的联系，那么就会进入崩溃恢复模式。在ZAB协议中，为了保证程序的正确运行，整个恢复过程结束后需要选举出一个新的Leader服务器。因此，ZAB协议需要一个高效且可靠的Leader选举算法，从而确保能够快速选举出新的Leader。同时，Leader选举算法不仅仅需要让Leader自己知道其自身已经被选举为Leader，同时还需要让集群中的所有其他服务器也快速地感知到选举产生的新的Leader服务器。崩溃恢复主要包括Leader选举和数据恢复两部分，下面将详细讲解Leader选举和数据恢复流程。

Leader选举算法
现有的选举算法有一下四种：基于UDP的LeaderElection、基于UDP的FastLeaderElection、基于UDP和认证的FastLeaderElection和基于TCP的FastLeaderElection，在3.4.10版本中弃用其他三种算法

FastLeaderElection原理
名词解释
myid —— zk服务器唯一ID

zxid ——  最新事务ID

高32位是Leader的epoch，从1开始，每次选出新的Leader，epoch加一；

低32位为该epoch内的序号，每次epoch变化，都将低32位的序号重置；

保证了zkid的全局递增性。

选票数据结构
logicClock 表示这是该服务器发起的第多少轮投票，从1开始计数

state 当前服务器的状态

self_id 当前服务器的唯一ID

self_zxid 当前服务器上所保存的数据的最大事务ID，从0开始计数

vote_id 被推举的服务器的唯一ID

vote_zxid 被推举的服务器上所保存的数据的最大事务ID，从0开始计数

服务器状态
LOOKING 不确定Leader状态。该状态下的服务器认为当前集群中没有Leader，会发起Leader选举。

FOLLOWING 跟随者状态。表明当前服务器角色是Follower，并且它知道Leader是谁。

LEADING 领导者状态。表明当前服务器角色是Leader。

OBSERVING 观察者状态，不参与选举，也不参与集群写操作时的投票。

Leader选举流程
能够确保提交已经被Leader提交的事务Proposal，同时丢弃已经被跳过的事务Proposal。针对这个要求，如果让Leader选举算法能够保证新选举出来的Leader服务器拥有集群中所有机器最高编号(即ZXID最大)的事务Proposal，那么就可以保证这个新选举出来的Leader一定具有所有已经提交的提案。更为重要的是，如果让具有最高编号事务Proposal的机器成为Leader，就可以省去Leader服务器检查Proposal的提交和丢弃工作的这一步操作了。

自增选举轮次
ZooKeeper规定所有有效的投票都必须在同一轮次中。每个服务器在开始新一轮投票时，会先对自己维护的logicClock进行自增操作。

初始化选票
每个服务器在广播自己的选票前，会将自己的投票箱清空。该投票箱记录了所收到的选票。例：服务器2投票给服务器3，服务器3投票给服务器1，则服务器1的投票箱为(2, 3), (3, 1), (1, 1)。票箱中只会记录每一投票者的最后一票，如投票者更新自己的选票，则其它服务器收到该新选票后会在自己票箱中更新该服务器的选票。

发送初始化选票
每个服务器最开始都是通过广播把票投给自己。

接收外部投票
服务器会尝试从其它服务器获取投票，并记入自己的投票箱内。如果无法获取任何外部投票，则会确认自己是否与集群中其它服务器保持着有效连接。如果是，则再次发送自己的投票；如果否，则马上与之建立连接。

更新选票
根据选票logicClock -> vote_zxid -> vote_id依次判断

1 判断选举轮次

收到外部投票后，首先会根据投票信息中所包含的logicClock来进行不同处理：

外部投票的logicClock > 自己的logicClock:  说明该服务器的选举轮次落后于其它服务器的选举轮次，立即清空自己的投票箱并将自己的logicClock更新为收到的logicClock，然后再对比自己之前的投票与收到的投票以确定是否需要变更自己的投票，最终再次将自己的投票广播出去;

外部投票的logicClock < 自己的logicClock: 当前服务器直接忽略该投票，继续处理下一个投票;

外部投票的logickClock = 自己的: 当时进行选票PK。

2 选票PK

选票PK是基于(self_id, self_zxid)与(vote_id, vote_zxid)的对比：

若logicClock一致，则对比二者的vote_zxid，若外部投票的vote_zxid比较大，则将自己的票中的vote_zxid与vote_myid更新为收到的票中的vote_zxid与vote_myid并广播出去，另外将收到的票及自己更新后的票放入自己的票箱。如果票箱内已存在(self_myid, self_zxid)相同的选票，则直接覆盖

若二者vote_zxid一致，则比较二者的vote_myid，若外部投票的vote_myid比较大，则将自己的票中的vote_myid更新为收到的票中的vote_myid并广播出去，另外将收到的票及自己更新后的票放入自己的票箱

统计选票
如果已经确定有过半服务器认可了自己的投票（可能是更新后的投票），则终止投票。否则继续接收其它服务器的投票。

更新服务器状态
投票终止后，服务器开始更新自身状态。若过半的票投给了自己，则将自己的服务器状态更新为LEADING，否则将自己的状态更新为FOLLOWING。

图示Leader选举流程


选票PK流程
注：图中箭头上的(1,1,0) 三个数一次代表该选票的服务器的LogicClock、被推荐的服务器的myid (即vote_myid) 以及被推荐的服务器的最大事务ID(即vote_zxid)；

(1, 1) 2个数分别代表投票服务器myid(即self_myid)和被推荐的服务器的myid (即vote_myid)

选举流程如下:

自增选票轮次&初始化选票&发送初始化选票

首先，三台服务器自增选举轮次将LogicClock=1；然后初始化选票，清空票箱；最后发起初始化投票给自己将各自的票通过广播的形式投个自己并保存在自己的票箱里。

接受外部投票&更新选票

以Server 1 为例，分别经历 Server 1 PK Server 2 和 Server 1 PK Server 3 过程

Server 1 PK Server 2

Server 1 接收到Server 2的选票(1,2,0) 表示，投票轮次LogicClock为1，投给Server 2，并且Server 2的最大事务ID，ZXID 为0；

这时Server 1将自身的选票轮次和Server 2 的选票轮次比较，发现LogicClock=1相等，接着Server 2比较比较最大事务ID，发现也zxid=0也相等，最后比较各自的myid，发现Server 2的myid=2 大于自己的myid=1；

根据选票PK规则，Server 1将自己的选票由 (1, 1) 更正为 (1, 2)，表示选举Server 2为Leader，然后将自己的新选票 (1, 2)广播给 Server 2 和 Server 3，同时更新票箱子中自己的选票并保存Server 2的选票，至此Server 1票箱中的选票为(1, 2) 和 (2, 2)；

Server 2收到Server 1的选票同样经过轮次比较和选票PK后确认自己的选票保持不变，并更新票箱中Server 1的选票由(1, 1)更新为(1, 2)，注意此次Server 2自己的选票并没有改变所有不用对外广播自己的选票。

Server 1 PK Server 3

更加Server 1 PK Server 2的流程类推，Server 1自己的选票由(1, 2)更新为(1, 3), 同样更新自己的票箱并广播给Server 2 和 Server 3；

Server 2再次接收到Server 1的选票(1, 3)时经过比较后根据规则也要将自己的选票从(1, 2)更新为(1, 3), 并更新票箱里自己的选票和Server 1的选票，同时向Server 1和 Server 3广播；

同理 Server 2 和 Server 3也会经历上述投票过程，依次类推，Server 1 、Server 2 和Server 3 在俩俩之间在经历多次选举轮次比较和选票PK后最终确定各自的选票。

更新服务器状态
更新服务器状态
选票确定后服务器根据自己票箱中的选票确定各自的角色和状态，票箱中超过半数的选票投给自己的则为Leader，更新自己的状态为LEADING，否则为Follower角色，状态为FOLLOWING，

成为Leader的服务器要主动向Follower发送心跳包，Follower做出ACK回应，以维持他们之间的长连接。

数据同步(待完善)
Commit过的数据不丢失
丢弃未Commit过的数据

作者：Lexus90511
链接：https://www.jianshu.com/p/3fec1f8bfc5f
来源：简书
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

 */
fun main(args: Array<String>) {
    val keyword = "java"
    val URL = "https://baike.baidu.com/item/$keyword"
    val document = Jsoup.connect(URL).get()
//    println(document)
    val select = document.select(".content")
//    println(select)

//    println(select.html())
//    println(select.text())
//    select.eachText().forEach {
//        println(it)
//    }

    printEle(select," ")
}


fun printEle(list: Elements,prefix:String) {
    list.forEach {
        printEle(it, prefix)
    }
}
fun printEle(one: Element, prefix:String) {
    val trim = one.ownText().trim()
    if (trim.isNotEmpty()) {
        println("$trim")
//    println("${prefix}${trim}")
    }

    printEle(one.children(),prefix+prefix)
}