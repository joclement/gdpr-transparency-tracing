<template>
  <div class="game">
    <div id="game-website">
      <div id="game-container-container">
        <div id="game-container"></div>
      </div>
      <div id="score-container">
        <div id="personal-best-container">
          Your Personal Best: {{personalBestPoints}}
        </div>
        <div id="current-score">
          Your current score: {{score}}
        </div>
        <div id="overall-highscore">
          Overall Highscore: {{overallHighscorePoints}}
        </div>
      </div>
    </div>
    <div id="endScreenTemplate" v-if="showEndScreen">
      <div id="endScreen">
        <div>Congratulations! You got {{score}} points
        </div>
        <button id="restartButton">Restart</button>
        <button id="saveScoreButton">Save Score</button>
      </div>
    </div>
    <div id="pauseScreenTemplate" v-if="showPauseScreen">
      <div id="pauseScreen">
        Congratulations! You got {{score}} points
      </div>
      <button id="continueButton">Restart</button>
      <button id="endGameButton">Save Score</button>
    </div>

  </div>
</template>

<script>
  import Header from "./Header";
  import Nav from "./Nav";

  export default {
    name: "Game",
    components: {Nav, Header},
    data() { return {
      score: 0,
      personalBestPoints: 0,
      overallHighscorePoints: 0
    }
    },
    mounted() {
      this.createGame({rowCount: 20});
    },
    methods: {
      createGame({rowCount: rowCount}) {
        let container;
        let gameField;
        //let personalBest;
        //let currentScore;
        //let overallHighscore;

        let cellwidth = Math.floor(100 / rowCount) + "%";
        let cellheight = cellwidth;
        let startValue = Math.round(rowCount / 2);

        let gameValues = {};
        gameValues.length = 0;

        //todo remove after development
        /*document.getElementById("addLength").addEventListener("click", function(){
            gameValues.length ++;
        });
        document.getElementById("removeLength").addEventListener("click", function(){
            gameValues.length --;
        });
        */

        setParameters();

        window.addEventListener("keypress", directionChange);
        window.addEventListener("keypress", exitGame);

        startGame({x: startValue, y: startValue});

        function GameLostException(message, points) {
          this.message = message;
          this.name = "GameLostException";
          this.points = points;
        }

        function GamePauseException(message, points) {
          this.message = message;
          this.name = "GamePauseException";
          this.points = points;
        }

        function resetGame() {

          setParameters();
          gameField.innerHTML = "";
          //text.innerHTML = "";

          startGame({x: startValue, y: startValue});

        }

        function createGraphics() {


          container = document.getElementById("game-container");
          //personalBest = document.getElementById("personal-best-points");

          gameField = document.createElement("div");
          gameField.id = "game-field";
          container.appendChild(gameField);

          gameField.style.width = "100%";
          //gameField.style.height = "100%";
          gameField.style.position = "absolute";
          gameField.style.top = "0";
          gameField.style.bottom = "0";


          //getSavedPoints(); todo add when spring boot backend is ready
          getHighscore();


          for (let i = 0; i < rowCount; i++) {
            let row = document.createElement("div");
            for (let j = 0; j < rowCount; j++) {
              let cell = document.createElement("div");
              cell.style.width = cellwidth;
              cell.style.height = "100%";
              cell.className = "gamecell";
              row.appendChild(cell);
            }
            row.style.height = cellheight;
            gameField.appendChild(row);
          }

        }

        function setParameters() {
          gameValues.direction = 1;
          gameValues.snake = []; //contains {x: ..., y:...}
          gameValues.foodList = []; //contains {x: ..., y:...}
          gameValues.length = 1;
          gameValues.points = 0;
          gameValues.rowCount = rowCount;
        }

        function setSnake({x: xValue, y: yValue}) {
          if (!gameValues.snake.find(snakeElem => snakeElem.x === xValue && snakeElem.y === yValue)) {       //ist das schön?
            gameValues.snake.push({x: xValue, y: yValue});
          }
          paintCell({x: xValue, y: yValue, color: "red"});
        }

        function startGame({x: startValueX, y: startValueY}) {
          createGraphics();
          setSnake({x: startValueX, y: startValueY});
          if (gameValues.snake.length > 1) {
            repaintSnake();
          }
          generateFood();
          let snakeInterval = setInterval(function () {
            try {
              move();
            } catch (e) {
              if (e instanceof GameLostException) {
                clearInterval(snakeInterval);
                endGame();

              }
              if (e instanceof GamePauseException) {
                clearInterval(snakeInterval);
                pauseGame();
              } else {
                console.log(e.stack);
              }
            }
          }, 100);
          console.log("SnakeInterval: " + snakeInterval);
          console.log("started");
        }

        function move() {
          let newPart = calculateNextField();
          gameValues.directionChanged = false;

          checkForGamePauseRequested();
          checkForBiteItself({x: newPart.x, y: newPart.y});
          checkForFood({x: newPart.x, y: newPart.y});

          gameValues.snake = [newPart].concat(gameValues.snake);
          cutSnakeToRightLength();

          paintCell(Object.assign({color: "red"}, gameValues.snake[0]));
          paintCell(Object.assign({color: "darkred"}, gameValues.snake[1]));
          addPoints({additionalPoints: -1});
        }

        function repaintSnake() {
          gameValues.snake.forEach(snakePart => paintCell(Object.assign({color: "red"}, snakePart)));
        }

        function calculateNextField() {
          let newPart = Object.assign({}, gameValues.snake[0]);
          if (gameValues.direction === -1) {
            newPart.y--;
          } else if (gameValues.direction === 1) {
            newPart.y++;
          } else if (gameValues.direction === -2) {
            newPart.x--;
          } else if (gameValues.direction === 2) {
            newPart.x++;
          }

          if (newPart.y > rowCount - 1) newPart.y = 0;
          if (newPart.y < 0) newPart.y = rowCount - 1;

          if (newPart.x > rowCount - 1) newPart.x = 0;
          if (newPart.x < 0) newPart.x = rowCount - 1;

          return newPart;
        }

        function checkForGamePauseRequested() {
          if (gameValues.gamePauseRequested) {
            gameValues.gamePauseRequested = false;
            throw new GamePauseException("GamePauseRequested", gameValues.points);
          }

        }

        function xAndYAreInArray({x: x, y: y, array: array}) {
          return !array.every(arrayPart => ((arrayPart.x !== x) || (arrayPart.y !== y)));
        }

        function checkForBiteItself({x: checkX, y: checkY}) {
          if (xAndYAreInArray({x: checkX, y: checkY, array: gameValues.snake})) {
            throw new GameLostException("Congratulations! You got " + gameValues.points + " points!", gameValues.points);
          }
        }

        function checkForFood({x: checkX, y: checkY}) {
          if (xAndYAreInArray({x: checkX, y: checkY, array: gameValues.foodList})) {
            console.log("found food");
            deleteFood({x: checkX, y: checkY});
            addLength();
            generateFood();
          }
        }

        function addPoints({additionalPoints: p}) {
          gameValues.points += p;
          this.score = gameValues.points;

        }

        function addLength(l = 1) {
          gameValues.length += l;
          addPoints({additionalPoints: l * 100});
          console.log("new length: " + gameValues.length);
        }

        function generateFood(numberOfTries = 0) {          //todo: ein bisschen schäbig, lieber abbruchbedingung woanders!
          if (numberOfTries > 20) {
            console.log("tried to often to generate food");
            return;
          }

          let foodX = Math.round(Math.random() * (gameValues.rowCount - 1));
          let foodY = Math.round(Math.random() * (gameValues.rowCount - 1));


          if (xAndYAreInArray({x: foodX, y: foodY, array: gameValues.snake})) {
            generateFood(numberOfTries + 1);
          } else {
            gameValues.foodList.push({x: foodX, y: foodY});
            paintCell({x: foodX, y: foodY, color: "green"});
            console.log("New food at X:" + foodX + ", Y:" + foodY);
          }
        }

        function deleteFood({x: foodX, y: foodY}) {
          gameValues.foodList = gameValues.foodList.filter(food => food.x !== foodX && food.y !== foodY);
          unPaintCell({x: foodX, y: foodY});
        }

        function paintCell({x: xValue, y: yValue, color: c}) {
          getElement({x: xValue, y: yValue}).style.backgroundColor = c;
        }

        function unPaintCell({x: xValue, y: yValue}) {
          getElement({x: xValue, y: yValue}).style.backgroundColor = "";
        }

        function getElement({x: xValue, y: yValue}) {
          return gameField.childNodes[xValue].childNodes[yValue];
        }

        function directionChange(e) {
          if (gameValues.directionChanged) return;
          gameValues.directionChanged = true;
          e = e || window.event;
          let keynum = e.keyCode || e.which;
          let key = String.fromCharCode(keynum);
          if (gameValues.direction !== 2 && (key === "w" || key === "W")) {
            gameValues.direction = -2;
          } else if (gameValues.direction !== -1 && (key === "d" || key === "D")) {
            gameValues.direction = 1;
          } else if (gameValues.direction !== -2 && (key === "s" || key === "S")) {
            gameValues.direction = 2;
          } else if (gameValues.direction !== 1 && (key === "a" || key === "A")) {
            gameValues.direction = -1;
          }
          console.log(gameValues.direction);
        }

        function exitGame(e) {
          if (e.keyCode === 27) {
            gameValues.gamePauseRequested = true;
          }

        }

        function cutSnakeToRightLength() {
          while (gameValues.snake.length > gameValues.length) {   //'while' helps against bug but do not solve it
            let lostElem = gameValues.snake.pop();
            unPaintCell(lostElem);
          }
        }

        function endGame() {
          if ('content' in document.createElement('template')) {
            let endScreen = document
              .querySelector("#endScreenTemplate")
              .content;
            container.innerHTML = "";
            container.appendChild(document.importNode(endScreen, true));
            //document.getElementById("finalScore").innerHTML = gameValues.points;

            document.getElementById("restartButton").addEventListener("click", resetGame);
            document.getElementById("saveScoreButton").addEventListener("click", savePoints);
            //resetGame();

          } else {
            alert("Your Browser is not able to play the game properly.");
            resetGame();
          }

        }

        function pauseGame() {                            //it's boilerplate!! (see showEndScreen)
          if ('content' in document.createElement('template')) {
            let pauseScreen = document
              .querySelector("#pauseScreenTemplate")
              .content;
            container.innerHTML = "";
            container.appendChild(document.importNode(pauseScreen, true));
            document.getElementById("pauseScore").innerHTML = gameValues.points;

            document.getElementById("continueButton").addEventListener("click", function () {

              startGame(gameValues.snake[0]);
              //paintCell({ x: gameValues.snake[0].x, y: gameValues.snake[0].y, color: "blue"});
            });
            document.getElementById("endGameButton").addEventListener("click", endGame);
            //resetGame();

          } else {
            alert("Your Browser is not able to play the game properly.");
            resetGame();
          }
        }

        function savePoints() {
          console.log("trying to save points");
          makeRequest({
            method: "POST",
            url: "/game/saveScore",
            contentType: "application/json",
            content: JSON.stringify({points: gameValues.points})
          }).catch(function (reason) {
            console.log(reason);
          }).then(getSavedPoints);

        }

        function makeRequest({method: method, url: url, content: content, contentType: contentType}) { // code by SomeKittens/Stackoverflow, modified by me, https://stackoverflow.com/questions/30008114/how-do-i-promisify-native-xhr
          return new Promise(function (resolve, reject) {
            var xhr = new XMLHttpRequest();
            xhr.open(method, url);
            xhr.onload = function () {
              if (this.status >= 200 && this.status < 300) {
                resolve(xhr.response);
              } else {
                reject({
                  status: this.status,
                  statusText: xhr.statusText
                });
              }
            };
            xhr.onerror = function () {
              reject({
                status: this.status,
                statusText: xhr.statusText
              });
            };
            if (contentType) {
              xhr.setRequestHeader("Content-type", contentType);
            }
            if (content === undefined) {
              xhr.send();
            } else {
              xhr.send(content);
            }
          });
        }

        function getSavedPoints() {
          return makeRequest({method: "GET", url: "/game/saveScore"})
            .then(function (res) {
              let response = JSON.parse(res);
              if (response.points) {
                this.personalBestPoints = response.points;
              } else {
                this.personalBestPoints = 0;
              }
            }).then(function (points) {
              gameValues.prevBest = points;
            }).catch(function (reason) {
              console.log(reason);
            });
        }

        function getHighscore() {
          return makeRequest({method: "GET", url: "/game/highscores"})
            .then(function (res) {
              let response = JSON.parse(res);
              if (response && response[0]) {
                this.overallHighscorePoints = response[0].highscore; //todo make sure it is the highest highscore
              } else {
                this.overallHighscorePoints = 0;
              }
            }).catch(function (reason) {
              console.log(reason);
            })
        }
      }
    }
  }
