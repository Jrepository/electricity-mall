package com.indi.electricity.mall.utils;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 线程池
 * <p>
 * whenComplete:感知异常
 * exceptionally：感知异常，并设置异常时的返回结果
 * handle:方法执行后的处理
 * <p>
 * thenRun:不能获取上一步的执行结果
 * thenAcceptAsync:接收上一步的返回值，但无返回值
 * thenApplyAsync:接收上一步的返回值，且有返回结果
 */
public class ThreadPoolUtil {


    public static final ExecutorService THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
            10,
            100,
            10,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
    );


    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        CompletableFuture.runAsync(()->{
//            System.out.println(Thread.currentThread().getId());
//        });

//        CompletableFuture<Long> future = CompletableFuture.supplyAsync(() -> {
//            return Thread.currentThread().getId();
//        }, THREAD_POOL_EXECUTOR);

//        CompletableFuture<Long> future = CompletableFuture.supplyAsync(() -> {
//            int i= 10/0;
//            return Thread.currentThread().getId();
//        }, THREAD_POOL_EXECUTOR).whenComplete((ret,exception)->{
//            System.out.println("success done,result:"+ret);
//            System.out.println("success done,exception:"+exception);
//        }).exceptionally(throwable -> {
//           return 0L;
//        });

//        CompletableFuture<Long> future = CompletableFuture.supplyAsync(() -> {
//            int i = 10 / 0;
//            return Thread.currentThread().getId();
//        }, THREAD_POOL_EXECUTOR).handle((rst, throwable) -> {
//            if (rst != null) {
//                rst *= 2;
//            }
//            if (throwable != null) {
//                rst = 0L;
//            }
//            return rst;
//        });


        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return Thread.currentThread().getId();
                }, THREAD_POOL_EXECUTOR)
                .thenApplyAsync((res) -> {
                    System.out.println("future1...");
                    return 1;
                }, THREAD_POOL_EXECUTOR);
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
                    return Thread.currentThread().getId();
                }, THREAD_POOL_EXECUTOR)
                .thenApplyAsync((res) -> {
                    System.out.println("future2...");
                    return 1;
                }, THREAD_POOL_EXECUTOR);
        CompletableFuture<Object> any = ThreadPoolUtil.anyOf(future, future2);
        System.out.println(any.get());
    }

    public static List<CompletableFuture> runAsync(List<Runnable> runnables) {
        List<CompletableFuture> futures = new ArrayList<>();
        runnables.forEach(runnable -> {
            futures.add(CompletableFuture.runAsync(runnable));
        });
        return futures;
    }

    public static CompletableFuture<Object> anyOf(CompletableFuture<?>... futures) {
        return CompletableFuture.anyOf(futures);
    }

//    public static void anyOf(List<CompletableFuture> futures) {
//        if (CollectionUtils.isEmpty(futures)) {
//            return;
//        }
//        anyOf(futures.toArray(new CompletableFuture[futures.size()]));
//    }

    public static void allOf(List<CompletableFuture> futures) {
        if (CollectionUtils.isEmpty(futures)) {
            return;
        }
        allOf(futures.toArray(new CompletableFuture[futures.size()]));
    }

    public static void allOf(List<CompletableFuture> futures, Long timeSeconds) {
        allOf(timeSeconds, futures.toArray(new CompletableFuture[futures.size()]));
    }


    public static void allOf(CompletableFuture<?>... futures) {
        CompletableFuture<Void> future = CompletableFuture.allOf(futures);
        futureGet(future);
    }



    public static void allOf(Long timeSeconds, CompletableFuture<?>... futures) {
        CompletableFuture<Void> future = CompletableFuture.allOf(futures);
        futureGet(timeSeconds, future);
    }

    private static void futureGet(CompletableFuture<?> future) {
        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static void futureGet(Long timeSeconds, CompletableFuture<?> future) {
        try {
            future.get(timeSeconds, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }


    /**
     * 监控线程
     */
    private static void monitorPool() {
        ThreadPoolExecutor tpe = ((ThreadPoolExecutor) THREAD_POOL_EXECUTOR);
        int queueSize = tpe.getQueue().size();
        if (queueSize > 5) {
//            logger.debug("当前排队线程数：{};当前活动线程数：{};当前线程数：{};历史最大线程数：{}", queueSize, tpe.getActiveCount(), tpe.getPoolSize(), tpe.getLargestPoolSize());
        }
    }

}
