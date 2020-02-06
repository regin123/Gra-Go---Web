<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <!--bootstrap css-->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <!--dolaczamy plik css z opisem graficznym strony-->
    <link href="${pageContext.request.contextPath}/css/basicStyleSheet.css" rel="stylesheet" type="text/css"/>
    <title>Spring Security Example </title>
    <title>Register</title>
</head>
<body>
<!--error -->
<%
    String errorVariable = "";
    if (request.getParameter("userexists") != null) {
        errorVariable = "Istnieje";
    } else if (request.getParameter("baddata") != null) {
        errorVariable = "Zle dane";
    }
%>
<%= errorVariable%>
<header>REGISTER PAGE</header>
<br/>
<section id="sekcjaRegister">
    <form:form action="/register" method="POST" modelAttribute="loginData">
        <div><form:label path="username"> User Name : <form:input path="username" type="text" name="username"/>
        </form:label></div>
        <div><form:label path="password"> Password: <form:input path="password" type="password" name="password"/>
        </form:label></div>
        <div><input type="submit" value="REGISTER"/></div>
    </form:form>
</section>
<!--bootstrap js-->
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
        integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>
</body>
</html>