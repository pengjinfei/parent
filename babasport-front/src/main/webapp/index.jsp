<%--
  Created by IntelliJ IDEA.
  User: pengjinfei
  Date: 2015/10/23
  Time: 21:31
  To change this template use File | Settings | File Templates.
--%>
<%@page import="java.net.URLEncoder" %>
<%@ page language="java" pageEncoding="utf-8" %>
<!-- 重定向到检索页面 -->
<%response.sendRedirect("/product/display/list.shtml?keyword=" + URLEncoder.encode("瑜伽服", "UTF-8")); %>
