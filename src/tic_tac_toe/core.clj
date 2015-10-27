(ns tic-tac-toe.core
  (:gen-class))

  (defn create-empty-board []
	["_" "_" "_" "_" "_" "_" "_" "_" "_"])
   
  (defn move 
    [location player board]
    (assoc board location player ))

  
(defn validmove? [move]
  	(if (and (<= move 8)(>=  move 0))
      true 
      false
  	))

(defn user-input-move []
    (println "what is your next move")
    (def input (read-string(read-line)))
    (if(= true (validmove? input))
      input
      (do (println "invalid selection") 
      	   (user-input-move))))
  
  (defn computer-move [board]
  	(.indexOf board "_"))

  (defn display-board [board]
    (println  board)
  )

 (defn clear-terminal[]
   (println "\033[2J"))

(defn game-runner 
 ([welcome]
   (println welcome)
   (def current-board (create-empty-board))
   (println current-board)
   	(def current-board (move (user-input-move) "X" current-board)) 
    (clear-terminal)
    (display-board current-board)
   (game-runner current-board "X"))
 ([board player]
	(def current-board (move (computer-move board) "O" board))
    (clear-terminal)
    (display-board current-board)
    (def current-board (move (user-input-move) player current-board))
    (clear-terminal)
    (display-board current-board)
    (game-runner current-board "X")))

(defn -main []
   ; (def move-chart ["0" "1" "2" "3" "4" "5" "6" "7" "8"])
  (def welcome "welcome to Dons tic tac toe")
  (game-runner  welcome ))