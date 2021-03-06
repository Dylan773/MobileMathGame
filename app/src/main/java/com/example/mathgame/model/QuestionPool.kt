package com.example.mathgame.model

import android.content.Context

/**
 * Helper class that contains functions for easy access to database records.
 *
 * @author Dylan Brand
 */
class QuestionPool(context: Context) {
    private val dbHelper: MathDataBase = MathDataBase(context)
    private val questionIDList: ArrayList<QuestionID>
    private val questionList: ArrayList<Question>
    private val answerList: ArrayList<Answer>

    init {
        questionIDList = dbHelper.getQuestionIDList()
        questionList = dbHelper.getAllQuestions()
        answerList = dbHelper.getAllAnswers()
    }

    /**
     * Returns all answers stored in the Answers table.
     *
     * @return All application answers.
     */
    fun getAnswerList(): ArrayList<Answer> {
        return answerList
    }

    /**
     * Accepts a questionID (Int) value, returning the relevant question's topic description.
     *
     * Uses the questionID's value (-1) to obtain the appropriate question's description from the
     * questionID ArrayList.
     *
     * @param questionID The Int value to be compared in the ArrayList.
     * @return The question's topic description.
     */
    fun getQuestionTopicDesc(questionID: Int): String {
        // ArrayList starts at index 0 so - 1
        return questionIDList.get(questionID - 1).questionDescription
    }

    /**
     * Accepts a questionTopicID (Int) value for comparison.
     *
     * Iterates through all application questions, obtaining and adding each question where the
     * questions' topicID matches the provided argument to an ArrayList.
     *
     * The ArrayList is then shuffled to randomise the order of questions, a question is then
     * extracted and removed from that list. Returning the chosen question.
     *
     * @param questionTopicID The value to be compared with each question's topicID.
     * @return A randomised Question that matches the topicID.
     */
    private fun addRandomQuestionByTopic(questionTopicID: Int): Question {
        val topicList = ArrayList<Question>()

        // Filter the questions
        for (q in questionList)
            if (q.questionTypeID == questionTopicID) {
                topicList.add(q)
            }

        topicList.shuffle() // Randomise the list

        val choseQuestion = topicList.get(0) // Obtains the first question in the randomised list
        questionList.remove(choseQuestion) // Removes the question from the main list so its not re-called

        return choseQuestion
    }

    /**
     * Sets the game questions for the current quiz session, extracting 14 questions
     * (2 questions per topic) for user interaction.
     *
     * Uses a helper function to extract relevant questions, adding each question to an ArrayList.
     * The ArrayList is then shuffled and returned, providing users with a random order of
     * questions.
     *
     * @return An ArrayList containing 14 randomised questions, 2 per topic
     * @see addRandomQuestionByTopic
     */
    fun setGameQuestions(): ArrayList<Question> {
        val q = ArrayList<Question>()

        q.add(addRandomQuestionByTopic(1))
        q.add(addRandomQuestionByTopic(1))

        q.add(addRandomQuestionByTopic(2))
        q.add(addRandomQuestionByTopic(2))

        q.add(addRandomQuestionByTopic(3))
        q.add(addRandomQuestionByTopic(3))

        q.add(addRandomQuestionByTopic(4))
        q.add(addRandomQuestionByTopic(4))

        q.add(addRandomQuestionByTopic(5))
        q.add(addRandomQuestionByTopic(5))

        q.add(addRandomQuestionByTopic(6))
        q.add(addRandomQuestionByTopic(6))

        q.add(addRandomQuestionByTopic(7))
        q.add(addRandomQuestionByTopic(7))

        q.shuffle() // Randomise all 14 chosen questions
        return q
    }
}