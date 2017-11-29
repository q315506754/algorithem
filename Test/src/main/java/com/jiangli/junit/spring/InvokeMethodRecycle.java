package com.jiangli.junit.spring;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

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

		long S = System.currentTimeMillis();
		long maxCost= Long.MIN_VALUE;
		long minCost= Long.MAX_VALUE;
		int totalTimes=1;

		if (fixedTimes != null) {
			 totalTimes = fixedTimes.value();

			for (int i = 0; i < totalTimes; i++) {
				long start = System.currentTimeMillis();
				fTestMethod.invokeExplosively(fTarget);
				long end = System.currentTimeMillis();
				long cost = end - start;
				if (fixedTimes.printDetail()) {
					System.out.println("第"+(i+1)+"次: cost:"+ cost +"ms");
				}
				if (cost > maxCost) {
					maxCost = cost;
				}
				if (cost < minCost) {
					minCost = cost;
				}
			}
		} else if (fixedDuration != null){
			totalTimes = 0;
			int EXECUTETIME = fixedDuration.value();

			while (true) {
				long start = System.currentTimeMillis();
				fTestMethod.invokeExplosively(fTarget);
				long end = System.currentTimeMillis();
				long cost = end - start;
				if (fixedDuration.printDetail()) {
					System.out.println("第"+(totalTimes+1)+"次: cost:"+ cost +"ms");
				}
				if (cost > maxCost) {
					maxCost = cost;
				}
				if (cost < minCost) {
					minCost = cost;
				}

				totalTimes++;

				if (end - S > EXECUTETIME) {
					break;
				}
			}
		} else {
			long start = System.currentTimeMillis();
			fTestMethod.invokeExplosively(fTarget);
			long end = System.currentTimeMillis();
			long cost = end - start;
			if (cost > maxCost) {
				maxCost = cost;
			}
			if (cost < minCost) {
				minCost = cost;
			}
		}

		long E = System.currentTimeMillis();
		long totalCost = E - S;
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("\t总次数:"+ totalTimes +"");
		System.out.println("\t总耗时:"+ totalCost +"ms");
		System.out.println("\t\t平均:"+(totalCost)/totalTimes +"ms");
		System.out.println("\t\t最高:"+maxCost +"ms");
		System.out.println("\t\t最低:"+minCost +"ms");
		if(totalCost > 0){
			System.out.println("\t\ttps:"+totalTimes*1000.0/totalCost +" /s");
		} else {
			System.out.println("\t\ttps 总耗时过小 无法估算");
		}
		if (totalTimes>2 && totalCost > 0) {
			long reducedTimes = totalTimes - 2;
			long reducedTotalCost = totalCost - maxCost -minCost;
			System.out.println("\t\ttps(去掉最高和最低):"+reducedTimes*1000.0/reducedTotalCost +" /s");
		}
		System.out.println("------------------------------------------------");
	}
}