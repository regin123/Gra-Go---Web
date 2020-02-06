<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <!--bootstrap css-->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <!--dolaczamy plik css z opisem graficznym strony-->
    <link href="${pageContext.request.contextPath}/css/basicStyleSheet.css" rel="stylesheet" type="text/css"/>
    <title>Game</title>
</head>
<body>
<header>GAME</header>
<br/>

<input type="hidden" id="ID" value="${gameID}">

<div class="row">
    <div class="col-sm-3">
        <section class="sekcjaGame">
            <h2>
                TOOLS
            </h2>
            <button class="przyciskMenu" onclick="location.href='/game/lobby'">
                RETURN
            </button>
            <br/>
            <label class="specialLabel">
                PLAYER:
            </label>
            <br/>
            <label class="specialLabel">
                POINTS:
            </label>
            <br/>
            <label id="labelPlayerScore" class="specialLabel">
                0
            </label>
            <br>
            <label class="labelSpecialLabel" class="specialLabel">
                ENEMY POINTS
            </label>
            <br>
            <label id="labelEnemyScore" class="specialLabel">
                0
            </label>
            <br/>
            <button class="przyciskMenu" onclick=sendMove(0,0,'PASS')>
                PAUSE
            </button>
            <button class="przyciskMenu" onclick=sendMove(0,0,'SURRENDER')>
                SURRENDER
            </button>
        </section>
    </div>
    <div class="col-sm-9">
        <section class="sekcjaGame" id="sekcjaGameBoard">
            <h2>
                GAME BOARD
            </h2>
            <table id="gameBoard">

            </table>
        </section>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/stomp.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
<!--dolaczam java script-->
<script src="${pageContext.request.contextPath}/js/scriptGame.js"></script>
<!--bootstrap js-->
<script
        src="http://code.jquery.com/jquery-3.4.1.min.js"
        integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>
</body>
</html>