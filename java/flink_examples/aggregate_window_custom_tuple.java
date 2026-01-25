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

public class aggregate_window_custom_tuple {
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
    .keyBy(new KeySelector<Tuple2<String, String>, String>() {
        @Override
        public String getKey(Tuple2<String, String> value) throws Exception {
            return value.f0;  // Возвращаем userId
        }
    })
    .window(TumblingProcessingTimeWindows.of(Time.seconds(5)))
    .process(new ProcessWindowFunction<Tuple2<String, String>, Tuple2<String, Integer>, String, TimeWindow>() {
        @Override
        public void process(String userId, 
                           Context context, 
                           Iterable<Tuple2<String, String>> events, 
                           Collector<Tuple2<String, Integer>> out) throws Exception {
            int count = 0;
            for (Tuple2<String, String> event : events) {
                count++;  // Считаем количество событий
            }
            out.collect(Tuple2.of(userId, count));  // Возвращаем userId и количество
        }
    })
    .print();

        
        // Запускаем выполнение
        env.execute("Infinite Integer Stream");
    }
}
