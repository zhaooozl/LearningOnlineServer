package com.example.learn.servlet.admin;

import com.alibaba.fastjson.JSON;
import com.example.learn.entity.base.BaseRespBean;
import com.example.learn.entity.common.CommonStatusBean;
import com.example.learn.servlet.BaseServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class DeleteCourseServlet extends BaseServlet {


    @Override
    public void onDoGet(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String subjectId = req.getParameter("subjectId");
        final String sql = "DELETE FROM subject WHERE subjectid=" + subjectId;

        CommonStatusBean commonStatusBean = new CommonStatusBean();
        boolean execute = statement.execute(sql);
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

    @Override
    public void onDoPost(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {

    }
}
