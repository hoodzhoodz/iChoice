package com.choicemmed.cbp1k1sdkblelibrary.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Name: ZhengZhong on 2018/1/22 09:39
 *         Email: zheng_zhong@163.com
 * @version V1.0.0
 */
public class BleScanThreadUtils {
    private static volatile BleScanThreadUtils instance;
    private ThreadFactory factory = new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "BP2941血压计搜索线程池");
        }
    };
    private ExecutorService executorService = new ThreadPoolExecutor(
            20, 50, 0L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingDeque<Runnable>(),
            factory);

    private BleScanThreadUtils() {
    }

    public static BleScanThreadUtils getInstance() {
        if (instance == null) {
            synchronized (BleScanThreadUtils.class) {
                if (instance == null) {
                    return new BleScanThreadUtils();
                }
            }
        }
        return instance;
    }

    public void execute(Runnable r) {
        executorService.execute(r);
    }

}
