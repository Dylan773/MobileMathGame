package com.example.mathgame.model

import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/* Database Configuration */
private const val DATABASE_NAME = "MobileAppDatabase.db"
private const val VERSION_NUMBER = 1

/**
 *
 */
class MathDataBase(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION_NUMBER) {

    /* QuestionID Table */
    val questionIDTableName = "QuestionID"
    val questionIDColumn_ID = "ID"
    val column_QuestionType = "QuestionType"
    val column_QuestionDescription = "Description"

    /* Question Table */
    val questionTableName = "Questions"
    val questionColumn_ID = "ID"
    val column_QuestionTypeID = "QuestionTypeID"
    val column_QuestionNumber = "QuestionNumber"
    val column_QuestionText = "QuestionText"

    /* Answer Table */
    val answerTableName = "Answers"
    val answerColumn_ID = "ID"
    val column_QuestionNumberID = "QuestionNumberID"
    val column_AnswerText = "AnswerText"
    val column_IsCorrect = "IsCorrect"


    /**
     *
     */
    override fun onCreate(db: SQLiteDatabase?) {
        try {
            // QuestionID Table
            var sqlCreateStatement: String =
                "CREATE TABLE " + questionIDTableName + " ( " + questionIDColumn_ID +
                        " INTEGER PRIMARY KEY AUTOINCREMENT, " + column_QuestionType + " INTEGER NOT NULL, " + column_QuestionDescription + " TEXT NOT NULL )"

            db?.execSQL(sqlCreateStatement)

            // Question Table
            sqlCreateStatement = "CREATE TABLE " + questionTableName + " ( " + questionColumn_ID +
                    " INTEGER PRIMARY KEY AUTOINCREMENT, " + column_QuestionTypeID + " INTEGER NOT NULL, " + column_QuestionNumber + " INTEGER NOT NULL, " +
                    column_QuestionText + " TEXT NOT NULL )"

            db?.execSQL(sqlCreateStatement)

            // Answer Table
            sqlCreateStatement = "CREATE TABLE " + answerTableName + " ( " + answerColumn_ID +
                    " INTEGER PRIMARY KEY AUTOINCREMENT, " + column_QuestionNumberID + " INTEGER NOT NULL, " + column_AnswerText + " TEXT NOT NULL, " +
                    column_IsCorrect + " INTEGER NOT NULL )"

            db?.execSQL(sqlCreateStatement)

        } catch (e: SQLException) {
        }
    }

    /**
     *
     */
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    /**
     *
     */
    fun getQuestionIDList(): ArrayList<QuestionID> {
        val questionIDList = ArrayList<QuestionID>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $questionIDTableName"

        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        if (cursor.moveToFirst())
            do {
                val id: Int = cursor.getInt(0)
                val questionType: Int = cursor.getInt(1)
                val questionDescription: String = cursor.getString(2)

                val q = QuestionID(id, questionType, questionDescription)
                questionIDList.add(q)
            } while (cursor.moveToNext())

        cursor.close()
        db.close()

        return questionIDList
    }

    fun getQ() {

    }
    /**
     *
     */
    fun getAllQuestions(): ArrayList<Question> {
        val questionList = ArrayList<Question>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $questionTableName"

        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        if (cursor.moveToFirst())
            do {
                val id: Int = cursor.getInt(0)
                val questionTypeID: Int = cursor.getInt(1)
                val questionNumber: Int = cursor.getInt(2)
                val questionText: String = cursor.getString(3)

                val q = Question(id, questionTypeID, questionNumber, questionText)
                questionList.add(q)
            } while (cursor.moveToNext())

        cursor.close()
        db.close()

        return questionList
    }

    /**
     *
     */
    fun getAllAnswers(): ArrayList<Answer> {
        val answerList = ArrayList<Answer>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $answerTableName"

        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        if (cursor.moveToFirst())
            do {
                val id: Int = cursor.getInt(0)
                val questionNumberID: Int = cursor.getInt(1)
                val answerText: String = cursor.getString(2)
                val isCorrect: Int = cursor.getInt(3)

                val a = Answer(id, questionNumberID, answerText, isCorrect)
                answerList.add(a)
            } while (cursor.moveToNext())

        cursor.close()
        db.close()

        return answerList
    }
}

//
//    fun addQuestion() {
//
//    }