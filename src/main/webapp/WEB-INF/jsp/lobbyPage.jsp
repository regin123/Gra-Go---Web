<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <!--bootstrap css-->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <!--dolaczamy plik css z opisem graficznym strony-->
    <link href="${pageContext.request.contextPath}/css/basicStyleSheet.css" rel="stylesheet" type="text/css"/>
    <title>Lobby</title>
</head>
<body>
<%
    String errorVariable = "";
    if (request.getParameter("gameInProgress") != null) {
        errorVariable = "Zajęte";
    }
%>
<%= errorVariable%>
<header>LOBBY</header>
<br/>
<div class="row">
    <div class="col-sm-4">
        <section class="sekcjaLobby">
            <h2>
                ACTIVE GAMES
            </h2>
            <button class="przyciskMenu" onclick="location.href='/game/create?bot=false'">
                CREATE GAME
            </button>
            <br/>
            <button class="przyciskMenu" onclick="playWithCp()">
                PLAY AGAINST CP
            </button>
            <br/>
        </section>
    </div>
    <div class="col-sm-8">
        <section class="sekcjaLobby">
            <h2>
                LOBBY LIST
            </h2>
            <c:forEach items="${gamesList}" var="gameData">
                <button class="przyciskLobby" onclick="location.href='/game/join/${gameData.gameID}'">
                    <!-- onclick="chooseGame(this.getText())" -->
                        ${gameData.username}
                </button>
            </c:forEach>
        </section>
    </div>
</div>
<!--dolaczam java script-->
<script src="${pageContext.request.contextPath}/js/scriptLobby.js"></script>
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