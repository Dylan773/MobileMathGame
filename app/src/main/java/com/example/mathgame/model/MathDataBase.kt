package com.example.mathgame.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

/* Database Configuration */
private const val DATABASE_NAME = "MobileAppDatabase.db"
private const val VERSION_NUMBER = 1

/**
 * Database Helper Class.
 *
 * @author Dylan Brand
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

    /* Admin Table */
    val adminTableName = "Admin"
    val adminColumn_ID = "ID"
    val column_FirstName = "AdminFirstName"
    val column_UserName = "Username"
    val column_Password = "Password"

    /* Student Table */
    val studentTableName = "Student"
    val studentColumn_ID = "ID"
    val column_FirstNameStudent = "FirstName"
    val column_Score = "Score"
    val column_QuizDate = "QuizDate"


    /**
     * Upon creation, the database helper creates the below tables for application functionality.
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

            // Admin Table
            sqlCreateStatement = "CREATE TABLE " + adminTableName + " ( " + adminColumn_ID +
                    " INTEGER PRIMARY KEY AUTOINCREMENT, " + column_FirstName + " TEXT NOT NULL, " + column_UserName + " TEXT NOT NULL, " +
                    column_Password + " TEXT NOT NULL )"

            db?.execSQL(sqlCreateStatement)

            // Answer Table )))))))
            sqlCreateStatement = "CREATE TABLE " + studentTableName + " ( " + studentColumn_ID +
                    " INTEGER PRIMARY KEY AUTOINCREMENT, " + column_FirstNameStudent + " TEXT NOT NULL, " + column_Score + " INT NOT NULL, " +
                    column_QuizDate + " TEXT NOT NULL )"

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
     * Returns all data in the databases QuestionID table.
     *
     * @return An ArrayList of QuestionID data.
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

    /**
     * Executes a SQL statement, iterating through all data in the Questions table using a cursor.
     * All data obtained from each row within the table is added to a data class to store that data.
     *
     * @return An ArrayList of all application questions.
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
     * Executes a SQL statement, iterating through all data in the Answers table using a cursor.
     * All data obtained from each row within the table is added to a data class to store the data.
     *
     * @return An ArrayList of all application answers.
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

    /**
     * Accepts an Admin object, comparing that admins username and password to data stored in the
     * Admin table. If the username and password matches a record in the table, a positive integer
     * is returned, highlighting a successful match.
     *
     * @param admin The admin object to be comapred.
     * @return An Int value, determine the success of a record match.
     */
    fun getAdmin(admin: Admin): Int {

        val db: SQLiteDatabase
        try {
            db = this.readableDatabase
        } catch (e: SQLiteException) {
            return -2 // Database error
        }

        val userName = admin.userName.lowercase()
        val userPassword = admin.password

        val sqlStatement =
            "SELECT * FROM $adminTableName WHERE $column_UserName = ? AND $column_Password = ?"
        val param = arrayOf(userName, userPassword)
        val cursor: Cursor = db.rawQuery(sqlStatement, param)
        if (cursor.moveToFirst()) {
            // The user is found
            val n = cursor.getInt(0)
            cursor.close()
            db.close()
            return n
        }

        cursor.close()
        db.close()
        return -1 //User not found

    }

    /**
     * Accepts a question object, adding the unique values of that object to the corresponding
     * columns in a new database entry.
     *
     * @param question The question to be added to the database.
     * @return An Int value, determining the success of the database addition.
     */
    fun addQuestion(question: Question): Int {

        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(column_QuestionTypeID, question.questionTypeID)
        cv.put(column_QuestionNumber, question.questionNumber)
        cv.put(column_QuestionText, question.questionText)

        val success = db.insert(questionTableName, null, cv)

        db.close()
        if (success.toInt() == -1) return success.toInt() //Error, adding new user
        else return 1
    }

    /**
     * Accepts five answer objects, adding the unique values of each object to the corresponding
     * columns in the Answers table with each unique database entry. Inserts five unique answers
     * to the Answers table.
     *
     * @return An Int value, determining the success of the database addition.
     */
    fun addAnswerOptions(option1: Answer, option2: Answer, option3: Answer, option4: Answer,
                         option5: Answer): Int {
        var success = 0L

        // Add each provided option to an ArrayList
        val options = ArrayList<Answer>()
        options.add(option1)
        options.add(option2)
        options.add(option3)
        options.add(option4)
        options.add(option5)

        // For every answer provided, add this to a new row in the answers table
        for (o in options) {

            val db: SQLiteDatabase = this.writableDatabase
            val cv: ContentValues = ContentValues()

            cv.put(column_QuestionNumberID, o.questionNumberID)
            cv.put(column_AnswerText, o.answerText)
            cv.put(column_IsCorrect, o.isCorrect)

            success = db.insert(answerTableName, null, cv)
            db.close()
        }

        if (success.toInt() == -1) return success.toInt() //Error, adding new user
        else return 1
    }

    /**
     * Accepts a student object as a parameter, adding that student data to the Student table
     * within the database.
     *
     * @param student The student object to be added to the database
     * @return An Int value, determining the success of the database addition.
     */
    fun addStudentRecord(student: Student): Int {

        // writableDatabase for insert actions
        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(column_FirstNameStudent, student.firstName)
        cv.put(column_Score, student.score)
        cv.put(column_QuizDate, student.quizDate)

        val success = db.insert(studentTableName, null, cv)

        db.close()
        if (success.toInt() == -1)
            return success.toInt() //Error adding user score
        else return 1
    }
}