package com.jiangli.doc.sql.helper.zhihuishu

import com.jiangli.doc.sql.DataQueryer
import com.jiangli.doc.sql.NamedSimpleCachedTableQueryer


/**
 *
 *
 * @author Jiangli
 * @date 2018/9/26 13:34
 */
//智慧树
class UserIdQueryer : NamedSimpleCachedTableQueryer("用户(${'$'}{params})详情", arrayOf("userId", "CREATOR_ID", "CREATE_USER", "CREATE_PERSON", "COMMENT_USER_ID", "A_USER_ID"), "db_G2S_OnlineSchool.TBL_USER", "ID as usid,USERNAME,REAL_NAME,NICK_NAME,PASSWORD,E_MAIL,CREATE_TIME,UPDATE_TIME,LAST_LOGIN_TIME,LOGIN_COUNT", "ID=${'$'}{params}")

class UserIdClassesQueryer : NamedSimpleCachedTableQueryer("入班(${'$'}{params})", arrayOf("userId"), "db_G2S_OnlineSchool.STUDENT", "PERSON_STUDENT_ID,COURSE_ID as courseId,STUDENT_ID,RECRUIT_ID as recruitId,SCHOOL_ID,SCHOOL_NAME,IS_DELETE", "STUDENT_ID=${'$'}{params}")

class RecruitIdQueryer : NamedSimpleCachedTableQueryer("招生(${'$'}{params})详情", arrayOf("recruitId"), "db_G2S_OnlineSchool.V2_RECRUIT", "CODE,NAME,START_TIME,END_TIME", "ID=${'$'}{params}")
class CourseIdQueryer : NamedSimpleCachedTableQueryer("课程(${'$'}{params})详情", arrayOf("courseId","rootCourseId"), "db_G2S_OnlineSchool.TBL_COURSE", "NAME,SCHOOL_NAME,TEACHER_NAME,ROOT_COURSE_ID", "COURSE_ID=${'$'}{params}")

class UserQuestionsQueryer : NamedSimpleCachedTableQueryer("提出的问题(${'$'}{params})", arrayOf("userId"), "ZHS_BBS.QA_QUESTION", "QUESTION_ID as questionId", "CREATE_USER=${'$'}{params}  ORDER BY CREATE_TIME desc")
class UserAnswersQueryer : NamedSimpleCachedTableQueryer("提出的回答(${'$'}{params})", arrayOf("userId"), "ZHS_BBS.QA_ANSWER", "ID as answerId", "A_USER_ID=${'$'}{params}  ORDER BY CREATE_TIME desc")
class UserCommentsQueryer : NamedSimpleCachedTableQueryer("提出的评论(${'$'}{params})", arrayOf("userId"), "ZHS_BBS.QA_COMMENT", "ID as commentId", "COMMENT_USER_ID=${'$'}{params}  ORDER BY CREATE_TIME desc")

class QuestionIdQueryer : NamedSimpleCachedTableQueryer("问题(${'$'}{params})详情", arrayOf("questionId"), "ZHS_BBS.QA_QUESTION", "QUESTION_ID as _questionId,RECRUIT_ID as recruitId,ANCESTOR_COURSE_ID as rootCourseId,COURSE_ID as courseId,ZHS_BBS.QA_QUESTION.*", "QUESTION_ID=${'$'}{params}")
class QuestionAnswersQueryer : NamedSimpleCachedTableQueryer("问题下回答(${'$'}{params})", arrayOf("_questionId"), "ZHS_BBS.QA_ANSWER", "ID as answerId", "Q_ID=${'$'}{params}  ORDER BY CREATE_TIME asc")
class AnswerIdQueryer : NamedSimpleCachedTableQueryer("回答(${'$'}{params})详情", arrayOf("answerId"), "ZHS_BBS.QA_ANSWER", "ID as _answerId,ZHS_BBS.QA_ANSWER.*", "ID=${'$'}{params} ")
class AnswerCommentsQueryer : NamedSimpleCachedTableQueryer("回答下评论(${'$'}{params})", arrayOf("_answerId"), "ZHS_BBS.QA_COMMENT", "ID as commentId", "A_ID=${'$'}{params}  ORDER BY CREATE_TIME asc")
class CommentIdQueryer : NamedSimpleCachedTableQueryer("评论(${'$'}{params})详情", arrayOf("commentId"), "ZHS_BBS.QA_COMMENT", "*", "ID=${'$'}{params}")

val allZhsQueryer = arrayOf<DataQueryer>(
        UserIdQueryer()

        ,UserIdClassesQueryer()

        ,RecruitIdQueryer()
        ,CourseIdQueryer()

        ,UserQuestionsQueryer()
        ,UserAnswersQueryer()
        ,UserCommentsQueryer()

        ,QuestionIdQueryer()
        ,QuestionAnswersQueryer()
        ,AnswerIdQueryer()
        ,AnswerCommentsQueryer()
        ,CommentIdQueryer()
)