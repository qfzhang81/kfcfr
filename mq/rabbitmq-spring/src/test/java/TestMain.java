import java.io.IOException;

/**
 * Created by zhangqf77 on 2018/7/13.
 */
public class TestMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        TestSender send = new TestSender();
        send.start();
        Thread.sleep(5000);

        TestReceiverAll receiverAll_1 = new TestReceiverAll();
        receiverAll_1.start();
        TestReceiverAll receiverAll_2 = new TestReceiverAll();
        receiverAll_2.start();
        TestReceiver369 receiver369 = new TestReceiver369();
        receiver369.start();
    }
}
