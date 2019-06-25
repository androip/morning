package morining;

import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.alibaba.fastjson.JSON;

@RabbitListener(queues = "Test")
@Component
public class RabbitMqListener {

	
    @RabbitHandler
    public void receive1(String in) throws InterruptedException {
//        receive(in, 1);
        System.out.println(" [x] Received '" + in + "'");
        Map<String, Map<String,Object>> map = (Map<String, Map<String, Object>>) JSON.parse(in);
        System.out.println(" [x] convert '" + map.get("k1") + "'");
        
    }
    
    public void receive(String in, int receiver) throws InterruptedException {
        StopWatch watch = new StopWatch();
        watch.start();
        Map<String, Map<String,Object>> map = (Map<String, Map<String, Object>>) JSON.parse(in);
        System.out.println("instance " + receiver + " [x] Received '" + map + "'");
        doWork(in);
        watch.stop();
        System.out.println("instance " + receiver + " [x] Done in " + 
            watch.getTotalTimeSeconds() + "s");
    }
    
    private void doWork(String in) throws InterruptedException {
        for (char ch : in.toCharArray()) {
            if (ch == '.') {
                Thread.sleep(1000);
            }
        }
    }
}
