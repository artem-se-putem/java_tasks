package com.example;

import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction.SourceContext;
import org.apache.flink.streaming.api.functions.windowing.ProcessAllWindowFunction;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
// import java.time.Duration;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

public class averagePerUser {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        
        // Увеличиваем параллелизм для наглядности (опционально)
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
events
    // .keyBy(x -> x)
    .windowAll(TumblingProcessingTimeWindows.of(Time.seconds(5)))
    .process(new ProcessAllWindowFunction<Tuple2<String, String>, Tuple2<String, Double>, TimeWindow>() {
        @Override
        public void process(
                           Context context, 
                           Iterable<Tuple2<String, String>> events, 
                           Collector<Tuple2<String, Double>> out) throws Exception {
            int totalEvents = 0;

            java.util.Set<String> uniqueUsers = new java.util.HashSet<>();

            for (Tuple2<String, String> event: events){
                totalEvents++;
                uniqueUsers.add(event.f0);
            }
            
            double averageEventsPerUser = 0.0;
            if (!uniqueUsers.isEmpty()){
                averageEventsPerUser = (double) totalEvents / uniqueUsers.size();
            }
            out.collect(Tuple2.of("average for user:", averageEventsPerUser));  // Возвращаем userId и количество
        }
    })
    .print();

        
        // Запускаем выполнение
        env.execute("Infinite Integer Stream");
    }
}
