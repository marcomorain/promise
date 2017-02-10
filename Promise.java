import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.Optional;

public class Promise<T> {

  private final CountDownLatch latch = new CountDownLatch(1);
  private final AtomicReference<T> value = new AtomicReference<>();

  public void deliver(T value) {
    if (value == null) {
          throw new IllegalArgumentException("`value` cannot be null.");
    }
    this.value.set(value);
    this.latch.countDown();
  }

  public T get() throws InterruptedException {
    this.latch.await();
    return this.value.get();
  }

  public Optional<T> get(long timeout, TimeUnit unit) throws InterruptedException {
    final boolean delivered = this.latch.await(timeout, unit);
    if (!delivered) {
      return Optional.empty();
    }
    return Optional.of(value.get());
  }

  public static void main(String[] args) throws InterruptedException {
    System.out.println("Hello world");
    Promise<Integer> p = new Promise<Integer>();
    Optional<Integer> x = p.get(3, TimeUnit.SECONDS);
    System.out.printf("Waited and got %s%n", x);
  }
}
