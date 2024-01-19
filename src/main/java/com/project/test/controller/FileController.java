package com.project.test.controller;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.function.BiFunction;

@RestController
public class FileController {

    @GetMapping(value = "/stream-file", produces = "text/event-stream")
    public Flux<ServerSentEvent<String>> streamFile() {
        return Flux.generate(
                () -> new BufferedReader(new FileReader("C:/Users/simon/Desktop/updateCV.txt")),
                new FileReadHandler(),
                this::closeReader
        ).delayElements(Duration.ofMillis(50));
    }

    private void closeReader(BufferedReader reader) {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException e) {
            // Log and handle the IOException
        }
    }

    private static class FileReadHandler implements BiFunction<BufferedReader, SynchronousSink<ServerSentEvent<String>>, BufferedReader> {
        @Override
        public BufferedReader apply(BufferedReader reader, SynchronousSink<ServerSentEvent<String>> sink) {
            try {
                String line = reader.readLine();
                if (line != null) {
                    sink.next(ServerSentEvent.builder(line).build());
                } else {
                    sink.complete();
                }
            } catch (IOException e) {
                sink.error(e);
            }
            return reader;
        }
    }
}
