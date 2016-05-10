package com.jiangli.memory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

import static java.lang.System.out;

public class MemoryTest {
    public static final int MODEL_RANDOM = 0x0001;
    public static final int MODEL_ALLANDDISORDER = 0x0000;
    public static final int MODEL_ALLANDORDER = 0x0010;
    public static final int MODEL_ALLANDREVERSE = 0x0020;
    public static int repeat_times = 3;
    static Random random = new Random();

    public static boolean isEnd(String obj) {
        if (obj == null) {
            return true;
        }
        if ("end".equals(obj.trim())) {
            return true;
        }
        if (";".equals(obj.trim())) {
            return true;
        }
        if (".".equals(obj.trim())) {
            return true;
        }
        return false;
    }

    public static int random(int from, int to) {
        return from + random.nextInt(to - from + 1);
    }

    public static int[] generate(int from, int to, int GUESS_SIZE, int GUESS_MODEL) {
        // generate
        List<Integer> list = new LinkedList<Integer>();
        switch (GUESS_MODEL) {
            case MemoryTest.MODEL_RANDOM: {
                while (GUESS_SIZE-- > 0) {
                    list.add(random(from, to));
                }
                break;
            }
            case MemoryTest.MODEL_ALLANDDISORDER: {
                for (int i = 0; i < repeat_times; i++) {
                    for (int j = from; j <= to; j++) {
                        list.add(j);
                    }
                }
                Collections.shuffle(list, random);
                break;
            }
            case MemoryTest.MODEL_ALLANDORDER: {
                for (int i = 0; i < repeat_times; i++) {
                    for (int j = from; j <= to; j++) {
                        list.add(j);
                    }
                }
                break;
            }
            case MemoryTest.MODEL_ALLANDREVERSE: {
                for (int i = 0; i < repeat_times; i++) {
                    for (int j = to; j >= from; j--) {
                        list.add(j);
                    }
                }
                break;
            }
            default: {
                System.err.println("default!");
            }
        }

        // switch
        int size = list.size();
        int[] ret = new int[size];
        for (int i = 0; i < size; i++) {
            ret[i] = list.get(i);
        }
        // System.arraycopy(list.toArray(new Integer[size]), 0, ret, 0, size);
        return ret;
    }

