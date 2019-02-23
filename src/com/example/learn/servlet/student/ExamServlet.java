package com.example.learn.servlet.student;

import com.alibaba.fastjson.JSON;
import com.example.learn.entity.base.BaseRespBean;
import com.example.learn.entity.student.exam.ExamLayoutBean;
import com.example.learn.entity.student.exam.Option;
import com.example.learn.servlet.BaseServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExamServlet extends BaseServlet {

    @Override
    public void onDoGet(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        final String subjectId = req.getParameter("subjectId");
//        String examPaperId = req.getParameter("examPaperId");
//        final String queryExampaperidSql = "SELECT * FROM exampaper WHERE subjectid=" + subjectId;
//        ResultSet examSet = statement.executeQuery(queryExampaperidSql);
//        int exampaperid = -1;
//        String exampapername = null;
//        while (examSet.next()) {
//            exampaperid = examSet.getInt("exampaperid");
//            exampapername = examSet.getString("exampapername");
//            break;
//        }
//        closeResultSet();

        final String queryQuestions = "SELECT * FROM questions WHERE subjectid=" + subjectId + " ORDER BY questiontype, questionid";
        ResultSet questionsSet = statement.executeQuery(queryQuestions);

        ArrayList<ExamLayoutBean> examLayoutBeans = new ArrayList<>();
        ArrayList<ExamLayoutBean> selectLayoutBeans = new ArrayList<>();
        ArrayList<ExamLayoutBean> judgeLayoutBeans = new ArrayList<>();
        ArrayList<ExamLayoutBean> fillLayoutBeans = new ArrayList<>();

        int totalScore = 0;
        int selectTotalScore = 0;
        int judgeTotalScore = 0;
        int fillTotalScore = 0;

        boolean isExistSelect = false;
        boolean isExistJudge = false;
        boolean isExistfill = false;

        while (questionsSet.next()) {
            String questionid = questionsSet.getString("questionid");
//            int exampaperid1 = questionsSet.getInt("exampaperid");
            int questiontype = questionsSet.getInt("questiontype");
            String title = questionsSet.getString("title");
            int score = questionsSet.getInt("score");
            String optiona = questionsSet.getString("optiona");
            String optionb = questionsSet.getString("optionb");
            String optionc = questionsSet.getString("optionc");
            String optiond = questionsSet.getString("optiond");
            ArrayList<Option> options = null;
            ExamLayoutBean layoutBean = null;
            layoutBean = new ExamLayoutBean();
            layoutBean.setLayouttype(questiontype);
            layoutBean.setScore(score);
            layoutBean.setQuestion(title);
            layoutBean.setQuestionId(questionid);
            if (questiontype == 3) { // 选择题
                options = new ArrayList<>();
                options.add(new Option("A", optiona));
                options.add(new Option("B", optionb));
                options.add(new Option("C", optionc));
                options.add(new Option("D", optiond));
                layoutBean.setOptions(options);
                selectTotalScore += score;
                isExistSelect = true;

                selectLayoutBeans.add(layoutBean);
            } else if (questiontype == 4) {  // 判断题
                options = new ArrayList<>();
                options.add(new Option("A", optiona));
                options.add(new Option("B", optionb));
                layoutBean.setOptions(options);

                judgeTotalScore += score;
                isExistJudge = true;

                judgeLayoutBeans.add(layoutBean);
            } else if (questiontype == 5) {  // 填空题
                fillTotalScore += score;
                isExistfill = true;

                fillLayoutBeans.add(layoutBean);
            }
            totalScore += score;
        }

        // 标题
        ExamLayoutBean titleBean = new ExamLayoutBean();
        titleBean.setExamName("exampapername");
        titleBean.setLayouttype(0);
        titleBean.setScore(totalScore);
        examLayoutBeans.add(titleBean);


        if (isExistSelect) {
            ExamLayoutBean layoutBean = new ExamLayoutBean();
            layoutBean.setTitle("一、选择题（总分：" + selectTotalScore + "）");
            layoutBean.setLayouttype(2);
            examLayoutBeans.add(layoutBean);
            examLayoutBeans.addAll(selectLayoutBeans);
        }
        if (isExistJudge) {
            ExamLayoutBean layoutBean = new ExamLayoutBean();
            layoutBean.setTitle("二、判断题（总分：" + judgeTotalScore + "）");
            layoutBean.setLayouttype(2);
            examLayoutBeans.add(layoutBean);
            examLayoutBeans.addAll(judgeLayoutBeans);
        }
        if (isExistfill) {
            ExamLayoutBean layoutBean = new ExamLayoutBean();
            layoutBean.setTitle("三、填空题（总分：" + fillTotalScore + "）");
            layoutBean.setLayouttype(2);
            examLayoutBeans.add(layoutBean);
            examLayoutBeans.addAll(fillLayoutBeans);
        }
        // 提交button
        ExamLayoutBean submitBean = new ExamLayoutBean();
        submitBean.setLayouttype(1);
        examLayoutBeans.add(submitBean);

        BaseRespBean<ArrayList<ExamLayoutBean>> respBean = new BaseRespBean<>();
        respBean.setCode(200);
        respBean.setMsg("success");
        respBean.setData(examLayoutBeans);

        resp.getWriter().print(JSON.toJSONString(respBean));

    }

    @Override
    public void onDoPost(HttpServletRequest req, HttpServletResponse resp) throws SQLException {

    }
}
