/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package auraly;
import java.util.concurrent.atomic.AtomicInteger;
/**
 *
 * @author Χρήστος Σαπουνας
 */
public class UniqueIdGenerator {
    private static final AtomicInteger counter = new AtomicInteger(0);

    public int generateId() {
         int timestampPart = (int) (System.currentTimeMillis() % 1_000_000);
        int counterPart = counter.getAndIncrement() % 1000;
        return (timestampPart * 1000) + counterPart;
    }
}
