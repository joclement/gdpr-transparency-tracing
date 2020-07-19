benchmark1:
	ab -n 100000 -c 10 http://172.31.59.188/game/highscores/top

benchmark2:
	ab -n 100000 http://172.31.59.188/game/highscores/top

benchmark3:
	ab -n 100000 -c 10 -T "application/json" -u savescorecontent.txt  http://172.31.59.188/game/savescore/user1

benchmark4:
	ab -n 100000 -T "application/json" -u savescorecontent.txt  http://172.31.59.188/game/savescore/user1


.PHONY: benchmark1 benchmark2 benchmark3 benchmark4
