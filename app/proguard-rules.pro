-keepnames class com.fasoh.corona.models.*
-keepnames class com.fasoh.corona.models.country.*
-keepnames class com.fasoh.corona.models.global.*
-keepnames class com.fasoh.corona.models.timeline.*

-keep enum * { *; }
-keepattributes *Annotation*

-keepclasseswithmembernames class com.fasoh.corona.models.** { *; }
-keepclasseswithmembernames class com.fasoh.corona.models.country.** { *; }
-keepclasseswithmembernames class com.fasoh.corona.models.global.** { *; }
-keepclasseswithmembernames class com.fasoh.corona.models.timeline.** { *; }
-keepattributes Signature

