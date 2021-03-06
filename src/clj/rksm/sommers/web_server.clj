(ns rksm.sommers.web-server
  (:require [org.httpkit.server :as http]
            [compojure.route :as route]
            [compojure.core :as comp]
            [rksm.sommers.routes]))

(defonce server (atom nil))

(defn stop-server []
  (when-not (nil? @server)
    ;; graceful shutdown: wait 100ms for existing requests to be finished
    ;; :timeout is optional, when no timeout, stop immediately
    (@server :timeout 100)
    (reset! server nil)))

(defn start-server [& [port]]
  (when server (stop-server))
  (reset! server (http/run-server #'rksm.sommers.routes/routes {:port (or port 8080)}))
  (println "web server running on port" (or port 8080)))

(comment
  (start-server)
  (stop-server)
  )
