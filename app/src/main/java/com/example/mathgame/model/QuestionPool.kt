package com.example.mathgame.model

import android.content.Context

//TODO - set the game answers here? so the gameQuiz screen doesnt call all game answers
class QuestionPool(context: Context) {

    private val dbHelper: MathDataBase = MathDataBase(context)

    //private val questionIDList: ArrayList<>
    private val questionList: ArrayList<Question>
    private val answerList: ArrayList<Answer>
    private val questionIDList: ArrayList<QuestionID>


    init {
        questionIDList = dbHelper.getQuestionIDList()
        questionList = dbHelper.getAllQuestions()
        answerList = dbHelper.getAllAnswers()
    }

    /**
     *
     */
    fun getQuestionList(): ArrayList<Question> {
        return questionList
    }

    /**
     *
     */
    fun getAnswerList(): ArrayList<Answer> {
        return answerList
    }

    /**
     * Accepts a questionTopicID (Int) value for comparison.
     * Compares the provided value to all questions in the questionList that contain that value as
     * their topicID.
     *
     * Adds all relevant questions to an ArrayList and shuffles that list, returning one question
     * from the list.
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
        questionList.remove(choseQuestion) // Removes the question from the main list so its not re-used

        return choseQuestion
    }


    /**
     * Accepts a questionID (Int) value, returning the relevant question's topic description.
     *
     * Uses the questionID's value (-1) to obtain the appropriate question's description from the
     * questionID ArrayList.
     *
     * @param questionID The Int value to be compared in the ArrayList
     */
    fun getQuestionTopicDesc(questionID: Int): String {
        return questionIDList.get(questionID - 1).questionDescription // starts a 0 so - 1
    }

    /**
     *
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