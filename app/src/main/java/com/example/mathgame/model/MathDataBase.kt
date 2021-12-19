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

    fun getAdmin(admin: Admin): Int {

        val db: SQLiteDatabase
        try {
            db = this.readableDatabase
        } catch (e: SQLiteException) {
            return -2 // Database error
        }

        val userName = admin.userName.lowercase()
        val userPassword = admin.password
        //val sqlStatement = "SELECT * FROM $TableName WHERE $Column_UserName = $userName AND $Column_Password = $userPassword"

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

//    fun addQuestion(question: Question): Int {
//        val isUserNameAlreadyExists =
//            checkUserName(user) // check if the username is already exist in the database
//        if (isUserNameAlreadyExists < 0)
//            return isUserNameAlreadyExists
//
//        // writableDatabase for insert actions
//        val db: SQLiteDatabase = this.writableDatabase
//        val cv: ContentValues = ContentValues()
//
//        cv.put(Column_FirstName, user.firstName)
//        cv.put(Column_LastName, user.LastName)
//        cv.put(Column_Age, user.age)
//        cv.put(Column_Gender, user.gender)
//        cv.put(Column_Address, user.address)
//        cv.put(Column_UserName, user.userName.lowercase())
//        cv.put(Column_Password, user.password)
//
//        val success = db.insert(TableName, null, cv)
//
//        db.close()
//        if (success.toInt() == -1) return success.toInt() //Error, adding new user
//        else return 1
//    }

    /**
     * Validation isnt neccessary as multiple students can share the same first name.
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
