package org.apache.ignite.tracer;

import org.apache.ignite.Ignition;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.configuration.ClientConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IgniteTest {

    @Test
    void simple() {
        Assertions.assertEquals(1, 1);
        System.out.println("simple passed");
    }

    @Test
    void testHeppy() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Thread.sleep(1000);
            ClientConfiguration cfg = new ClientConfiguration()
                    .setAddresses("127.0.0.1:10800");
            IgniteClient client = Ignition.startClient(cfg);
            ClientCache<String, String> clientCache = client.getOrCreateCache("JOKER_CACHE");
            Dictionary dictionary = new Dictionary(1, "Ivan", "Hello from Quarkus REST");
            clientCache.put("KEY", "Hello from Quarkus REST");
            BinaryObject binaryObject = client.binary().toBinary(dictionary);
            clientCache.withKeepBinary().put("KEY1", binaryObject);
            BinaryObject binaryObject2 = (BinaryObject) clientCache.withKeepBinary().get("KEY1");
            Assertions.assertEquals(binaryObject, binaryObject2);
            System.out.println("testHeppy passed");
        }
    }

//    @Test
//    void jacsonTest() throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        Dictionary d = new Dictionary(123, "adsf", "awsfoimnaoj");
//        String carAsString = objectMapper.writeValueAsString(d);
//        System.out.println(carAsString);
//        Assertions.assertEquals("{\"id\":123,\"name\":\"adsf\",\"value\":\"awsfoimnaoj\"}", carAsString);
//    }
}
