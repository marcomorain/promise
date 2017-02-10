import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class Promise<T> {

  private final CountDownLatch latch = new CountDownLatch(1);
  private final AtomicReference<T> value = new AtomicReference<>();

  public void deliver(T value) {
    this.value.set(value);
    latch.countDown();
  }

  public T get() throws InterruptedException {
    this.latch.await();
    return this.value.get();
  }

  public T get(long timeout, TimeUnit unit, T defaultValue) throws InterruptedException {
    final boolean delivered = this.latch.await(timeout, unit);
    if (!delivered) {
      return defaultValue;
    }

    return this.value.get();
  }

  public static void main(String[] args) throws InterruptedException {
    System.out.println("Hello world");
    Promise<Integer> p = new Promise<Integer>();
    Integer x = p.get(3, TimeUnit.SECONDS, 5);
    System.out.printf("Waited and got %s%n", x);
  }
}
