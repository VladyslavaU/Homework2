package com.quiz;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Quiz {
    private final HashMap<String, List<Option>> questions = new HashMap<>();
    private int score;

    public Quiz(final Resource resource) throws IOException {
        this.loadQuizQuestions(resource.getFile());
    }

    public void addQuestion(final String question, final List<Option> options) {
        this.questions.put(question, options);
    }

    public HashMap<String, List<Option>> getQuestions() {
        return this.questions;
    }

    public void startQuiz() {
        this.score = 0;
        final Scanner scanner = new Scanner(System.in);
        System.out.println("Введите свои имя и фамилию");
        System.out.println(scanner.next() + ", начинаем тест.");
        for (Map.Entry<String, List<Option>> entry : this.getQuestions().entrySet()) {
            System.out.println(entry.getKey());
            Quiz.printOptions(entry.getValue());
            System.out.println("Введите номер ответа, который считаете правильным.");
            this.checkAnswer(checkInput(scanner.next()), entry.getValue());
        }
        StringBuilder sb = new StringBuilder("Вы набрали ")
                .append(this.score)
                .append(" из ")
                .append(this.questions.size())
                .append(".");
        System.out.println(sb);
        scanner.close();
    }

    private static void printOptions(final List<Option> options) {
        for (int i = 0; i < options.size(); i++) {
            System.out.println(i + 1 + ". ".concat(options.get(i).toString()));
        }
    }

    private int checkInput(final String input) {
        if (!input.matches("[1-5]")) {
            System.out.println("Пожалуйста введите число от 1 до 5.");
            final Scanner scanner = new Scanner(System.in);
            return checkInput(scanner.next());
        } else {
            return Integer.parseInt(input);
        }
    }

    private void checkAnswer(final int answer, final List<Option> options) {
        if (options.get(answer - 1).isCorrect()) {
            this.score++;
        }
    }

    private void loadQuizQuestions(final File file) throws IOException {
        final Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(new FileReader(file));
        for (CSVRecord record : records) {
            List<Option> options = new ArrayList<>();
            options.add(new Option(record.get(1), true));
            for (int i = 2; i < record.size(); i++) {
                options.add(new Option(record.get(i)));
            }
            Collections.shuffle(options);
            this.addQuestion(record.get(0), options);
        }
    }
}
