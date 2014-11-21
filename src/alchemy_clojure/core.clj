(ns alchemy-clojure.core
  (:require [cheshire.core   :as json])
  (:require [clj-http.client :as client]))

(defonce endpoints
  {:sentiment {:url  "/url/URLGetTextSentiment"
               :text "/text/TextGetTextSentiment"
               :html "/html/HTMLGetTextSentiment" }
   :sentiment_targeted {:url  "/url/URLGetTargetedSentiment"
                        :text "/text/TextGetTargetedSentiment"
                        :html "/html/HTMLGetTargetedSentiment"}
   :author {:url  "/url/URLGetAuthor"
            :html "/html/HTMLGetAuthor"}
   :keywords {:url  "/url/URLGetRankedKeywords"
              :text "/text/TextGetRankedKeywords"
              :html "/html/HTMLGetRankedKeywords"}
   :concepts {:url  "/url/URLGetRankedConcepts"
              :text "/text/TextGetRankedConcepts"
              :html "/html/HTMLGetRankedConcepts"}
   :entities {:url  "/url/URLGetRankedNamedEntities"
              :text "/text/TextGetRankedNamedEntities"
              :html "/html/HTMLGetRankedNamedEntities"}
   :category {:url  "/url/URLGetCategory"
              :text "/text/TextGetCategory"
              :html "/html/HTMLGetCategory"}
   :relations {:url  "/url/URLGetRelations"
               :text "/text/TextGetRelations"
               :html "/html/HTMLGetRelations"}
   :language {:url   "/url/URLGetLanguage"
              :text  "/text/TextGetLanguage"
              :html  "/html/HTMLGetLanguage"}
   :text {:url  "/url/URLGetText"
          :html "/html/HTMLGetText"}
   :text_raw {:url  "/url/URLGetRawText"
              :html "/html/HTMLGetRawText"}
   :title {:url  "/url/URLGetTitle"
           :html "/html/HTMLGetTitle"}
   :feeds {:url  "/url/URLGetFeedLinks"
           :html "/html/HTMLGetFeedLinks"}
   :microformats {:url  "/url/URLGetMicroformatData"
                  :html "/html/HTMLGetMicroformatData"}})

(defonce base-url "http://access.alchemyapi.com/calls")

(defonce ^:dynamic *api-key* nil)

(defmacro with-api-key
  "Binds the specified API key and executes the body."
  [api-key & body]
  `(binding [*api-key* ~api-key] ~@body))

(defmacro defendpoint
  "Defines endpoint function"
  [endpointname docstring]
  `(defn ~(symbol endpointname)
      ~docstring
     [flavor# data# & [options#]]
     (let [options# (assoc options# flavor# data#) url# (flavor# (~(keyword endpointname) endpoints))]
    (request url# options#))))

(defn- request
  "HTTP request wrapper"
  [url options]
  (json/parse-string (:body (client/post (str base-url url)
                                         {:form-params (assoc options
                                                         :outputMode "json"
                                                         :apikey *api-key*)}))
                     true))

(defendpoint sentiment "Calculates the sentiment for text, a URL or HTML.")

(defendpoint sentiment_targeted "Calculates the targeted sentiment for text, a URL or HTML.")

(defendpoint author "Extracts the author from a URL or HTML.")

(defendpoint keywords "Extracts the keywords from text, a URL or HTML.")

(defendpoint concepts "Tags the concepts for text, a URL or HTML.")

(defendpoint entities "Extracts the entities for text, a URL or HTML.")

(defendpoint category "Categorizes the text for text, a URL or HTML.")

(defendpoint relations "Extracts the relations for text, a URL or HTML.")

(defendpoint language "Detects the language for text, a URL or HTML.")

(defendpoint text "Extracts the cleaned text (removes ads, navigation, etc.) for text, a URL or HTML.")

(defendpoint text_raw "Extracts the raw text (includes ads, navigation, etc.) for a URL or HTML.")

(defendpoint title "Extracts the title for a URL or HTML.")

(defendpoint feeds "Detects the RSS/ATOM feeds for a URL or HTML.")

(defendpoint microformats "Parses the microformats for a URL or HTML.")
