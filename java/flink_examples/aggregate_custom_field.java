// public package com.example;

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

public class aggregate_custom_field {
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
    .map(tuple -> 1)  // каждый элемент → 1 (тип: Integer)
    .windowAll(TumblingProcessingTimeWindows.of(Time.seconds(5)))
    .aggregate(new AggregateFunction<Integer, Integer, Integer>() {
        @Override
        public Integer createAccumulator() {
            return 0;  // Начальное значение аккумулятора
        }

        @Override
        public Integer add(Integer value, Integer accumulator) {
            return accumulator + value;  // Прибавляем 1 к счетчику
        }

        @Override
        public Integer getResult(Integer accumulator) {
            return accumulator;  // Возвращаем итоговое количество
        }

        @Override
        public Integer merge(Integer a, Integer b) {
            return a + b;  // Объединяем результаты из разных параллельных задач
        }
    })
    .print();

        
        // Запускаем выполнение
        env.execute("Infinite Integer Stream");
    }
}