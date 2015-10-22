<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/back_page/head.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>babasport-list</title>
    <script type="text/javascript">
        function deleteBatch(pageNo) {
            var num = $("input[name='ids']:checked").size();
            if(!num) {
                alert("请至少选择一个进行操作");
                return;
            }
            if (!confirm("确认删除吗?"))
                return;
            var name=$("input[name='name']").val();
            var isDisplay = $("select[name='isDisplay'] option:selected").val();
            $("#jvForm").attr("action", "/brand/deleteBatch.do?name=" + name + "&isDispaly=" + isDisplay + "&pageNo=" + pageNo);
            $("#jvForm").attr("method", "post");
            $("#jvForm").submit();

        }
        function deleteById(id,pageNo) {
            if (!confirm("确认删除吗?"))
                return;
            var name=$("input[name='name']").val();
            var isDisplay = $("select[name='isDisplay'] option:selected").val();
            $("#jvForm").attr("action", "/brand/deleteById.do?id=" + id + "&name=" + name + "&isDispaly=" + isDisplay + "&pageNo=" + pageNo);
            $("#jvForm").attr("method", "post");
            $("#jvForm").submit();
        }

        function checkBox(ids,checked) {
            $("input[name='ids']").attr("checked", checked);
        }
    </script>
</head>
<body>
<div class="box-positon">
    <div class="rpos">当前位置: 品牌管理 - 列表</div>
    <form class="ropt">
        <input class="add" type="button" value="添加" onclick="window.location.href='/brand/toAdd.do'"/>
    </form>
    <div class="clear"></div>
</div>
<div class="body-box">
    <form action="/brand/list.do" method="post" style="padding-top:5px;">
        品牌名称: <input type="text" name="name" value="${name}"/>
        <select name="isDisplay">
            <option value="1" <c:if test="${isDisplay ==1 }">selected="selected"</c:if>>是</option>
            <option value="0" <c:if test="${isDisplay ==0 }">selected="selected"</c:if>>不是</option>
        </select>
        <input type="submit" class="query" value="查询"/>
    </form>
    <form id="jvForm">
        <table cellspacing="1" cellpadding="0" border="0" width="100%" class="pn-ltable">
            <thead class="pn-lthead">
            <tr>
                <th width="20"><input type="checkbox" onclick="checkBox('ids',this.checked)"/></th>
                <th>品牌ID</th>
                <th>品牌名称</th>
                <th>品牌图片</th>
                <th>品牌描述</th>
                <th>排序</th>
                <th>是否可用</th>
                <th>操作选项</th>
            </tr>
            </thead>
            <tbody class="pn-ltbody">
            <c:forEach items="${pagination.list}" var="brand">
                <tr bgcolor="#ffffff" onmouseout="this.bgColor='#ffffff'" onmouseover="this.bgColor='#eeeeee'">
                    <td><input type="checkbox" value="${brand.id }" name="ids"/></td>
                    <td align="center">${brand.id }</td>
                    <td align="center">${brand.name }</td>
                    <td align="center"><img width="40" height="40" src="${brand.allUrl}"/></td>
                    <td align="center"></td>
                    <td align="center">${brand.sort}</td>
                    <td align="center">
                        <c:if test="${brand.isDisplay == 1 }">是</c:if>
                        <c:if test="${brand.isDisplay == 0 }">否</c:if>
                    </td>
                    <td align="center">
                        <a class="pn-opt" href="/brand/toEdit.do?id=${brand.id}">修改</a> | <a class="pn-opt"
                                                               onclick="deleteById('${brand.id}','${pagination.pageNo}')" href="#">删除</a>
                    </td>
                </tr>

            </c:forEach>
            </tbody>
        </table>
    </form>
    <div class="page pb15">
	<span class="r inb_a page_b">
        <c:forEach items="${pagination.pageView }" var="page">
            ${page }
        </c:forEach>
    </span>
    </div>
    <div style="margin-top:15px;"><input class="del-button" type="button" value="删除" onclick="deleteBatch('${pagination.pageNo}');"/></div>
</div>
</body>
</html>