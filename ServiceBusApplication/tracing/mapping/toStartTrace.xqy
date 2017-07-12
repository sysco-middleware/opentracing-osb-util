xquery version "1.0" encoding "utf-8";

(:: OracleAnnotationVersion "1.0" ::)

declare namespace ns1="http://middleware.sysco.no/opentracing/osb";
(:: import schema at "../schema/trace.xsd" ::)

declare variable $messageId as xs:string external;
declare variable $componentName as xs:string external;
declare variable $parentMessageId as xs:string? external;

declare function local:func($messageId as xs:string, 
                            $componentName as xs:string, 
                            $parentMessageId as xs:string) 
                            as element() (:: schema-element(ns1:startTrace) ::) {
    <ns1:startTrace>
        <ns1:messageId>{fn:data($messageId)}</ns1:messageId>
        <ns1:componentName>{fn:data($componentName)}</ns1:componentName>
        {
            if ($parentMessageId) then
                <ns1:parentMessageId>{fn:data($parentMessageId)}</ns1:parentMessageId>
            else
               ()
        }
    </ns1:startTrace>
};

local:func($messageId, $componentName, $parentMessageId)
