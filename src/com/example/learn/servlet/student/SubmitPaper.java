package com.example.learn.servlet.student;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.learn.entity.base.BaseRespBean;
import com.example.learn.entity.common.InsertRespBean;
import com.example.learn.entity.student.exam.SubmitBean;
import com.example.learn.servlet.BaseServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class SubmitPaper extends BaseServlet {


    @Override
    public void onDoGet(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String userId = req.getParameter("userId");
        String questionId = req.getParameter("questionId");
        String answer = req.getParameter("answer");
        String insertAnswer = "INSERT INTO answer (questionid, userid, answer) " +
                "VALUES " +
                "(" + questionId + ", " + userId + ", " + answer + ")";

        boolean execute = statement.execute(insertAnswer);
        BaseRespBean<InsertRespBean> respBean = new BaseRespBean<>();
        respBean.setCode(200);
        respBean.setMsg("success");
        InsertRespBean insertRespBean = new InsertRespBean();
        if (execute) {
            insertRespBean.setCode(0);
            insertRespBean.setMsg("提交成功");
            respBean.setData(insertRespBean);
        } else {
            insertRespBean.setCode(-1);
            insertRespBean.setMsg("提交失败");
            respBean.setData(insertRespBean);
        }
        resp.getWriter().print(JSON.toJSONString(insertRespBean));
    }

    @Override
    public void onDoPost(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        try {
            String str = null;
            reader = req.getReader();
            if ((str = reader.readLine()) != null) {
                buffer.append(str);
            }
            System.out.print(buffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        ArrayList<SubmitBean> submitBeans = JSONObject.parseObject(buffer.toString(), new TypeReference<ArrayList<SubmitBean>>() {
        });

        BaseRespBean<InsertRespBean> respBean = new BaseRespBean<>();
        InsertRespBean insertRespBean = new InsertRespBean();
        StringBuffer insertAnswer =  new StringBuffer("INSERT INTO answer (questionid, userid, answer) VALUES");
        for (SubmitBean s : submitBeans) {
            String userId = s.getUserId();
            String questionId = s.getQuestionId();
            String answer = s.getAnswer();
            System.out.println("userId: " + userId);
            System.out.println("questionId: " + questionId);
            System.out.println("answer: " + answer);

            String temp = " (" + questionId + ", " + userId + ",\"" + answer + "\")";

            String sql = "INSERT INTO answer (questionid, userid, answer) VALUES" +
                    " (" + questionId + ", " + userId + ",\"" + answer + "\")";
            boolean execute = statement.execute(insertAnswer.toString());

            if (execute) {
                insertRespBean.setCode(0);
                insertRespBean.setMsg("提交成功");
                respBean.setData(insertRespBean);
            } else {
                insertRespBean.setCode(-1);
                insertRespBean.setMsg("提交失败");
                respBean.setData(insertRespBean);
            }
//            insertAnswer.append(temp);
        }

        System.out.print(insertAnswer.toString());

        respBean.setCode(200);
        respBean.setMsg("success");
        resp.getWriter().print(JSON.toJSONString(insertRespBean));
    }
}
