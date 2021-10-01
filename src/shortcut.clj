(ns shortcut
  (:require [clojure.tools.logging :as log]))

(def release {:release 1 :time "2021-10-01-13-30"})

(defn start!
  []
  (log/infof "start shortcut %s" release)
  (log/info "\n       .__                   __                 __    ___.                                              __           .__        \n  _____|  |__   ____________/  |_  ____  __ ___/  |_  \\_ |__ ___.__.   ____ ______   ________________ _/  |________  |__| ____  \n /  ___/  |  \\ /  _ \\_  __ \\   __\\/ ___\\|  |  \\   __\\  | __ <   |  |  /  _ \\\\____ \\_/ __ \\_  __ \\__  \\\\   __\\_  __ \\ |  |/  _ \\ \n \\___ \\|   Y  (  <_> )  | \\/|  | \\  \\___|  |  /|  |    | \\_\\ \\___  | (  <_> )  |_> >  ___/|  | \\// __ \\|  |  |  | \\/ |  (  <_> )\n/____  >___|  /\\____/|__|   |__|  \\___  >____/ |__|    |___  / ____|  \\____/|   __/ \\___  >__|  (____  /__|  |__| /\\ |__|\\____/ \n     \\/     \\/                        \\/                   \\/\\/             |__|        \\/           \\/           \\/            \n"))

(defn stop!
  []
  (log/infof "start shortcut %s" release))

(defn init!
  []
  (Thread/setDefaultUncaughtExceptionHandler
   (reify Thread$UncaughtExceptionHandler
     (uncaughtException [_ thread ex]
       (log/error ex "uncaught exception" (.getName thread)))))
  (.addShutdownHook (Runtime/getRuntime) (Thread. (reify Runnable (run [_] (stop!)))))
  (log/infof "initializing shortcut v%s" release)
  (start!))

(defn -main
  [& _]
  (init!))