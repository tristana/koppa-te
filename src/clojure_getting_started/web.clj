(ns clojure-getting-started.web
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]]))

(defn splash []
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body "Hallå Världen! Koppa te? 	&#9749;"})

(defn you-are-special []
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body "Hallå Vasile! Puss puss =^._.^="})

(defn brew-tea [tea-type]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body "Brewing some " tea-type " tea"})

(defn no-soup-for-you []
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body "I said TEA, not FRUIT SOUP! NEEEXT!!"})

(defroutes app
  (GET "/" []
       (splash))
 (GET "/green" []
       (brew-tea "green")) 
 (GET "/black" []
       (brew-tea "black"))
 (GET "/herbal" []
       (brew-tea "herbal"))
 (GET "/fruit" []
       (no-soup-for-you))
 (GET "/IV" []
       (you-are-special))
 (ANY "*" []
       (route/not-found (slurp (io/resource "404.html")))))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))

;; For interactive development:
;; (.stop server)
;; (def server (-main))
