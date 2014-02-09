(ns alchemy-clojure.core
  (:require [cheshire.core   :as json])
  (:require [clj-http.client :as client]))

(defonce endpoints {
  :sentiment {
    :url  "/url/URLGetTextSentiment"
    :text "/text/TextGetTextSentiment"
    :html "/html/HTMLGetTextSentiment"  
  }
  :sentiment_targeted {
    :url  "/url/URLGetTargetedSentiment"
    :text "/text/TextGetTargetedSentiment"
    :html "/html/HTMLGetTargetedSentiment"
  }
  :author {
    :url  "/url/URLGetAuthor"
    :html "/html/HTMLGetAuthor"
  }
  :keywords {
    :url  "/url/URLGetRankedKeywords"
    :text "/text/TextGetRankedKeywords"
    :html "/html/HTMLGetRankedKeywords"
  }
  :concepts {
    :url  "/url/URLGetRankedConcepts"
    :text "/text/TextGetRankedConcepts"
    :html "/html/HTMLGetRankedConcepts"
  }
  :entities {
    :url  "/url/URLGetRankedNamedEntities"
    :text "/text/TextGetRankedNamedEntities"
    :html "/html/HTMLGetRankedNamedEntities"
  }
  :category {
    :url  "/url/URLGetCategory"
    :text "/text/TextGetCategory"
    :html "/html/HTMLGetCategory"
  }
  :relations {
    :url  "/url/URLGetRelations"
    :text "/text/TextGetRelations"
    :html "/html/HTMLGetRelations"
  }
  :language {
    :url   "/url/URLGetLanguage"
    :text  "/text/TextGetLanguage"
    :html  "/html/HTMLGetLanguage"
  }
  :text {
    :url  "/url/URLGetText"
    :html "/html/HTMLGetText"
  }
  :text_raw {
    :url  "/url/URLGetRawText"
    :html "/html/HTMLGetRawText"
  }
  :title {
    :url  "/url/URLGetTitle"
    :html "/html/HTMLGetTitle"
  }
  :feeds {
    :url  "/url/URLGetFeedLinks"
    :html "/html/HTMLGetFeedLinks"
  }
  :microformats {
    :url  "/url/URLGetMicroformatData"
    :html "/html/HTMLGetMicroformatData"
  }
})

(defonce base-url "http://access.alchemyapi.com/calls")

(defonce ^:dynamic *api-key* nil)

(defmacro with-api-key
  "Binds the specified API key and executes the body."
  [api-key & body]
  `(binding [*api-key* ~api-key] ~@body))

; (defmacro defendpoint
;   "Defines endpoint function"
;   [endpoint flavor data & opts]
;   `(let 
;     [options# (assoc ~opts ~flavor ~data) url# (~flavor (~endpoint endpoints))]
;     (request url# options#)))

(defn- request
  "HTTP request wrapper"
  [url, options]
  (let 
    [options (assoc options :outputMode "json" :apikey *api-key*) ]
    (json/parse-string (:body (client/post (str base-url url) {:form-params options})) true)))

(defn sentiment
  "Calculates the sentiment for text, a URL or HTML."
  [flavor data & [options]]
  (let 
    [options (assoc options flavor data) url (flavor (:sentiment endpoints))]
    (request url options)))

(defn sentiment_targeted
  "Calculates the targeted sentiment for text, a URL or HTML."
  [flavor data & [options]]
  (let 
    [options (assoc options flavor data) url (flavor (:sentiment_targeted endpoints))]
    (request url options)))

(defn author
  "Extracts the author from a URL or HTML."
  [flavor data & [options]]
  (let 
    [options (assoc options flavor data) url (flavor (:author endpoints))]
    (request url options)))

(defn keywords
  "Extracts the keywords from text, a URL or HTML."
  [flavor data & [options]]
  (let 
    [options (assoc options flavor data) url (flavor (:keywords endpoints))]
    (request url options)))

(defn concepts
  "Tags the concepts for text, a URL or HTML."
  [flavor data & [options]]
  (let 
    [options (assoc options flavor data) url (flavor (:concepts endpoints))]
    (request url options)))

(defn entities
  "Extracts the entities for text, a URL or HTML."
  [flavor data & [options]]
  (let 
    [options (assoc options flavor data) url (flavor (:entities endpoints))]
    (request url options)))

(defn category
  "Categorizes the text for text, a URL or HTML."
  [flavor data & [options]]
  (let 
    [options (assoc options flavor data) url (flavor (:category endpoints))]
    (request url options)))

(defn relations
  "Extracts the relations for text, a URL or HTML."
  [flavor data & [options]]
  (let 
    [options (assoc options flavor data) url (flavor (:relations endpoints))]
    (request url options)))

(defn language
  "Detects the language for text, a URL or HTML."
  [flavor data & [options]]
  (let 
    [options (assoc options flavor data) url (flavor (:language endpoints))]
    (request url options)))

(defn text
  "Extracts the cleaned text (removes ads, navigation, etc.) for text, a URL or HTML."
  [flavor data & [options]]
  (let 
    [options (assoc options flavor data) url (flavor (:text endpoints))]
    (request url options)))

(defn text_raw
  "Extracts the raw text (includes ads, navigation, etc.) for a URL or HTML."
  [flavor data & [options]]
  (let 
    [options (assoc options flavor data) url (flavor (:text_raw endpoints))]
    (request url options)))

(defn title
  "Extracts the title for a URL or HTML."
  [flavor data & [options]]
  (let 
    [options (assoc options flavor data) url (flavor (:title endpoints))]
    (request url options)))

(defn feeds
  "Detects the RSS/ATOM feeds for a URL or HTML."
  [flavor data & [options]]
  (let 
    [options (assoc options flavor data) url (flavor (:feeds endpoints))]
    (request url options)))

(defn microformats
  "Parses the microformats for a URL or HTML."
  [flavor data & [options]]
  (let 
    [options (assoc options flavor data) url (flavor (:microformats endpoints))]
    (request url options)))

