package com.example.flink;

import java.time.Duration;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.connector.source.util.ratelimit.RateLimiterStrategy;
import org.apache.flink.connector.datagen.source.DataGeneratorSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.SlidingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;

public class aggregate_SlidingWindow {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Вместо SourceFunction теперь используется DataGeneratorSource или новые коннекторы
        DataGeneratorSource<Integer> source = new DataGeneratorSource<>(
            index -> {
                // Генерируем случайное число от 0 до 100
                Integer a = (int) (Math.random() * 101);
                System.out.print(String.valueOf(a) + " ");
                return a;
            },
            Integer.MAX_VALUE, // сколько элементов генерировать
            RateLimiterStrategy.perSecond(1), // скорость: 1 сообщение в секунду
            Types.INT
        );

        // Окно шириной 5 сек, сдвиг - 2 сек
        env.fromSource(source, WatermarkStrategy.noWatermarks(), "Generator Source")
           .windowAll(SlidingProcessingTimeWindows.of(Duration.ofSeconds(5), Duration.ofSeconds(2)))
           .sum(0)
           .print();

        env.execute("Flink 2.0 Job");
    }
}
