package ru.skillbranch.devintensive.models

import android.util.Log
import androidx.core.text.isDigitsOnly

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    fun askQuestion(): String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.IDLE -> Question.IDLE.question
        Question.SERIAL -> Question.SERIAL.question
    }

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {
        return if (question.isCorrect(answer)) {
            if (question.answers.contains(answer)) {
                question = question.nextQuestion()
                "Отлично - ты справился\n${question.question}" to status.color
            } else {
                if(question == Question.IDLE){
                    question.question to status.color
                }
                else {
                    if (status == Status.CRITICAL) {
                        status = status.nextStatus()
                        question = Question.NAME
                        "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
                    } else {
                        status = status.nextStatus()
                        "Это неправильный ответ\n${question.question}" to status.color
                    }
                }
            }
        } else {
            "${question.condition()}\n${question.question}" to status.color
        }
    }


    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus(): Status {
            return if (this.ordinal < values().lastIndex)
                values()[this.ordinal + 1]
            else values()[0]
        }
    }

    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf("Бендер", "Bender")) {
            override fun nextQuestion(): Question = PROFESSION
            override fun condition(): String = "Имя должно начинаться с заглавной буквы"
            override fun isCorrect(answer: String): Boolean = answer.getOrElse(0){'b'}.isUpperCase()
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion(): Question = MATERIAL
            override fun condition(): String = "Профессия должна начинаться со строчной буквы"
            override fun isCorrect(answer: String): Boolean = answer.getOrElse(0){'B'}.isLowerCase()
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun nextQuestion(): Question = BDAY
            override fun condition(): String = "Материал не должен содержать цифр"
            override fun isCorrect(answer: String): Boolean {
                var isCorrect = true
                answer.forEach { if(it.isDigit()) isCorrect = false }
                return isCorrect
            }
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion(): Question = SERIAL
            override fun condition(): String = "Год моего рождения должен содержать только цифры"
            override fun isCorrect(answer: String): Boolean {
                var isCorrect = true
                answer.forEach { if(!it.isDigit()) isCorrect = false }
                return isCorrect
            }
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion(): Question = IDLE
            override fun condition(): String = "Серийный номер содержит только цифры, и их 7"
            override fun isCorrect(answer: String): Boolean {
                var isCorrect = true
                answer.forEach { if(!it.isDigit()) isCorrect = false }
                if(answer.length != 7) isCorrect = false
                return isCorrect
            }
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun nextQuestion(): Question = IDLE
            override fun condition(): String? = null
            override fun isCorrect(answer: String): Boolean = true

        };

        abstract fun nextQuestion(): Question

        abstract fun condition(): String?

        abstract fun isCorrect(answer: String): Boolean
    }
}
