(ns example-app.core
  (:require [reagent.dom :as r.dom]
            [reagent.core :as r]))

; Custom components 
(defn Input [{:keys [class] :as props}]
  [:input
   (merge props {:class (str "block rounded-full p-1.5 px-3 border text-gray-600 border-gray-200 w-full " class)})])

(defn TodoItem [text]
  [:div.py-2.px-1.rounded-lg.border-b.border-opacity-75.hover:bg-gray-200.text-gray-600 text])
; End of custom components

(defn app []
  (let [todo-draft (r/atom "")
        todo-list (r/atom [])]
    (fn []
      ; app returns a function because reagent expects a function that returns hiccup
      [:div
       [:div.p-8.max-w-full
        [:div.rounded-lg.bg-gray-400.text-white.text-opacity-80.shadow-xs.py-6.px-4.text-center
         [:div.text-2xl.lg:text-6xl
          [:span "List"]]]
        [:div
         [:ul.mt-3.flex.flex-col.gap-2
          (for [todo @todo-list]
            ^{:key todo} [TodoItem todo])]]

        [Input {:type "text"
                :class "!bg-red-300 my-5 mt-12 "
                :value @todo-draft
                :on-change #(reset! todo-draft (-> % .-target .-value))}]

        [:input.hover:cursor-pointer
         {:type "button"
          :value "Add item"
          :class "bg-green-100 bg-opacity-20 hover:bg-green-200 hover:bg-opacity-20 border border-green-600 text-green-600 py-1 w-full rounded-full cursor-pointer"
          :on-click #(do (swap! todo-list conj @todo-draft)
                         (reset! todo-draft ""))}]]])))


(r.dom/render [app] (js/document.getElementById "app"))