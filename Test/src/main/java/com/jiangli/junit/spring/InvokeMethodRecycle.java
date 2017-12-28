package com.jiangli.junit.spring;

import com.jiangli.junit.spring.data.DataCollector;
import com.jiangli.junit.spring.data.StatisticModel;
import com.jiangli.junit.spring.data.handler.ConsolePrintDataHandler;
import com.jiangli.junit.spring.data.handler.DataHandler;
import com.jiangli.junit.spring.data.handler.ResultsCollector;
import com.jiangli.junit.spring.group.AvailableGroup;
import com.jiangli.junit.spring.group.CommonGroup;
import com.jiangli.junit.spring.group.InvokerGroup;
import com.jiangli.junit.spring.group.invoker.GroupInvoker;
import com.jiangli.junit.spring.group.invoker.SingleGroup;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import java.lang.reflect.Constructor;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class InvokeMethodRecycle extends Statement {
    private final FrameworkMethod fTestMethod;
	private Object fTarget;

	public InvokeMethodRecycle(FrameworkMethod testMethod, Object target) {
		fTestMethod= testMethod;
		fTarget= target;
	}
	
	@Override
	public void evaluate() throws Throwable {
		System.out.println("--------------method "+fTestMethod.getMethod()+" ----------------------");

		RepeatFixedTimes fixedTimes = fTestMethod.getAnnotation(RepeatFixedTimes.class);
		RepeatFixedDuration fixedDuration = fTestMethod.getAnnotation(RepeatFixedDuration.class);


		//首先分组
		GroupInvoker[] groupInvokers = getGroupInvokers();

		//数据收集器
		DataHandler dataHandler = getDataHandler();

		for (GroupInvoker groupInvoker : groupInvokers) {
			InvokeContext context = new InvokeContext(groupInvoker);
			context.setFixedTimes(fixedTimes)
			.setFixedDuration(fixedDuration)
			.init();

			ExecutorService threadPool = null;
			if (context.isMultiThread()) {
				threadPool = Executors.newFixedThreadPool(context.getThreadNum());
			}

			DataCollector model = new StatisticModel();

			model.start();

			//三种不同的模式选择其一执行
			executeFixedTimes(context,model,threadPool);
			executeFixedDuration(context,model,threadPool);
			executeDefault(context,model);

			model.finish();

			dataHandler.handler(context, model);
		}

	}

	private DataHandler getDataHandler() throws Exception {
		ResultsCollector resultsCollector = fTestMethod.getAnnotation(ResultsCollector.class);
		Class<? extends DataHandler> value;
		if (resultsCollector != null) {
			value = resultsCollector.value();
		} else {
			value = ConsolePrintDataHandler.class;//default
		}
		return value.newInstance();
	}


	private void executeDefault(InvokeContext invokeContext, DataCollector model) throws Throwable {
		if (invokeContext.getInvokeMode() == InvokeMode.DEFAULT) {
			model.setTotalTimes(1);

			long start = System.currentTimeMillis();
			invokeContext.getGroupInvoker().evaluate();
			long end = System.currentTimeMillis();
			model.record(end - start);
		}
	}

	private void executeFixedDuration(final InvokeContext invokeContext,final DataCollector model,final ExecutorService threadPool) throws Throwable {
		if (invokeContext.getInvokeMode() == InvokeMode.FIXED_DURATION){
			model.setPlanCost(invokeContext.getFixedDuration().value());

			final long PLAN_COST = model.getPlanCost();
			final long START_TS = model.getStartTs();
			//多线程
			if (invokeContext.isMultiThread()){
				final AtomicLong times = new AtomicLong(0);

				final CountDownLatch countDownLatch = new CountDownLatch(invokeContext.getThreadNum());
				final Object waitLock = new Object();

				for (int i = 0; i < invokeContext.getThreadNum(); i++) {
					threadPool.execute(new Runnable() {
						@Override
						public void run() {
							try {
								countDownLatch.countDown();
								synchronized (waitLock) {
									waitLock.wait();
								}

								while (true) {
									invokeContext.getGroupInvoker().evaluate();
									times.incrementAndGet();

								}
							} catch (Throwable throwable) {
								throwable.printStackTrace();
							}
						}
					});
				}

				countDownLatch.await();

				model.start();
				synchronized (waitLock) {
					waitLock.notifyAll();
				}

				Thread.sleep(PLAN_COST);
				model.setTotalTimes(times.get());
			} else {
				//单线程
				long times = 0;

				while (true) {
					long start = System.currentTimeMillis();
					invokeContext.getGroupInvoker().evaluate();
					long end = System.currentTimeMillis();
					long cost = end - start;
					times++;

					if (invokeContext.isPrintDetail()) {
						System.out.println("第"+times+"次: cost:"+ cost +"ms");
					}
					model.record(cost);

					if (end - START_TS > PLAN_COST) {
						break;
					}
				}
				model.setTotalTimes(times);
			}
		}
	}

	private void executeFixedTimes(final InvokeContext invokeContext,final DataCollector model,final ExecutorService threadPool) throws Throwable {
		if (invokeContext.getInvokeMode() == InvokeMode.FIXED_TIMES) {
			model.setTotalTimes(invokeContext.getFixedTimes().value());

			final long MAX_TIMES = model.getTotalTimes();

			//多线程
			if (invokeContext.isMultiThread()){
				final AtomicLong times = new AtomicLong(0);

				final CountDownLatch countDownLatch = new CountDownLatch(invokeContext.getThreadNum());
				final CountDownLatch waitLock = new CountDownLatch(1);

				for (int i = 0; i < invokeContext.getThreadNum(); i++) {
					threadPool.execute(new Runnable() {
						@Override
						public void run() {
							try {
								countDownLatch.countDown();
								synchronized (waitLock) {
									waitLock.wait();
								}

								while (true) {
									invokeContext.getGroupInvoker().evaluate();

									if(times.incrementAndGet() >= MAX_TIMES){
										waitLock.countDown();
										break;
									}
								}
							} catch (Throwable throwable) {
								throwable.printStackTrace();
							}
						}
					});
				}

				countDownLatch.await();

				model.start();
				synchronized (waitLock) {
					waitLock.notifyAll();
				}

				waitLock.await();
			} else {
				//单线程
				for (int i = 0; i < MAX_TIMES; i++) {
					long start = System.currentTimeMillis();
					invokeContext.getGroupInvoker().evaluate();
					long end = System.currentTimeMillis();
					long cost = end - start;
					if (invokeContext.isPrintDetail()) {
						System.out.println("第"+(i+1)+"次: cost:"+ cost +"ms");
					}
					model.record(cost);
				}
			}
		}
	}

	private GroupInvoker[] getGroupInvokers() throws Exception {
		InvokerGroup invokerGroup = fTestMethod.getAnnotation(InvokerGroup.class);

		String[] params=null;
		Class<? extends CommonGroup> groupClass;
		if (invokerGroup != null) {
			AvailableGroup group = invokerGroup.value();
			params = invokerGroup.params();
			groupClass = group.getGroupClass();
		} else {
			groupClass = SingleGroup.class;//default
		}
		if (params == null) {
			params = new String[]{};
		}

		Constructor<? extends CommonGroup> constructor = groupClass.getDeclaredConstructor(FrameworkMethod.class, Object.class);
		CommonGroup group = constructor.newInstance(fTestMethod, fTarget);
		GroupInvoker[] groupInvokers = group.splitGroup(params);
		return groupInvokers;
	}
}