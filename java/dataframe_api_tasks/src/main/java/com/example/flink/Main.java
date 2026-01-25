package com.example.flink;

import java.time.Duration;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.connector.source.util.ratelimit.RateLimiterStrategy;
import org.apache.flink.connector.datagen.source.DataGeneratorSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;

public class Main {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Вместо SourceFunction теперь используется DataGeneratorSource или новые коннекторы
        DataGeneratorSource<String> source = new DataGeneratorSource<>(
                index -> "Message #" + index,
                Long.MAX_VALUE, // сколько элементов генерировать
                RateLimiterStrategy.perSecond(1), // скорость: 1 сообщение в секунду
                Types.STRING
        );

        env.fromSource(source, WatermarkStrategy.noWatermarks(), "Generator Source")
           // Вместо window(TumblingProcessingTimeWindows.of(Time.seconds(5)))
           // Используем Duration из пакета java.time
           .windowAll(TumblingProcessingTimeWindows.of(Duration.ofSeconds(5)))
           .reduce((value1, value2) -> value1 + " & " + value2)
           .print();

        env.execute("Flink 2.0 Job");
    }
}
