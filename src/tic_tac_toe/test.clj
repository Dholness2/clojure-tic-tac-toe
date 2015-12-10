(defn changer
[change coins coin-amounts]
    (let [current-change (get-change change (first coins))]
     (changer current-change (drop coins)))

(changer