package com.quiz;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {
    public static void main(String[] args) {
        final ApplicationContext ctx = new ClassPathXmlApplicationContext("app-config.xml");
        final Quiz quiz = ctx.getBean(Quiz.class);
        quiz.startQuiz();
    }
}
