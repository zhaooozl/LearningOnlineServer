package com.example.learn.servlet.common;

import com.alibaba.fastjson.JSON;
import com.example.learn.entity.base.BaseRespBean;
import com.example.learn.entity.common.CommonStatusBean;
import com.example.learn.servlet.BaseServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class QuestionServlet extends BaseServlet {

    @Override
    public void onDoGet(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String operateType = req.getParameter("operateType");
        String subjectId = req.getParameter("subjectId");
        String questionType = req.getParameter("questionType");
        String score = req.getParameter("score");
        String question = req.getParameter("question");
        String questionId = req.getParameter("questionId");
        String optiona = req.getParameter("optiona");
        String optionb = req.getParameter("optionb");
        String optionc = req.getParameter("optionc");
        String optiond = req.getParameter("optiond");

        if ("insert".equals(operateType)) { //插入
//            String examPaperId = req.getParameter("examPaperId");

            String sql = "INSERT INTO questions " +
                    "(subjectid, questiontype, score, title, optiona, optionb, optionc, optiond)" +
                    " VALUES " +
                    "(" + subjectId + ", " +
                    questionType + ", " +
                    score + ", \"" +
                    question + "\", \"" +
                    optiona + "\", \"" +
                    optionb + "\", \"" +
                    optionc + "\", \"" +
                    optiond + "\"" +
                    ")";
            System.out.println(sql);
            CommonStatusBean commonStatusBean = new CommonStatusBean();
            boolean execute = statement.execute(sql);
            if (!execute) {
                commonStatusBean.setCode(0);
                commonStatusBean.setMsg("添加成功");
            } else {
                commonStatusBean.setCode(-1);
                commonStatusBean.setMsg("添加失败");
            }
            BaseRespBean<CommonStatusBean> respBean = new BaseRespBean<>();
            respBean.setCode(200);
            respBean.setMsg("success");
            respBean.setData(commonStatusBean);
            resp.getWriter().print(JSON.toJSONString(respBean));


        } else if ("update".equals(operateType)) { //更新


            String sql = "UPDATE questions SET " +
                    "questiontype=" + questionType + ", " +
                    "score=" + score + ", " +
                    "title=\"" + question + "\", " +
                    "optiona=\"" + optiona + "\", " +
                    "optionb=\"" + optionb + "\", " +
                    "optionc=\"" + optionc + "\", " +
                    "optiond=\"" + optiond + "\" " +
                    "WHERE questionId=" + questionId
                    ;

            System.out.println(sql);

            CommonStatusBean commonStatusBean = new CommonStatusBean();
            int i = statement.executeUpdate(sql);
            if (1 == i) {
                commonStatusBean.setCode(0);
                commonStatusBean.setMsg("修改成功");
            } else {
                commonStatusBean.setCode(-1);
                commonStatusBean.setMsg("修改失败");
            }

            BaseRespBean<CommonStatusBean> respBean = new BaseRespBean<>();
            respBean.setCode(200);
            respBean.setMsg("成功");
            respBean.setData(commonStatusBean);
            resp.getWriter().print(JSON.toJSONString(respBean));
        } else if ("delete".equals(operateType)) { //更新

            String sql = "DELETE FROM questions WHERE questionid=" + questionId;
            System.out.println(sql);
            boolean execute = statement.execute(sql);
            CommonStatusBean commonStatusBean = new CommonStatusBean();
            if (!execute) {
                commonStatusBean.setCode(0);
                commonStatusBean.setMsg("删除成功");
            } else {
                commonStatusBean.setCode(-1);
                commonStatusBean.setMsg("删除失败");
            }

            BaseRespBean<CommonStatusBean> respBean = new BaseRespBean<>();
            respBean.setCode(200);
            respBean.setMsg("success");
            respBean.setData(commonStatusBean);
            resp.getWriter().print(JSON.toJSONString(respBean));

        }

    }


    private void insert() {

    }

    @Override
    public void onDoPost(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {

    }
}
