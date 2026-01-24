package com.example;

import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction.SourceContext;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
// import java.time.Duration;

public class generate_data {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        
        DataStream<Tuple2<String, String>> events = env.addSource(new SourceFunction<Tuple2<String, String>>() {
            private volatile boolean isRunning = true;

            @Override
            public void run(SourceContext<Tuple2<String, String>> ctx) throws Exception {
                String[] users = {"user1", "user2", "user3"};
                String[] events = {"click", "view", "purchase"};

                while (isRunning) {
                    String user = users[(int) (Math.random()* users.length)];
                    String event = events[(int) (Math.random()* events.length)];

                    ctx.collect(Tuple2.of(user, event));  // Отправляем текущее число в поток
                    Thread.sleep(500);       // Ждём 500 мс (0.5 сек)
                }
            }

            @Override
            public void cancel() {
                isRunning = false;  // Корректное завершение при остановке задания
            }
        });
        
        // Выводим в консоль (каждое число будет с префиксом номера параллельной задачи)
        // infiniteStream.print();

        events.keyBy(x->1)//.window(
            // TumblingProcessingTimeWindows.of(Time.seconds(4)))
            .print();
        
        // Запускаем выполнение
        env.execute("Infinite Integer Stream");
    }
}
