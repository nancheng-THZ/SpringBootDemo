<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8" isELIgnored="false"%>
 
<div style="margin:0px auto; width:500px">


    <form action="/updateDo" method="post">
        <input name="id" type="hidden" value="${user.id}">
        用户名：<input type="text" name="userName" value="${user.userName}"><br>
        密 码 ：<input type="password" name="passWord" value="${user.passWord}"><br>
        身 份 ：<input type="text" name="realName" value="${user.realName}"><br>
        <input type="submit" value="修改" >
    </form>






 
</form>
</div>