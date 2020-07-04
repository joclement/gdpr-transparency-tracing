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
      <button id="continueButton">Continue</button>
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
    data() {
      const rowCount = 20;
      const cellWidth = Math.floor(100 / rowCount) + "%";
      return {
        score: 0,
        personalBestPoints: 0,
        overallHighscorePoints: 0,
        rowCount: rowCount,
        startValue: 20,
        showPauseScreen: false,
        showEndScreen: false,
        container: {},
        gameField: {},
        cellwidth: cellWidth,
        cellheight: cellWidth,
        gameValues: {
          direction: 1,
          snake: [], //contains {x: ..., y:...}
          foodList: [], //contains {x: ..., y:...}
          length: 1,
          points: 0,
          rowCount: rowCount
        },
        gameValuesCopy: this.gameValues
      }
    },
    mounted() {
        this.container = document.getElementById("game-container"); //todo does this fix the problem with this.container is null?
        this.gameField = document.createElement("div");
      this.createGame();
    },
    methods: {
      createGame() {
        //let personalBest;
        //let currentScore;
        //let overallHighscore;

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

        window.addEventListener("keypress", this.directionChange);
        window.addEventListener("keypress", this.exitGame);

        this.startGame({x: this.startValue, y: this.startValue});
      },
      GameLostException(message, points) {
        this.message = message;
        this.name = "GameLostException";
        this.points = points;
      },
      GamePauseException(message, points) {
        this.message = message;
        this.name = "GamePauseException";
        this.points = points;
      },
      resetGame() {
        this.gameValues = this.gameValuesCopy
        this.gameField.innerHTML = "";
        //text.innerHTML = "";

        this.startGame({x: this.startValue, y: this.startValue});
      },
      createGraphics() {


        //personalBest = document.getElementById("personal-best-points");
        this.gameField.id = "game-field";
        this.container.appendChild(this.gameField);

        this.gameField.style.width = "100%";
        //gameField.style.height = "100%";
        this.gameField.style.position = "absolute";
        this.gameField.style.top = "0";
        this.gameField.style.bottom = "0";


        //getSavedPoints(); todo add when spring boot backend is ready
        this.getHighscore();


        for (let i = 0; i < this.rowCount; i++) {
          let row = document.createElement("div");
          for (let j = 0; j < this.rowCount; j++) {
            let cell = document.createElement("div");
            cell.style.width = this.cellwidth;
            cell.style.height = "100%";
            cell.className = "gamecell";
            row.appendChild(cell);
          }
          row.style.height = this.cellheight;
          this.gameField.appendChild(row);
        }

      },
      setSnake({x: xValue, y: yValue}) {
        if (!this.gameValues.snake.find(snakeElem => snakeElem.x === xValue && snakeElem.y === yValue)) {       //ist das schön?
          this.gameValues.snake.push({x: xValue, y: yValue});
        }
        this.paintCell({x: xValue, y: yValue, color: "red"});
      },
      startGame({x: startValueX, y: startValueY}) {
        this.createGraphics();
        this.setSnake({x: startValueX, y: startValueY});
        if (this.gameValues.snake.length > 1) {
          this.repaintSnake();
        }
        this.generateFood();
        let snakeInterval = setInterval(function () {
          try {
            this.move();
          } catch (e) {
            if (e instanceof this.GameLostException) {
              this.clearInterval(snakeInterval);
              this.endGame();

            }
            if (e instanceof this.GamePauseException) {
              clearInterval(snakeInterval);
              this.pauseGame();
            } else {
              console.log(e.stack);
            }
          }
        }, 100);
        console.log("SnakeInterval: " + snakeInterval);
        console.log("started");
      },
      move() {
        let newPart = this.calculateNextField();
        this.gameValues.directionChanged = false;

        this.checkForGamePauseRequested();
        this.checkForBiteItself({x: newPart.x, y: newPart.y});
        this.checkForFood({x: newPart.x, y: newPart.y});

        this.gameValues.snake = [newPart].concat(this.gameValues.snake);
        this.cutSnakeToRightLength();

        this.paintCell(Object.assign({color: "red"}, this.gameValues.snake[0]));
        this.paintCell(Object.assign({color: "darkred"}, this.gameValues.snake[1]));
        this.addPoints({additionalPoints: -1});
      },
      repaintSnake() {
        this.gameValues.snake.forEach(snakePart => this.paintCell(Object.assign({color: "red"}, snakePart)));
      },
      calculateNextField() {
        let newPart = Object.assign({}, this.gameValues.snake[0]);
        if (this.gameValues.direction === -1) {
          newPart.y--;
        } else if (this.gameValues.direction === 1) {
          newPart.y++;
        } else if (this.gameValues.direction === -2) {
          newPart.x--;
        } else if (this.gameValues.direction === 2) {
          newPart.x++;
        }

        if (newPart.y > this.rowCount - 1) newPart.y = 0;
        if (newPart.y < 0) newPart.y = this.rowCount - 1;

        if (newPart.x > this.rowCount - 1) newPart.x = 0;
        if (newPart.x < 0) newPart.x = this.rowCount - 1;

        return newPart;
      },
      checkForGamePauseRequested() {
        if (this.gameValues.gamePauseRequested) {
          this.gameValues.gamePauseRequested = false;
          throw new this.GamePauseException("GamePauseRequested", this.gameValues.points);
        }

      },
      xAndYAreInArray({x: x, y: y, array: array}) {
        return !array.every(arrayPart => ((arrayPart.x !== x) || (arrayPart.y !== y)));
      },
      checkForBiteItself({x: checkX, y: checkY}) {
        if (this.xAndYAreInArray({x: checkX, y: checkY, array: this.gameValues.snake})) {
          throw new this.GameLostException("Congratulations! You got " + this.gameValues.points + " points!", this.gameValues.points);
        }
      },
      checkForFood({x: checkX, y: checkY}) {
        if (this.xAndYAreInArray({x: checkX, y: checkY, array: this.gameValues.foodList})) {
          console.log("found food");
          this.deleteFood({x: checkX, y: checkY});
          this.addLength();
          this.generateFood();
        }
      },
      addPoints({additionalPoints: p}) {
        this.gameValues.points += p;
        this.score = this.gameValues.points; //todo or this? this.gameValues.score = this.gameValues.points;

      },
      addLength(l = 1) {
        this.gameValues.length += l;
        this.addPoints({additionalPoints: l * 100});
        console.log("new length: " + this.gameValues.length);
      },
      generateFood(numberOfTries = 0) {          //todo: ein bisschen schäbig, lieber abbruchbedingung woanders!
        if (numberOfTries > 20) {
          console.log("tried to often to generate food");
          return;
        }

        let foodX = Math.round(Math.random() * (this.gameValues.rowCount - 1));
        let foodY = Math.round(Math.random() * (this.gameValues.rowCount - 1));


        if (this.xAndYAreInArray({x: foodX, y: foodY, array: this.gameValues.snake})) {
          this.generateFood(numberOfTries + 1);
        } else {
          this.gameValues.foodList.push({x: foodX, y: foodY});
          this.paintCell({x: foodX, y: foodY, color: "green"});
          console.log("New food at X:" + foodX + ", Y:" + foodY);
        }
      },
      deleteFood({x: foodX, y: foodY}) {
        this.gameValues.foodList = this.gameValues.foodList.filter(food => food.x !== foodX && food.y !== foodY);
        this.unPaintCell({x: foodX, y: foodY});
      },
      paintCell({x: xValue, y: yValue, color: c}) {
        this.getElement({x: xValue, y: yValue}).style.backgroundColor = c;
      },
      unPaintCell({x: xValue, y: yValue}) {
        this.getElement({x: xValue, y: yValue}).style.backgroundColor = "";
      },
      getElement({x: xValue, y: yValue}) {
        return this.gameField.childNodes[xValue].childNodes[yValue];
      },
      directionChange(e) {
        if (this.gameValues.directionChanged) return;
        this.gameValues.directionChanged = true;
        e = e || window.event;
        let keynum = e.keyCode || e.which;
        let key = String.fromCharCode(keynum);
        if (this.gameValues.direction !== 2 && (key === "w" || key === "W")) {
          this.gameValues.direction = -2;
        } else if (this.gameValues.direction !== -1 && (key === "d" || key === "D")) {
          this.gameValues.direction = 1;
        } else if (this.gameValues.direction !== -2 && (key === "s" || key === "S")) {
          this.gameValues.direction = 2;
        } else if (this.gameValues.direction !== 1 && (key === "a" || key === "A")) {
          this.gameValues.direction = -1;
        }
        console.log(this.gameValues.direction);
      },
      exitGame(e) {
        if (e.keyCode === 27) {
          this.gameValues.gamePauseRequested = true;
        }
      },
      cutSnakeToRightLength() {
        while (this.gameValues.snake.length > this.gameValues.length) {   //'while' helps against bug but do not solve it
          let lostElem = this.gameValues.snake.pop();
          this.unPaintCell(lostElem);
        }
      },
      endGame() {
        this.showEndScreen = true;
        document.getElementById("restartButton").addEventListener("click", this.resetGame);
        document.getElementById("saveScoreButton").addEventListener("click", this.savePoints);
        //resetGame();
      },
      pauseGame() {
        this.showPauseScreen = true;
        document.getElementById("continueButton").addEventListener("click", function () {
          this.startGame(this.gameValues.snake[0]);
          this.showPauseScreen = false;
          //paintCell({ x: gameValues.snake[0].x, y: gameValues.snake[0].y, color: "blue"});
        });
        document.getElementById("endGameButton").addEventListener("click", this.endGame);
        //resetGame();
      },
      savePoints() {
        console.log("trying to save points");
        this.makeRequest({
          method: "POST",
          url: "/game/saveScore",
          contentType: "application/json",
          content: JSON.stringify({points: this.gameValues.points})
        }).catch(function (reason) {
          console.log(reason);
        }).then(this.getSavedPoints);

      },
      makeRequest({method: method, url: url, content: content, contentType: contentType}) { // code by SomeKittens/Stackoverflow, modified by me, https://stackoverflow.com/questions/30008114/how-do-i-promisify-native-xhr
        return new Promise(function (resolve, reject) {
          let xhr = new XMLHttpRequest();
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
      },
      getSavedPoints() {
        return this.makeRequest({method: "GET", url: "/game/saveScore"})
          .then(function (res) {
            let response = JSON.parse(res);
            if (response.points) {
              this.personalBestPoints = response.points;
            } else {
              this.personalBestPoints = 0;
            }
          }).then(function (points) {
            this.gameValues.prevBest = points;
          }).catch(function (reason) {
            console.log(reason);
          });
      },
      getHighscore() {
        return this.makeRequest({method: "GET", url: "/game/highscores"})
          .then(function (res) {
            let response = JSON.parse(res);
            if (response && response[0]) {
              this.overallHighscorePoints = (response[0]).highscore; //todo make sure it is the highest highscore
            } else {
              this.overallHighscorePoints = 0;
            }
          }).catch(function (reason) {
            console.log(reason);
          })
      }
    }
  }
</script>

<style>
  #score-container {
    grid-area: score-container;
    text-align: left;
    border: dashed grey 1px;
  }

  #game-container-container {
    box-sizing: border-box;
    grid-area: game-container;

    font-size: 0;
    border: dashed grey 1px;
    margin: 0 5px 5px;
  }

  #game-container {
    width: 100%;
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
