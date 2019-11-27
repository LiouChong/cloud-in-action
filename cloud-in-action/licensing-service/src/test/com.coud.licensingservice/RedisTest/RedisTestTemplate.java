package RedisTest;

import com.cloud.licensingservice.LicensingServiceApplication;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Author: Liuchong
 * Description:
 * date: 2019/11/20 21:44
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LicensingServiceApplication.class)
public class RedisTestTemplate {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private KafkaProducer<String, String> producer;
    @Autowired
    private KafkaConsumer<String, String> consumer;

    @Test
    public void testGet() {
        Set<Object> org = redisTemplate.opsForHash().keys("org");

        System.out.println(org);
    }

    @Test
    public void testPut() {
        redisTemplate.opsForHash().put("org", "foo", "bar");
    }

    @Test
    public void kafkaProducerTest() {
        producer.send(new ProducerRecord<String, String>("organization", "from LiuChong"));
        producer.close();
    }

    @Test
    public void testConsumer() {
        consumer.subscribe(Collections.singletonList("organization"));
    }
}
