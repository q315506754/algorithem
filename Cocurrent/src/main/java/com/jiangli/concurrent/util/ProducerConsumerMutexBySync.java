package com.jiangli.concurrent.util;

/**
 * Created by Jiangli on 2016/7/10.
 */
public class ProducerConsumerMutexBySync {
    static class Depot {
        private int size;
        private int capacity;
        private Object fullCondtion = new Object();
        private Object emptyCondtion = new Object();

        public Depot() {
            this(100);
        }

        public Depot(int capacity) {
            this.capacity = capacity;
        }

        public void produce(int val) {
//            synchronized (this) {
                int left = val;
                while (left > 0) {
                    while (size >= capacity) {
                        synchronized (fullCondtion) {
                            try {
                                fullCondtion.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    synchronized (this) {
                        int inc = (size + left) > capacity ? (capacity - size) : left;
                        size += inc;
                        left -= inc;
                        System.out.printf("%s produce(%3d) --> left=%3d, inc=%3d, size=%3d\n",
                                Thread.currentThread().getName(), val, left, inc, size);
                    }
                    // 通知“消费者”可以消费了。
                    synchronized (emptyCondtion) {
                        emptyCondtion.notifyAll();
                    }

                }
//            }
        }

        public void consume(int val) {
//            synchronized (this) {
                int left = val;
                while (left > 0) {
                    // 库存为0时，等待“生产者”生产产品。
                    while (size <= 0) {
                        synchronized (emptyCondtion) {
                            try {
                                emptyCondtion.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    // 获取“实际消费的数量”(即库存中实际减少的数量)
                    // 如果“库存”<“客户要消费的数量”，则“实际消费量”=“库存”；
                    // 否则，“实际消费量”=“客户要消费的数量”。
                    synchronized (this) {
                        int dec = (size < left) ? size : left;
                        size -= dec;
                        left -= dec;
                        System.out.printf("%s consume(%3d) <-- left=%3d, dec=%3d, size=%3d\n",
                                Thread.currentThread().getName(), val, left, dec, size);
                    }
                    synchronized (fullCondtion) {
                        fullCondtion.notifyAll();
                    }
                }
            }
//        }

    }

    // 生产者
    static class Producer {
        private Depot depot;

        public Producer(Depot depot) {
            this.depot = depot;
        }

        // 消费产品：新建一个线程向仓库中生产产品。
        public void produce(final int val) {
            new Thread() {
                public void run() {
                    depot.produce(val);
                }
            }.start();
        }
    }

    // 消费者
    static class Customer {
        private Depot depot;

        public Customer(Depot depot) {
            this.depot = depot;
        }

        // 消费产品：新建一个线程从仓库中消费产品。
        public void consume(final int val) {
            new Thread() {
                public void run() {
                    depot.consume(val);
                }
            }.start();
        }
    }

    public static void main(String[] args) {
        Depot mDepot = new Depot();
        Producer mPro = new Producer(mDepot);
        Customer mCus = new Customer(mDepot);

        mPro.produce(60);
        mPro.produce(120);
        mCus.consume(90);
        mCus.consume(150);
        mPro.produce(110);
    }
}
