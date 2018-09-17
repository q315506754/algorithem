package com.jiangli.thread.main.executors;

/**
 * @author Jiangli
 * @date 2018/9/17 17:06
 */
public class ThreadPoolExecutorMain {

    public static void main(String[] args) {
    //     a pool
    //    core pool sizes ,  maximum pool sizes
    //    always new ,    when queue is full

    //    Keep-alive times
    //    使得超出core size部分的线程闲置keepAliveTime之后销毁
    //    设置了allowCoreThreadTimeOut之后也可以让核心线程闲置keepAliveTime之后销毁

    //    queue
    //    3种策略
    //    direct handoffs 直接转移（池无界） 适用内部有依赖的任务 避免reject，线程池增长风险
    //    unbounded queues （队列无界） 适用内部无依赖的任务,web request，队列增长风险
    //    bounded queues （队列有界）适用资源受限场景，要在队列大小和池大小中权衡以获得高吞吐量。
    //     队列太小，则cpu一直忙碌，调度开销大，吞吐下降
    //     队列太大，则cpu闲置，资源没有得到合理利用，吞吐下降


    //    拒绝任务情况,reject，此时调用RejectedExecutionHandler
    //    1 池和队列大小有界且已满
    //    2 exector has been shut down

    //    此时调用RejectedExecutionHandler 四种策略
    //    ThreadPoolExecutor.AbortPolicy throw RejectedExecutionException
    //    ThreadPoolExecutor.CallerRunsPolicy 提交者自己运行任务
    //    ThreadPoolExecutor.DiscardPolicy 忽略任务
    //    ThreadPoolExecutor.DiscardOldestPolicy 丢弃队头任务，之后重试（可能反复执行）


    }
}
