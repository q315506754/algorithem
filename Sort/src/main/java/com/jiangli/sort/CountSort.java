package com.jiangli.sort;

import com.jiangli.common.core.Sorter;
import org.springframework.stereotype.Component;

/**
 * @author Jiangli
 * @date 2018/10/26 11:00
 */
@Component
public class CountSort extends Sorter<Integer> {
    @Override
    public Integer[] sort(Integer[] arr) {
        return new Integer[0];
    }

    /**
     * 二.TCP协议中的常见计时器
     *
     *    TCP协议通常包含四种计时器：重传计时器，持续计时器，保活计时器和时间等待计时器。
     *
     *    1.重传计时器(Retransmission Timer)，该计时器用于整个连接期间，用于处理RTO(重传超时)。当一个报文从发送队列发出去后，就启动该计时器，若在RTO之内收到了该报文的ACK，则停止该重传计时器；若t>=RTO都还没有收到报文的ACK，则重传该报文，并清空该重传计时器。
     *
     *     注意：若ACK报文捎带其它信息，则不会为该报文设置重传计时器。
     *
     *    2.持续计时器(Persistent Timer)，用于处理零窗体值的通过，防止"死锁"现象。
     *
     *      假若接收端的TCP要命令发送端的TCP停止发送报文段时，就向发送方发送TCP发送一个报文段，该报文的窗体大小字段为0，这就是零窗体值。发送端的TCP收到该零值窗体值报文后，就会停止向接收端的TCP发送报文，直到接收端的TCP发送一个窗体大小非0的ACK报文为止。。。
     *
     *      当接收端向发送端发送ACK应答的时候，假如该应答在传输的路途中丢失了，发送端并没有收到该应答，不再向接收端发送消息；而接收端却认为自己已经做出了回应，一直处在等待的状态中。此时这种情况就是传说中的"死锁"
     *
     *      为了解决上述的"死锁现象"，TCP中存在一个持续计时器。启动后，在未超时期间，若收到了接收端的非0窗体的通知，则停止该计时器；若该持续计时器超时了，则发送TCP就发送一个特殊的探測报文段，该报文段仅包括1B的新数据，该报文不须要确认。探測报文的作用在于提醒对方(目的能够记录在数据部分)，重传上次发送方发送的那个ACK报文(即那个非0值窗体的报文)。
     *
     *      注意：TCP规定，接收窗体的rwnd=0，也必须接收这三种报文段：零窗体探測报文段、确认报文段和携带紧急数据的报文段。
     *
     *  3.保活计时器(Keeplive Timer)，防止两端的TCP在连接期间长时间处于空暇状态。一般是server设置的计时器，超时通常设置为2h，当server超过了2h还没有收到客户的任何信息，server就向客户发送过一个探測报文段，若连续发送了10个探測报文段(每个75s一个)还没有响应，就觉得客户出了故障，直接终止这个连接。
     *
     *  4.时间等待计时器(Time-Wait timer)，超时时间=2MSL,max segment lifetime，这个计时器有两个作用：
     *
     *       1).保证在2MSL时间内，server端可以收到最后一个ACK；
     *
     *       2).可以保证之前某些在网络中滞留非常久的发给server的报文不会在本次连接连接关闭后再去骚扰server。
     *
     *     值得注意的是，最后两次挥手期间，启动了两种计时器，server向client发送FIN后启动重传计时器；client收到FIN后，向server发送ACK，同一时间也启动Time-Wait计时器(时间长度为2MSL)。
     */

}
