package thread.concurrentutil;

import java.util.Map;
import java.util.OptionalInt;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lys on 2018/8/14.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class BankWaterService implements Runnable {

	/**
	 * 创建四个屏障
	 */
	private CyclicBarrier c = new CyclicBarrier(4, this);

	/**
	 * 假设只有4个sheet
	 */
	private ExecutorService executor = Executors.newFixedThreadPool(4);

	/**
	 * 保存每个sheet 结果
	 */
	private ConcurrentHashMap<String, Integer> sheetBankWaterCount = new ConcurrentHashMap<>(5);


	private void count() {
		for (int i = 0; i < 4; i++) {
			executor.execute(() -> {
				//计算当前sheet的银流数据，计算代码省略
				sheetBankWaterCount.put(Thread.currentThread().getName(), 1);
				//银流计算完成，插入一个屏障
				try {
					c.await();
				} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
				}
			});
		}
	}


	@Override
	public void run() {
		int sum = 0;
		OptionalInt result = sheetBankWaterCount.entrySet().stream().mapToInt(Map.Entry::getValue).reduce((a, b) -> a + b);
		if(result.isPresent()){
			sum = result.getAsInt();
		}
		sheetBankWaterCount.put("result",sum);
		System.out.println(sum);

	}

	public static void main(String[] args) {
		BankWaterService bankWaterService = new BankWaterService();
		bankWaterService.count();
		bankWaterService.executor.shutdown();
	}
}
