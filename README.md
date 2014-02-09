# alchemy-clojure

A SDK for AlchemyAPI using Clojure

## Usage

```clojure
(with-api-key "your-api-key" 
    (entities :text "This is some text about Russia and Sochi" {:disambiguate 0}))
````

## License

Copyright © 2014 Dmitriy Rozhkov

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
