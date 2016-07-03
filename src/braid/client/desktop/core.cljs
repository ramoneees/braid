(ns braid.client.desktop.core
  (:require [reagent.core :as r]
            [braid.client.store :as store]
            [braid.client.ui.views.app :refer [app-view]]
            [braid.client.clj-highlighter :as highlighter]
            [braid.client.dispatcher :as dispatcher]
            [braid.client.router :as router]))

(enable-console-print!)

(defn ^:export init []
  (.addEventListener js/document "visibilitychange"
                     (fn [e] (store/set-window-visibility! (= "visible" (.-visibilityState js/document)))))

  (highlighter/install-highlighter)

  (r/render [app-view] (. js/document (getElementById "app")))

  (router/init)

  (dispatcher/dispatch! :check-auth!))

(defn ^:export reload
  "Force a re-render. For use with figwheel"
  []
  (r/render [app-view] (.getElementById js/document "app")))