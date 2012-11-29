(ns shopify.util.auth
  (:require [clojure.string :as str]
            digest))

(defn verify-params
  "Uses your shared secret to verify that a signed map of query params is from Shopify."
  [app params]
  (let [secret (app :secret)
        signature (params :signature)
        params (dissoc params :signature)
        join-keypair (comp (partial str/join "=") (partial map name))
        sorted-param-string (->> params (map join-keypair) sort (str/join))]
    (= signature
       (digest/md5 (str secret sorted-param-string)))))
