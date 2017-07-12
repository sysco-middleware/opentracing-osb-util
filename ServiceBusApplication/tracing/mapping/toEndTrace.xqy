xquery version "1.0" encoding "utf-8";

(:: OracleAnnotationVersion "1.0" ::)

declare namespace ns1="http://middleware.sysco.no/opentracing/osb";
(:: import schema at "../schema/trace.xsd" ::)

declare variable $messageId as xs:string external;

declare function local:func($messageId as xs:string) as element() (:: schema-element(ns1:endTrace) ::) {
    <ns1:endTrace>
        <ns1:messageId>{fn:data($messageId)}</ns1:messageId>
    </ns1:endTrace>
};

local:func($messageId)