    /**
     * @param args
     *
     * @return void
     *
     * @author JiangLi CreateTime 2014-2-26 下午2:02:52
     */
    public static void main(String[] args) {
        final int GUESS_FROM = 1;
        final int GUESS_TO = 100;
        final int GUESS_MODEL = MemoryTest.MODEL_ALLANDDISORDER;
//		final int GUESS_MODEL = MemoryTest.MODEL_RANDOM;
        final int GUESS_SIZE = 111;
        final boolean seeGenerates = true;
        repeat_times = 1;

        try {
            // initial
            int[] generate = generate(GUESS_FROM, GUESS_TO, GUESS_SIZE, GUESS_MODEL);
            int totalSize = generate.length;
            out.println("Initial over!The total size is " + totalSize);
            if (seeGenerates) {
                out.println(Arrays.toString(generate));
            }

            // initial
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String readLine = null;
            int counter = 0;
            List<MemoryResult> testResults = new LinkedList<MemoryResult>();


            // start
            out.println("start!......");
            while (true) {
                // collection
                long start = System.currentTimeMillis();
                int dataOfThisTime = generate[counter++];

                // info
                System.out.print("imagine this:");
//				System.err.println(dataOfThisTime);
                System.out.println(dataOfThisTime);

                // check
                readLine = br.readLine();
                if (isEnd(readLine)) {
                    out.println("command over!......");
                    break;
                }
                if (counter == totalSize) {
                    out.println("test over!......");
                    break;
                }
                if ("l".equalsIgnoreCase(readLine)) {
                    out.println((totalSize - counter) + " left");
                    start = System.currentTimeMillis();//reset the start time
//					readLine = br.readLine();
                    counter--;
                    continue;
                }

                // info
                long end = System.currentTimeMillis();
                out.println("cost:" + (end - start) + " ms");

                // collection
                MemoryResult resultOne = new MemoryResult(dataOfThisTime, start, end);
                testResults.add(resultOne);
            }
            out.println("end!......");

            // analyse
            out.println("analysing!");
            analyse(testResults);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * { "2": { "firstAppearedTime":30, "appearedTimes":10,
     * "appearedTime":[30,35.....,64], "costime":[1234,3333,....],
     * "totalCostTime":99231, "minimumCostTime":1234, "maximumCostTime":9999,
     * "averageCostTime":5555 } }
     */
    public static void analyse(List<MemoryResult> results) {
        Map<Integer, MemoryAnalyseResult> analyseResults = new HashMap<Integer, MemoryAnalyseResult>();

        int counter = 0;
        long totalTime = 0;
        for (MemoryResult rOne : results) {
            counter++;
            int key = rOne.getData();
            long costOfThisTime = rOne.getEndTime() - rOne.getStartTime();
            totalTime += costOfThisTime;

            MemoryAnalyseResult mData = analyseResults.get(key);

            if (mData == null) {
                mData = new MemoryAnalyseResult();
                mData.setData(key);
                mData.setFirstAppearedTime(counter);
                List<Integer> appearedTime = new ArrayList<Integer>();
                appearedTime.add(counter);
                mData.setAppearedTime(appearedTime);
                mData.setAppearedTimes(1);
                List<Long> costime = new ArrayList<Long>();
                costime.add(costOfThisTime);
                mData.setCostime(costime);
                mData.setTotalCostTime(costOfThisTime);
                mData.setMaximumCostTime(costOfThisTime);
                mData.setMinimumCostTime(costOfThisTime);
                mData.setAverageCostTime(costOfThisTime);

                analyseResults.put(key, mData);
            } else {
                List<Integer> appearedTime = mData.getAppearedTime();
                appearedTime.add(counter);
                mData.setAppearedTimes(mData.getAppearedTimes() + 1);
                List<Long> costime = mData.getCostime();
                costime.add(costOfThisTime);
                mData.setTotalCostTime(mData.getTotalCostTime() + costOfThisTime);

                // caculate average
                mData.setAverageCostTime(mData.getTotalCostTime() / mData.getAppearedTimes());

                // caculate min&max
                long calMin = costime.get(0);
                long calMax = costime.get(0);

                for (Long eachTime : costime) {
                    if (eachTime < calMin) {
                        calMin = eachTime;
                    }
                    if (eachTime > calMax) {
                        calMax = eachTime;
                    }
                }

                mData.setMinimumCostTime(calMin);
                mData.setMaximumCostTime(calMax);
            }

        }

        out.println(analyseResults);
        out.println("--------original result start--------");
        List<MemoryAnalyseResult> resultList = new ArrayList<MemoryAnalyseResult>();
        for (Integer key : analyseResults.keySet()) {
            MemoryAnalyseResult mOne = analyseResults.get(key);
            System.out.println(mOne);
            resultList.add(mOne);
        }
        out.println("--------original result   end--------");


        out.println("--------sort by max start--------");
        Collections.sort(resultList, new MaxComparator());
        for (MemoryAnalyseResult mOne : resultList) {
            String printStr = "max time:" + mOne.getData() + "->" + mOne.getMaximumCostTime();
            if (mOne.getAppearedTimes() > 1) {
                printStr = printStr + "(" + Arrays.toString(mOne.getCostime().toArray(new Long[mOne.getCostime().size()])) + " average:" + mOne.getAverageCostTime() + ")";
            }
            System.out.println(printStr);
        }
        out.println("--------sort by max   end--------");
        out.println("totalTime:" + totalTime);
    }
}

class MemoryAnalyseResult {
    // key
    int data;

    int firstAppearedTime;
    List<Integer> appearedTime;
    int appearedTimes;
    List<Long> costime;
    long totalCostTime;
    long minimumCostTime;
    long maximumCostTime;
    long averageCostTime;

    /**
     * @param data
     * @param firstAppearedTime
     * @param appearedTime
     * @param costime
     * @param totalCostTime
     * @param minimumCostTime
     * @param maximumCostTime
     * @param averageCostTime
     */
    public MemoryAnalyseResult(int data, int firstAppearedTime, List<Integer> appearedTime, List<Long> costime, long totalCostTime, long minimumCostTime, long maximumCostTime, long averageCostTime) {
        super();
        this.data = data;
        this.firstAppearedTime = firstAppearedTime;
        this.appearedTime = appearedTime;
        this.costime = costime;
        this.totalCostTime = totalCostTime;
        this.minimumCostTime = minimumCostTime;
        this.maximumCostTime = maximumCostTime;
        this.averageCostTime = averageCostTime;
    }

    /**
     *
     */
    public MemoryAnalyseResult() {
        super();
    }

    public int getAppearedTimes() {
        return appearedTimes;
    }

    public void setAppearedTimes(int appearedTimes) {
        this.appearedTimes = appearedTimes;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public int getFirstAppearedTime() {
        return firstAppearedTime;
    }

    public void setFirstAppearedTime(int firstAppearedTime) {
        this.firstAppearedTime = firstAppearedTime;
    }

    public List<Integer> getAppearedTime() {
        return appearedTime;
    }

    public void setAppearedTime(List<Integer> appearedTime) {
        this.appearedTime = appearedTime;
    }

    public List<Long> getCostime() {
        return costime;
    }

    public void setCostime(List<Long> costime) {
        this.costime = costime;
    }

    public long getTotalCostTime() {
        return totalCostTime;
    }

    public void setTotalCostTime(long totalCostTime) {
        this.totalCostTime = totalCostTime;
    }

    public long getMinimumCostTime() {
        return minimumCostTime;
    }

    public void setMinimumCostTime(long minimumCostTime) {
        this.minimumCostTime = minimumCostTime;
    }

    public long getMaximumCostTime() {
        return maximumCostTime;
    }

    public void setMaximumCostTime(long maximumCostTime) {
        this.maximumCostTime = maximumCostTime;
    }

    public long getAverageCostTime() {
        return averageCostTime;
    }

    public void setAverageCostTime(long averageCostTime) {
        this.averageCostTime = averageCostTime;
    }

    @Override
    public String toString() {
        return "MemoryAnalyseResult [data(key)=" + data + ", firstAppearedTime=" + firstAppearedTime + ", appearedTime=" + appearedTime + ", appearedTimes=" + appearedTimes + ", costime=" + costime
                + ", totalCostTime=" + totalCostTime + ", minimumCostTime=" + minimumCostTime + ", maximumCostTime=" + maximumCostTime + ", averageCostTime=" + averageCostTime + "]";
    }


}

class MemoryResult {
    int data;
    long startTime;
    long endTime;

    /**
     * @param data
     * @param startTime
     * @param endTime
     */
    public MemoryResult(int data, long startTime, long endTime) {
        super();
        this.data = data;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     *
     */
    public MemoryResult() {
        super();
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

}