</script>

<style>
  #score-container{
    grid-area: score-container;
    text-align: left;
    border: dashed grey 1px;
  }

  #game-container-container {
    box-sizing: border-box;
    grid-area: game-container;

    font-size : 0;
    border: dashed grey 1px;
    margin: 0 5px 5px;
  }

  #game-container {
    width : 100%;
    height: 0;
    padding-bottom: 100%;
    position: relative;
    background-color: black;
  }

  #endScreen, #pauseScreen {
    font-size: large;
    font-family: sans-serif;
    color: red;
    text-align: center;
  }


  .gamecell {
    display: inline-block;
    background-color: black;
  }

  @media (min-width: 1000px) {
    #game-website {
      width: 900px;
      margin: 0 auto;
      display: grid;
      grid-template-columns: 1fr 7fr 2fr 1fr;
      background-color: lightgrey;
      grid-template-areas:
                "header header header header"
                "nav game-container score-container .";
    }
  }

  @media (max-width: 999px) {
    #game-website {
      width: 90%;
      margin: 0 auto;
      display: grid;
      grid-template-columns: 2fr 6fr 2fr 1fr;
      background-color: lightgrey;
      grid-template-areas:
                "header header header header"
                "nav nav nav nav"
                "game-container game-container game-container score-container";
    }

    nav ul li {
      display: inline;
    }

    h1 {
      margin: 0;
    }

  }
</style>
